package com.systemk.spyder.Dto.Request;

import java.util.Date;

public class ErpStoreReturnNoBean {

	//지시 목록에서 지시번호별 merge를 위해 존재하는 Bean
	//erp지시번호
	private Long erpReturnNo;
	
	//등록일자
	private Date erpRegDate;
	
	//확정일자
	private Date returnConfirmDate;
	
	//지시 제목
	private String returnTitle;
	
	//지시 구분
	private String returnType;
	
	//이행수량
	private Long orderAmount;
	
	//from 매장코드
	private String fromCustomerCode;
	
	//from 매장 코너코드
	private String fromCornerCode;
	
	//to 매장코드
	private String toCustomerCode;
	
	//to 매장 코너코드
	private String toCornerCode;
	
	//rfid yn
	private String rfidYn;

	public Long getErpReturnNo() {
		return erpReturnNo;
	}

	public void setErpReturnNo(Long erpReturnNo) {
		this.erpReturnNo = erpReturnNo;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getErpRegDate() {
		return erpRegDate;
	}

	public void setErpRegDate(Date erpRegDate) {
		this.erpRegDate = erpRegDate;
	}

	public Date getReturnConfirmDate() {
		return returnConfirmDate;
	}

	public void setReturnConfirmDate(Date returnConfirmDate) {
		this.returnConfirmDate = returnConfirmDate;
	}

	public String getReturnTitle() {
		return returnTitle;
	}

	public void setReturnTitle(String returnTitle) {
		this.returnTitle = returnTitle;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getFromCustomerCode() {
		return fromCustomerCode;
	}

	public void setFromCustomerCode(String fromCustomerCode) {
		this.fromCustomerCode = fromCustomerCode;
	}

	public String getFromCornerCode() {
		return fromCornerCode;
	}

	public void setFromCornerCode(String fromCornerCode) {
		this.fromCornerCode = fromCornerCode;
	}

	public String getToCustomerCode() {
		return toCustomerCode;
	}

	public void setToCustomerCode(String toCustomerCode) {
		this.toCustomerCode = toCustomerCode;
	}

	public String getToCornerCode() {
		return toCornerCode;
	}

	public void setToCornerCode(String toCornerCode) {
		this.toCornerCode = toCornerCode;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}
		
}
