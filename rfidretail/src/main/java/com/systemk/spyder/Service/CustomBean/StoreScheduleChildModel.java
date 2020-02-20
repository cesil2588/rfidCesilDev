package com.systemk.spyder.Service.CustomBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StoreScheduleChildModel {

	@JsonInclude(Include.NON_DEFAULT)
	private Long boxSeq;
	
	@JsonInclude(Include.NON_NULL)
	private String barcode;
	
	@JsonInclude(Include.NON_NULL)
	private String openDbCode;
	
	@JsonInclude(Include.NON_NULL)
	private String lineNum;
	
	private String style;
	
	private String color;
	
	private String size;
	
	private String orderDegree;
	
	private String count;
	
	private List<StoreScheduleSubChildModel> StoreScheduleSubChildList;

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

	public String getOpenDbCode() {
		return openDbCode;
	}

	public void setOpenDbCode(String openDbCode) {
		this.openDbCode = openDbCode;
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

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<StoreScheduleSubChildModel> getStoreScheduleSubChildList() {
		return StoreScheduleSubChildList;
	}

	public void setStoreScheduleSubChildList(List<StoreScheduleSubChildModel> storeScheduleSubChildList) {
		StoreScheduleSubChildList = storeScheduleSubChildList;
	}

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	@Override
	public String toString() {
		return "StoreScheduleChildModel [boxSeq=" + boxSeq + ", barcode=" + barcode + ", openDbCode=" + openDbCode
				+ ", lineNum=" + lineNum + ", style=" + style + ", color=" + color + ", size=" + size + ", orderDegree="
				+ orderDegree + ", count=" + count + ", StoreScheduleSubChildList=" + StoreScheduleSubChildList + "]";
	}
}
