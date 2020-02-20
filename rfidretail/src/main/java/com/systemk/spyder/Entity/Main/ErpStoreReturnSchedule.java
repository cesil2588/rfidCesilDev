package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "물류 매장 반품 출고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpStoreReturnSchedule implements Serializable{

	private static final long serialVersionUID = 8747797875075532681L;
	
	//@ApiModelProperty(notes = "반품일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long erpStoreReturnScheduleSeq;
	
	// 반품오더번호(box_no)
	//@ApiModelProperty(notes = "반품오더번호")
	@Column(nullable = false, length = 20)
	private String returnOrderNo;
	
	//반품 오더 라인번호
	//@ApiModelProperty(notes = "반품 오더 라인번호")
	@Column(nullable = true, length = 6)
	private String returnOrderLiNo;
	
	//미결 발생일
	//@ApiModelProperty(notes = "미결 발생일")
	@Column(nullable = true, length = 8)
	private String orderRegDate;
	
	//from 거래처 코드
	//@ApiModelProperty(notes = "from 거래처 코드")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startCompanySeq")
	private CompanyInfo startCompanyInfo;
	
	//from 거래처 코너
	//@ApiModelProperty(notes = "from 거래처 코너")
	@Column(nullable = true, length = 1)
	private String startCompanyCornerCode;
	
	//일련번호
	//@ApiModelProperty(notes = "일련번호")
	private BigDecimal returnReleaseSeq;
	
	//미결 serial
	//@ApiModelProperty(notes = "미결 serial")
	private BigDecimal returnOrderSerial;
	
	//미결구분
	//@ApiModelProperty(notes = "미결구분")
	@Column(nullable = true, length = 2)
	private String returnType;
	
	//to 거래처 코드
	//@ApiModelProperty(notes = "to 거래처 코드")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endCompanySeq")
	private CompanyInfo endCompanyInfo;
	
	//to 거래처 코너
	//@ApiModelProperty(notes = "to 거래처 코너")
	@Column(nullable = true, length = 1)
	private String endCompanyCornerCode;
	
	//스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = true, length = 20)
	private String style;
	
	//나머지 제품코드
	//@ApiModelProperty(notes = "나머지 제품코드")
	@Column(nullable = true, length = 20)
	private String returnAnotherCode;
	
	//미결명세서 발행여부
	//@ApiModelProperty(notes = "미결명세서 발행여부")
	@Column(nullable = true, length = 1)
	private String returnReportYn;
	
	//미결 명세 번호
	//@ApiModelProperty(notes = "미결 명세 번호")
	private Long returnReportNo;
	
	//미결수량
	//@ApiModelProperty(notes = "미결수량")
	private Long returnAmount;
	
	//전송여부
	//@ApiModelProperty(notes = "전송여부")
	@Column(nullable = true, length = 1)
	private String transYn;
	
	//전송일시
	//@ApiModelProperty(notes = "전송일시")
	@Column(nullable = true, length = 20)
	private String transDate;
	
	//확정여부
	//@ApiModelProperty(notes = "확정여부")
	@Column(nullable = true, length = 1)
	private String completeYn;
	
	//확정일시
	//@ApiModelProperty(notes = "확정일시")
	@Column(nullable = true, length = 8)
	private String completeDate;
	
	//확정자
	//@ApiModelProperty(notes = "물류마감구분")
	@Column(nullable = true, length = 10)
	private String completeUserId;
	
	//미결확정수량
	//@ApiModelProperty(notes = "미결확정수량")
	private String returnCompleteAmount;
	
	//입고예정 정보 테이블로 이동 여부
	//@ApiModelProperty(notes = "입고예정 정보 테이블로 이동 여부")
	private String transRfidYn;
	
	//입고예정 정보 테이블로 이동한 날짜
	//@ApiModelProperty(notes = "입고예정 정보 테이블로 이동한 날짜")
	private String transRfidDate;

	
	// ErpStoreReturnSchedule 모델 복사
    public void CopyData(ErpStoreReturnSchedule param)
    {
        
    }


	public Long getErpStoreReturnScheduleSeq() {
		return erpStoreReturnScheduleSeq;
	}


	public void setErpStoreReturnScheduleSeq(Long erpStoreReturnScheduleSeq) {
		this.erpStoreReturnScheduleSeq = erpStoreReturnScheduleSeq;
	}


	public String getReturnOrderNo() {
		return returnOrderNo;
	}


	public void setReturnOrderNo(String returnOrderNo) {
		this.returnOrderNo = returnOrderNo;
	}


	public String getReturnOrderLiNo() {
		return returnOrderLiNo;
	}


	public void setReturnOrderLiNo(String returnOrderLiNo) {
		this.returnOrderLiNo = returnOrderLiNo;
	}


	public String getOrderRegDate() {
		return orderRegDate;
	}


	public void setOrderRegDate(String orderRegDate) {
		this.orderRegDate = orderRegDate;
	}


	public CompanyInfo getStartCompanyInfo() {
		return startCompanyInfo;
	}


	public void setStartCompanyInfo(CompanyInfo startCompanyInfo) {
		this.startCompanyInfo = startCompanyInfo;
	}


	public String getStartCompanyCornerCode() {
		return startCompanyCornerCode;
	}


	public void setStartCompanyCornerCode(String startCompanyCornerCode) {
		this.startCompanyCornerCode = startCompanyCornerCode;
	}


	public BigDecimal getReturnReleaseSeq() {
		return returnReleaseSeq;
	}


	public void setReturnReleaseSeq(BigDecimal returnReleaseSeq) {
		this.returnReleaseSeq = returnReleaseSeq;
	}


	public BigDecimal getReturnOrderSerial() {
		return returnOrderSerial;
	}


	public void setReturnOrderSerial(BigDecimal returnOrderSerial) {
		this.returnOrderSerial = returnOrderSerial;
	}


	public String getReturnType() {
		return returnType;
	}


	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}


	public CompanyInfo getEndCompanyInfo() {
		return endCompanyInfo;
	}


	public void setEndCompanyInfo(CompanyInfo endCompanyInfo) {
		this.endCompanyInfo = endCompanyInfo;
	}


	public String getEndCompanyCornerCode() {
		return endCompanyCornerCode;
	}


	public void setEndCompanyCornerCode(String endCompanyCornerCode) {
		this.endCompanyCornerCode = endCompanyCornerCode;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public String getReturnAnotherCode() {
		return returnAnotherCode;
	}


	public void setReturnAnotherCode(String returnAnotherCode) {
		this.returnAnotherCode = returnAnotherCode;
	}


	public String getReturnReportYn() {
		return returnReportYn;
	}


	public void setReturnReportYn(String returnReportYn) {
		this.returnReportYn = returnReportYn;
	}


	public Long getReturnReportNo() {
		return returnReportNo;
	}


	public void setReturnReportNo(Long returnReportNo) {
		this.returnReportNo = returnReportNo;
	}


	public Long getReturnAmount() {
		return returnAmount;
	}


	public void setReturnAmount(Long returnAmount) {
		this.returnAmount = returnAmount;
	}


	public String getTransYn() {
		return transYn;
	}


	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}


	public String getTransDate() {
		return transDate;
	}


	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}


	public String getCompleteYn() {
		return completeYn;
	}


	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}


	public String getCompleteDate() {
		return completeDate;
	}


	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}


	public String getCompleteUserId() {
		return completeUserId;
	}


	public void setCompleteUserId(String completeUserId) {
		this.completeUserId = completeUserId;
	}


	public String getReturnCompleteAmount() {
		return returnCompleteAmount;
	}


	public void setReturnCompleteAmount(String returnCompleteAmount) {
		this.returnCompleteAmount = returnCompleteAmount;
	}


	public String getTransRfidYn() {
		return transRfidYn;
	}


	public void setTransRfidYn(String transRfidYn) {
		this.transRfidYn = transRfidYn;
	}


	public String getTransRfidDate() {
		return transRfidDate;
	}


	public void setTransRfidDate(String transRfidDate) {
		this.transRfidDate = transRfidDate;
	}
    
    

}
