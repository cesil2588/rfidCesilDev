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

//@ApiModel(description = "RFID 생산 요청 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BartagOrder implements Serializable {
	
	private static final long serialVersionUID = 4075688718259989136L;

	// 바택 요청 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bartagOrderSeq;
	
	// 생성일자
	//@ApiModelProperty(notes = "생성일자")
	@Column(nullable = false, length = 8)
	private String createDate;
	
	// 생성 일련번호
	//@ApiModelProperty(notes = "생성일련번호")
	private Long createSeq;
	
	// 생성 라인번호
	//@ApiModelProperty(notes = "라인번호")
	private Long createNo;
	
	// 제품연도
	//@ApiModelProperty(notes = "제품연도")
	@Column(nullable = false, length = 4)
	private String productYy;

	// 제품시즌
	//@ApiModelProperty(notes = "제품시즌")
	@Column(nullable = false, length = 5)
	private String productSeason;
	
	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	private String style;
	
	// 오더차수
	//@ApiModelProperty(notes = "오더차수")
	@Column(nullable = false, length = 3)
	private String orderDegree;
	
	// 나머지 스타일
	//@ApiModelProperty(notes = "컬러 + 사이즈")
	@Column(nullable = false, length = 20)
	private String anotherStyle;
	
	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
	private String color;
	
	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
	private String size;
	
	// ERP RFID용 바택
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;
	
	// 생산예정 확정 수량
	//@ApiModelProperty(notes = "실 생산예정 수량")
	private Long orderAmount;
	
	// RFID 태그 요청 확정 수량
	//@ApiModelProperty(notes = "RFID 태그 요청 확정 수량")
	private Long completeAmount;
	
	// RFID 태그 요청 미완료 수량
	//@ApiModelProperty(notes = "RFID 태그 요청 미확정 수량")
	private Long nonCheckCompleteAmount;
	
	// 생산업체
	//@ApiModelProperty(notes = "생산업체")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productionCompanySeq")
	private CompanyInfo productionCompanyInfo;
	
	// 세부생산업체
	//@ApiModelProperty(notes = "생산업체 상세")
	@Column(nullable = true, length = 20)
	private String detailProductionCompanyName;
	
	// 오더 일자
	//@ApiModelProperty(notes = "오더일자")
	@Column(nullable = false, length = 8)
	private String orderDate;
	
	// 오더 일련번호
	//@ApiModelProperty(notes = "오더 일련번호")
	private Long orderSeq;
	
	// 오더 시간
	//@ApiModelProperty(notes = "오더 시간")
	@Column(nullable = false, length = 8)
	private String orderTime;
	
	// RFID 최초 요청 상태
	//@ApiModelProperty(notes = "상태 (1: 초기생성, 2: 수량입력, 3: 확정완료, 4: 추가수량입력, 5: 추가완료, 6: 종결")
	@Column(nullable = false, length = 1)
	private String stat;
	
	// ERP 상태
	//@ApiModelProperty(notes = "ERP 상태")
	@Column(nullable = false, length = 1)
	private String erpStat;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="updUserSeq")
	private UserInfo updUserInfo;
	
	// 오더 완료일
	//@ApiModelProperty(notes = "RFID 생산 요청 완료일자")
	private Date completeDate;
	
	// 추가 요청 차수
	//@ApiModelProperty(notes = "RFID 생산 추가 요청 차수")
	@Column(nullable = false, length = 1)
	private String additionOrderDegree;
	
	// 추가 요청 수량
	//@ApiModelProperty(notes = "RFID 생산 추가 요청 확정 수량")
	private Long additionAmount;
	
	// 미완료 요청 수량
	//@ApiModelProperty(notes = "RFID 생산 추가 요청 미확정 수량")
	private Long nonCheckAdditionAmount;
	
	// 이전 추가 요청 수량
	//@ApiModelProperty(notes = "이전 차수까지의 RFID 생산 추가 수량")
	private Long tempAdditionAmount;
	
	// 오더 완료 여부
	//@ApiModelProperty(notes = "RFID 생산 요청 완료여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String completeYn;

	public Long getBartagOrderSeq() {
		return bartagOrderSeq;
	}

	public void setBartagOrderSeq(Long bartagOrderSeq) {
		this.bartagOrderSeq = bartagOrderSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getCreateSeq() {
		return createSeq;
	}

	public void setCreateSeq(Long createSeq) {
		this.createSeq = createSeq;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getAnotherStyle() {
		return anotherStyle;
	}

	public void setAnotherStyle(String anotherStyle) {
		this.anotherStyle = anotherStyle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public CompanyInfo getProductionCompanyInfo() {
		return productionCompanyInfo;
	}

	public void setProductionCompanyInfo(CompanyInfo productionCompanyInfo) {
		this.productionCompanyInfo = productionCompanyInfo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

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

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getDetailProductionCompanyName() {
		return detailProductionCompanyName;
	}

	public void setDetailProductionCompanyName(String detailProductionCompanyName) {
		this.detailProductionCompanyName = detailProductionCompanyName;
	}

	public String getErpStat() {
		return erpStat;
	}

	public void setErpStat(String erpStat) {
		this.erpStat = erpStat;
	}
	
	public String getProductYy() {
		return productYy;
	}

	public void setProductYy(String productYy) {
		this.productYy = productYy;
	}

	public String getProductSeason() {
		return productSeason;
	}

	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
	}
	
	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}
	
	public String getAdditionOrderDegree() {
		return additionOrderDegree;
	}

	public void setAdditionOrderDegree(String additionOrderDegree) {
		this.additionOrderDegree = additionOrderDegree;
	}

	public Long getAdditionAmount() {
		return additionAmount;
	}

	public void setAdditionAmount(Long additionAmount) {
		this.additionAmount = additionAmount;
	}
	
	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}
	
	public Long getTempAdditionAmount() {
		return tempAdditionAmount;
	}

	public void setTempAdditionAmount(Long tempAdditionAmount) {
		this.tempAdditionAmount = tempAdditionAmount;
	}
	
	public Long getNonCheckCompleteAmount() {
		return nonCheckCompleteAmount;
	}

	public void setNonCheckCompleteAmount(Long nonCheckCompleteAmount) {
		this.nonCheckCompleteAmount = nonCheckCompleteAmount;
	}

	public Long getNonCheckAdditionAmount() {
		return nonCheckAdditionAmount;
	}

	public void setNonCheckAdditionAmount(Long nonCheckAdditionAmount) {
		this.nonCheckAdditionAmount = nonCheckAdditionAmount;
	}

	// BartagOrder 모델 복사
	public void CopyData(BartagOrder param)
    {
        this.bartagOrderSeq = param.getBartagOrderSeq();
        this.productYy = param.getProductYy();
        this.productSeason = param.getProductSeason();
        this.anotherStyle = param.getAnotherStyle();
        this.color = param.getColor();
        this.erpKey = param.getErpKey();
        this.completeAmount = param.getCompleteAmount();
        this.completeDate = param.getCompleteDate();
        this.createDate = param.getCreateDate();
        this.createNo = param.getCreateNo();
        this.createSeq = param.getCreateSeq();
        this.orderAmount = param.getOrderAmount();
        this.orderDate = param.getOrderDate();
        this.orderDegree = param.getOrderDegree();
        this.orderSeq = param.getOrderSeq();
        this.orderTime = param.getOrderTime();
        this.regDate = param.getRegDate();
        this.size = param.getSize();
        this.stat = param.getStat();
        this.style = param.getStyle();
        this.updDate = param.getUpdDate();
        this.productionCompanyInfo = param.getProductionCompanyInfo();
        this.updUserInfo = param.getUpdUserInfo();
        this.detailProductionCompanyName = param.getDetailProductionCompanyName();
        this.erpStat = param.getErpStat();
        this.additionAmount = param.getAdditionAmount();
        this.additionOrderDegree = param.getAdditionOrderDegree();
        this.tempAdditionAmount = param.getTempAdditionAmount();
        this.completeYn = param.getCompleteYn();
        this.nonCheckAdditionAmount = param.getNonCheckAdditionAmount();
        this.nonCheckCompleteAmount = param.getNonCheckCompleteAmount();
    }

	public Long getCreateNo() {
		return createNo;
	}

	public void setCreateNo(Long createNo) {
		this.createNo = createNo;
	}
}
