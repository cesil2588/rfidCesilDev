package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//매장 재고 RFID 태그 테이블
//@ApiModel(description = "매장 재고 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreStorageRfidTag implements Serializable{

	private static final long serialVersionUID = 7712884191782800706L;

	// 매장 재고 RFID 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long storeStorageDetailSeq;

	// 매장 재고 일련번호
	//@ApiModelProperty(notes = "매장 재고 일련번호")
	private Long storeStorageSeq;

	// 박스 일련번호
	//@ApiModelProperty(notes = "박스 일련번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="boxSeq")
	private BoxInfo boxInfo;
	
	// 박스 바코드
	//@ApiModelProperty(notes = "박스 바코드")
    @Column(nullable = true, length = 30)
    private String boxBarcode;

	// RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = true, length = 32)
	private String rfidTag;

	// ERP key
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;

	// 시즌(연도,계절)
	//@ApiModelProperty(notes = "시즌")
	@Column(nullable = false, length = 3)
	private String season;

	// 오더차수
	//@ApiModelProperty(notes = "오더차수")
	@Column(nullable = false, length = 2)
	private String orderDegree;

	// 업체코드
	//@ApiModelProperty(notes = "업체코드")
	@Column(nullable = false, length = 3)
	private String customerCd;

	// 발행장소
	//@ApiModelProperty(notes = "발행장소")
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

	// 변경상태(1:입고예정, 2:입고, 3:판매, 4:반품요청, 5:재발행요청, 6:매장간이동 요청, 7:폐기)
	//@ApiModelProperty(notes = "변경상태(1:입고예정, 2:입고, 3:판매, 4:반품요청, 5:재발행요청, 6:매장간이동 요청, 7:폐기)")
	@Column(nullable = false, length = 1)
	private String stat;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regUserSeq")
	private UserInfo regUserInfo;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updUserSeq")
	private UserInfo updUserInfo;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;

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

	public BoxInfo getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	public Long getStoreStorageDetailSeq() {
		return storeStorageDetailSeq;
	}

	public void setStoreStorageDetailSeq(Long storeStorageDetailSeq) {
		this.storeStorageDetailSeq = storeStorageDetailSeq;
	}

	public Long getStoreStorageSeq() {
		return storeStorageSeq;
	}

	public void setStoreStorageSeq(Long storeStorageSeq) {
		this.storeStorageSeq = storeStorageSeq;
	}

	public void CopyData(DistributionStorageRfidTag param)
    {
        this.rfidTag = param.getRfidTag();
        this.erpKey = param.getErpKey();
        this.season = param.getSeason();
        this.orderDegree = param.getOrderDegree();
        this.customerCd = param.getCustomerCd();
        this.publishLocation = param.getPublishLocation();
        this.publishRegDate = param.getPublishRegDate();
        this.publishDegree = param.getPublishDegree();
        this.rfidSeq = param.getRfidSeq();
        this.regDate = param.getRegDate();
    }
	
	public void CopyData(RfidTagMaster param)
    {
        this.rfidTag = param.getRfidTag();
        this.erpKey = param.getErpKey();
        this.season = param.getSeason();
        this.orderDegree = param.getOrderDegree();
        this.customerCd = param.getCustomerCd();
        this.publishLocation = param.getPublishLocation();
        this.publishRegDate = param.getPublishRegDate();
        this.publishDegree = param.getPublishDegree();
        this.rfidSeq = param.getRfidSeq();
        this.regDate = param.getRegDate();
    }

	public String getBoxBarcode() {
		return boxBarcode;
	}

	public void setBoxBarcode(String boxBarcode) {
		this.boxBarcode = boxBarcode;
	}
}