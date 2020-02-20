package com.systemk.spyder.Service.CustomBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StoreScheduleSubChildModel {

	@JsonInclude(Include.NON_DEFAULT)
	private Long boxSeq;
	
	@JsonInclude(Include.NON_NULL)
	private String barcode;
	
	@JsonInclude(Include.NON_NULL)
	private String openDbCode;
	
	@JsonInclude(Include.NON_NULL)
	private String lineNum;
	
	@JsonInclude(Include.NON_NULL)
	private String style;
	
	@JsonInclude(Include.NON_NULL)
	private String color;
	
	@JsonInclude(Include.NON_NULL)
	private String size;
	
	@JsonInclude(Include.NON_NULL)
	private String orderDegree;
	
	private String rfidTag;
	
	@JsonInclude(Include.NON_DEFAULT)
	private Long distributionStorageSeq;
	
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

	public Long getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Long boxSeq) {
		this.boxSeq = boxSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getDistributionStorageSeq() {
		return distributionStorageSeq;
	}

	public void setDistributionStorageSeq(Long distributionStorageSeq) {
		this.distributionStorageSeq = distributionStorageSeq;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getOpenDbCode() {
		return openDbCode;
	}

	public void setOpenDbCode(String openDbCode) {
		this.openDbCode = openDbCode;
	}

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	@Override
	public String toString() {
		return "StoreScheduleSubChildModel [boxSeq=" + boxSeq + ", barcode=" + barcode + ", openDbCode=" + openDbCode
				+ ", lineNum=" + lineNum + ", style=" + style + ", color=" + color + ", size=" + size + ", orderDegree="
				+ orderDegree + ", rfidTag=" + rfidTag + ", distributionStorageSeq=" + distributionStorageSeq + "]";
	}
	

}
