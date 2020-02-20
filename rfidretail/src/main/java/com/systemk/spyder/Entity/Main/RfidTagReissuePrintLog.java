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

//태그 재발행 프린트 로그 테이블
//@ApiModel(description = "RFID 태그 재발행 프린트 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagReissuePrintLog implements Serializable{

	private static final long serialVersionUID = 479632876923360878L;

	// 태그 재발행 프린트 로그 테이블
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rfidTagReissuePrintLogSeq;
	
	// 생성일자
	//@ApiModelProperty(notes = "생성일자")
	@Column(nullable = false, length = 8)
	private String createDate;
		
	// 생성 일련번호
	//@ApiModelProperty(notes = "생성일련번호")
	private Long createSeq;
	
	// 타겟 RFID 태그
	//@ApiModelProperty(notes = "타겟 RFID 태그 인코딩 정보")
	@Column(nullable = false, length = 32)
	private String targetRfidTag;
	
	// ERP key
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;

	// 시즌(연도, 계절)
	//@ApiModelProperty(notes = "시즌(연도, 계절)")
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
	@Column(nullable = true, length = 1)
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
	
	// 신규 RFID 태그
	//@ApiModelProperty(notes = "신규 RFID 태그 인코딩 정보")
	@Column(nullable = true, length = 32)
	private String reissueRfidTag;
	
	// 상태(1: 재발행 요청, 2: 재발행 완료)
	//@ApiModelProperty(notes = "상태(1: 재발행 요청, 2: 재발행 완료)")
	@Column(nullable = false, length = 1)
	private String stat;
	
	// 재발행 요청 업체
	//@ApiModelProperty(notes = "재발행 요청 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="requestCompanySeq")
	private CompanyInfo requestCompanyInfo;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regUserSeq")
	private UserInfo regUserInfo;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updUserSeq")
	private UserInfo updUserInfo;
	
	// 재발행 완료일
	//@ApiModelProperty(notes = "재발행 완료일")
	private Date reissueCompleteDate;
	
	// 에러 메시지
	//@ApiModelProperty(notes = "에러메시지")
	@Column(nullable = true, length = 1000)
	private String errorMessage;

	public Long getRfidTagReissuePrintLogSeq() {
		return rfidTagReissuePrintLogSeq;
	}

	public void setRfidTagReissuePrintLogSeq(Long rfidTagReissuePrintLogSeq) {
		this.rfidTagReissuePrintLogSeq = rfidTagReissuePrintLogSeq;
	}

	public String getTargetRfidTag() {
		return targetRfidTag;
	}

	public void setTargetRfidTag(String targetRfidTag) {
		this.targetRfidTag = targetRfidTag;
	}

	public String getReissueRfidTag() {
		return reissueRfidTag;
	}

	public void setReissueRfidTag(String reissueRfidTag) {
		this.reissueRfidTag = reissueRfidTag;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
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

	public Date getReissueCompleteDate() {
		return reissueCompleteDate;
	}

	public void setReissueCompleteDate(Date reissueCompleteDate) {
		this.reissueCompleteDate = reissueCompleteDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CompanyInfo getRequestCompanyInfo() {
		return requestCompanyInfo;
	}

	public void setRequestCompanyInfo(CompanyInfo requestCompanyInfo) {
		this.requestCompanyInfo = requestCompanyInfo;
	}
}