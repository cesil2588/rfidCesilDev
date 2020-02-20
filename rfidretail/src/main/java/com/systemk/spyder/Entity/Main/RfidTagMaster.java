package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//RFID 태그 마스터 테이블
//@ApiModel(description = "RFID 태그 마스터 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagMaster implements Serializable{
	
	private static final long serialVersionUID = -7230386263355702421L;

	// RFID 태그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rfidTagSeq;

	// 바택일련번호
	//@ApiModelProperty(notes = "바택 발행 일련번호")
	private long bartagSeq;

	// RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩 정보")
	@Column(nullable = true, length = 32)
	private String rfidTag;

	// ERP key
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;

	// 시즌(연도,계절)
	//@ApiModelProperty(notes = "시즌, 연도")
	@Column(nullable = false, length = 3)
	private String season;

	// 오더차수
	//@ApiModelProperty(notes = "오더차수")
	@Column(nullable = false, length = 2)
	private String orderDegree;

	// 생산업체코드
	//@ApiModelProperty(notes = "생산업체코드")
	@Column(nullable = false, length = 3)
	private String customerCd;

	// 발행장소
	//@ApiModelProperty(notes = "발행장소(1: 발행업체, 2: 물류센터, 3: 기타)")
	@Column(nullable = false, length = 1)
	private String publishLocation;

	// 발행날짜
	//@ApiModelProperty(notes = "발행날짜")
	@Column(nullable = true, length = 6)
	private String publishRegDate;

	// 발행차수
	//@ApiModelProperty(notes = "발행차수")
	@Column(nullable = true, length = 2)
	private String publishDegree;

	// RFID 일련번호
	//@ApiModelProperty(notes = "RFID 태그 시리얼")
	@Column(nullable = false, length = 5)
	private String rfidSeq;

	// 변경상태
	//@ApiModelProperty(notes = "상태(1:미발행, 2:발행대기, 3:발행완료, 4:재발행대기, 5:재발행완료, 6:재발행요청, 7:폐기)")
	@Column(nullable = false, length = 1)
	private String stat;

	// 등록일시
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 발행일자
	//@ApiModelProperty(notes = "바택발행 생성일자")
	@Column(nullable = false, length = 8)
	private String createDate;

	// 일련번호
	//@ApiModelProperty(notes = "바택발행 시리얼")
	private Long seq;

	// 라인번호
	//@ApiModelProperty(notes = "바택발행 라인번호")
	private Long lineSeq;
	
	// 수정일시
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="updUserSeq")
 	private UserInfo updUserInfo;
	
	// 검색 데이터
	//@ApiModelProperty(notes = "바택 발행 정보")
	@Transient
	@JsonInclude(Include.NON_NULL)
	private BartagMaster bartagMaster;
	
	//@ApiModelProperty(notes = "RFID 태그 이력 목록")
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<RfidTagHistory> rfidTagHistoryList;
	
	//@ApiModelProperty(notes = "생산 재고 정보")
	@Transient
	@JsonInclude(Include.NON_NULL)
	private ProductionStorageRfidTag productionStorageRfidTag;
	
	//@ApiModelProperty(notes = "물류 재고 정보")
	@Transient
	@JsonInclude(Include.NON_NULL)
	private DistributionStorageRfidTag distributionStorageRfidTag;
	
	//@ApiModelProperty(notes = "매장 재고 정보")
	@Transient
	@JsonInclude(Include.NON_NULL)
	private StoreStorageRfidTag storeStorageRfidTag;
	
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Long getRfidTagSeq() {
		return rfidTagSeq;
	}

	public void setRfidTagSeq(Long rfidTagSeq) {
		this.rfidTagSeq = rfidTagSeq;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getCustomerCd() {
		return customerCd;
	}

	public void setCustomerCd(String customerCd) {
		this.customerCd = customerCd;
	}

	public String getPublishLocation() {
		return publishLocation;
	}

	public void setPublishLocation(String publishLocation) {
		this.publishLocation = publishLocation;
	}

	public String getPublishRegDate() {
		return publishRegDate;
	}

	public void setPublishRegDate(String publishRegDate) {
		this.publishRegDate = publishRegDate;
	}

	public String getPublishDegree() {
		return publishDegree;
	}

	public void setPublishDegree(String publishDegree) {
		this.publishDegree = publishDegree;
	}

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Long lineSeq) {
		this.lineSeq = lineSeq;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public long getBartagSeq() {
		return bartagSeq;
	}

	public void setBartagSeq(long bartagSeq) {
		this.bartagSeq = bartagSeq;
	}
	

	public BartagMaster getBartagMaster() {
		return bartagMaster;
	}

	public void setBartagMaster(BartagMaster bartagMaster) {
		this.bartagMaster = bartagMaster;
	}

	public List<RfidTagHistory> getRfidTagHistoryList() {
		return rfidTagHistoryList;
	}

	public void setRfidTagHistoryList(List<RfidTagHistory> rfidTagHistoryList) {
		this.rfidTagHistoryList = rfidTagHistoryList;
	}
	
	public ProductionStorageRfidTag getProductionStorageRfidTag() {
		return productionStorageRfidTag;
	}

	public void setProductionStorageRfidTag(ProductionStorageRfidTag productionStorageRfidTag) {
		this.productionStorageRfidTag = productionStorageRfidTag;
	}

	public DistributionStorageRfidTag getDistributionStorageRfidTag() {
		return distributionStorageRfidTag;
	}

	public void setDistributionStorageRfidTag(DistributionStorageRfidTag distributionStorageRfidTag) {
		this.distributionStorageRfidTag = distributionStorageRfidTag;
	}

	public StoreStorageRfidTag getStoreStorageRfidTag() {
		return storeStorageRfidTag;
	}

	public void setStoreStorageRfidTag(StoreStorageRfidTag storeStorageRfidTag) {
		this.storeStorageRfidTag = storeStorageRfidTag;
	}

	@Override
	public String toString() {
		return "RfidTagMaster [rfidTagSeq=" + rfidTagSeq + ", bartagSeq=" + bartagSeq + ", rfidTag=" + rfidTag
				+ ", erpKey=" + erpKey + ", season=" + season + ", orderDegree=" + orderDegree + ", customerCd="
				+ customerCd + ", publishLocation=" + publishLocation + ", publishRegDate=" + publishRegDate
				+ ", publishDegree=" + publishDegree + ", rfidSeq=" + rfidSeq + ", stat=" + stat + ", regDate="
				+ regDate + ", createDate=" + createDate + ", seq=" + seq + ", lineSeq=" + lineSeq + "]";
	}
	
	public void CopyData(RfidTagMaster param)
    {
        this.bartagSeq = param.getBartagSeq();
        this.rfidTag = param.getRfidTag();
        this.erpKey = param.getErpKey();
        this.season = param.getSeason();
        this.orderDegree = param.getOrderDegree();
        this.customerCd = param.getCustomerCd();
        this.publishLocation = param.getPublishLocation();
        this.publishRegDate = param.getPublishRegDate();
        this.publishDegree = param.getPublishDegree();
        this.rfidSeq = param.getRfidSeq();
        this.stat = param.getStat();
        this.regDate = param.getRegDate();
        this.createDate = param.getCreateDate();
        this.seq = param.getSeq();
        this.lineSeq = param.getLineSeq();
    }
}