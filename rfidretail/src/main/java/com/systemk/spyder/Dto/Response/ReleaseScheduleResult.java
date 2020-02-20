package com.systemk.spyder.Dto.Response;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;

public class ReleaseScheduleResult {

	private String startCompanyName;

	private String startCustomerCode;

	private Long startCompanySeq;

	private String endCompanyName;

	private String endCustomerCode;

	private Long endCompanySeq;

	private Long orderAmount;

	private Long sortingAmount;

	private Long stockAmount;

	private String rfidYn;

	private String referenceNo;

	private String style;

	private String color;

	private String size;

	private String erpKey;

	private String startRfidSeq;

	private String endRfidSeq;

	public String getStartCompanyName() {
		return startCompanyName;
	}

	public void setStartCompanyName(String startCompanyName) {
		this.startCompanyName = startCompanyName;
	}

	public String getStartCustomerCode() {
		return startCustomerCode;
	}

	public void setStartCustomerCode(String startCustomerCode) {
		this.startCustomerCode = startCustomerCode;
	}

	public Long getStartCompanySeq() {
		return startCompanySeq;
	}

	public void setStartCompanySeq(Long startCompanySeq) {
		this.startCompanySeq = startCompanySeq;
	}

	public String getEndCompanyName() {
		return endCompanyName;
	}

	public void setEndCompanyName(String endCompanyName) {
		this.endCompanyName = endCompanyName;
	}

	public String getEndCustomerCode() {
		return endCustomerCode;
	}

	public void setEndCustomerCode(String endCustomerCode) {
		this.endCustomerCode = endCustomerCode;
	}

	public Long getEndCompanySeq() {
		return endCompanySeq;
	}

	public void setEndCompanySeq(Long endCompanySeq) {
		this.endCompanySeq = endCompanySeq;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getSortingAmount() {
		return sortingAmount;
	}

	public void setSortingAmount(Long sortingAmount) {
		this.sortingAmount = sortingAmount;
	}

	public Long getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Long stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getStartRfidSeq() {
		return startRfidSeq;
	}

	public void setStartRfidSeq(String startRfidSeq) {
		this.startRfidSeq = startRfidSeq;
	}

	public String getEndRfidSeq() {
		return endRfidSeq;
	}

	public void setEndRfidSeq(String endRfidSeq) {
		this.endRfidSeq = endRfidSeq;
	}

	public ReleaseScheduleResult() {

	}

	public ReleaseScheduleResult(ErpStoreSchedule erpSchedule) {
		this.startCompanyName =  erpSchedule.getStartCompanyInfo().getName();
		this.startCustomerCode = erpSchedule.getStartCompanyInfo().getCustomerCode();
		this.startCompanySeq = erpSchedule.getStartCompanyInfo().getCompanySeq();
		this.endCompanyName = erpSchedule.getEndCompanyInfo().getName();
		this.endCustomerCode = erpSchedule.getEndCompanyInfo().getCustomerCode();
		this.endCompanySeq = erpSchedule.getEndCompanyInfo().getCompanySeq();
		this.orderAmount = erpSchedule.getOrderAmount();
		this.sortingAmount = erpSchedule.getSortingAmount();
		this.rfidYn = erpSchedule.getRfidYn();
		this.referenceNo = erpSchedule.getReferenceNo();
		this.style = erpSchedule.getStyle();
		this.color = erpSchedule.getColor();
		this.size = erpSchedule.getSize();
	}

	public void CopyData(BartagMinMaxResult minMaxResult) {
		this.erpKey = minMaxResult.getErpKey();
		this.startRfidSeq = minMaxResult.getStartRfidSeq();
		this.endRfidSeq = minMaxResult.getEndRfidSeq();
	}
}
