package com.systemk.spyder.Dto.Request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BoxTagListBean {

	@JsonInclude(Include.NON_DEFAULT)
	private Long boxSeq;
	
	@JsonInclude(Include.NON_NULL)
	private String barcode;
	
	@JsonInclude(Include.NON_NULL)
	private String orderType;
	
	@JsonInclude(Include.NON_NULL)
	private String arrivalCustomerCd;
	
	@JsonInclude(Include.NON_NULL)
	private String invoiceNum;
	
	@JsonInclude(Include.NON_DEFAULT)
	private int boxResultCode;
	
	@JsonInclude(Include.NON_NULL)
	private String boxResultMessage;
	
	@JsonInclude(Include.NON_NULL)
	private String stat;
	
	@JsonInclude(Include.NON_NULL)
	private List<RfidTagBean> rfidTagList;

	public Long getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Long boxSeq) {
		this.boxSeq = boxSeq;
	}
	public List<RfidTagBean> getRfidTagList() {
		return rfidTagList;
	}

	public void setRfidTagList(List<RfidTagBean> rfidTagList) {
		this.rfidTagList = rfidTagList;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getArrivalCustomerCd() {
		return arrivalCustomerCd;
	}

	public void setArrivalCustomerCd(String arrivalCustomerCd) {
		this.arrivalCustomerCd = arrivalCustomerCd;
	}

	public int getBoxResultCode() {
		return boxResultCode;
	}

	public void setBoxResultCode(int boxResultCode) {
		this.boxResultCode = boxResultCode;
	}

	public String getBoxResultMessage() {
		return boxResultMessage;
	}

	public void setBoxResultMessage(String boxResultMessage) {
		this.boxResultMessage = boxResultMessage;
	}

	@Override
	public String toString() {
		return "BoxTagListBean [boxSeq=" + boxSeq + ", barcode=" + barcode + ", orderType=" + orderType
				+ ", arrivalCustomerCd=" + arrivalCustomerCd + ", invoiceNum=" + invoiceNum + ", boxResultCode="
				+ boxResultCode + ", boxResultMessage=" + boxResultMessage + ", rfidTagList=" + rfidTagList + "]";
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
}
