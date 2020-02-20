package com.systemk.spyder.Dto;

public class ErpStoreMoveNoBean {

	//이동지시일자
	private String orderRegDate;
	
	//이동지시 sequence
	private Long orderSeq;
	
	//이동지시 serial
	private Long orderSerial;
	
	//이동지시 from 매장코드
	private String fromCustomerCode;
	
	//이동지시 from 매장코너코드
	private String fromCornerCode;
	
	//이동지시 to 매장코드
	private String toCustomerCode;
	
	//이동지시 to 매장코너코드
	private String toCornerCode;
	
	//지시수량
	private Long orderAmount;

	public String getOrderRegDate() {
		return orderRegDate;
	}

	public void setOrderRegDate(String orderRegDate) {
		this.orderRegDate = orderRegDate;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Long getOrderSerial() {
		return orderSerial;
	}

	public void setOrderSerial(Long orderSerial) {
		this.orderSerial = orderSerial;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
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
	
	
}
