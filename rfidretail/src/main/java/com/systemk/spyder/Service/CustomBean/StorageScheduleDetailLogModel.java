package com.systemk.spyder.Service.CustomBean;

import java.util.Set;

public class StorageScheduleDetailLogModel {

	private String barcode;
	
	private Long lineNum;

	private String style;
	
	private String color;
	
	private String size;
	
	private String orderDegree;
	
	private Long amount;
	
	private Set<StorageScheduleSubDetailLogModel> storageScheduleSubDetailLogModel;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getLineNum() {
		return lineNum;
	}

	public void setLineNum(Long lineNum) {
		this.lineNum = lineNum;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Set<StorageScheduleSubDetailLogModel> getStorageScheduleSubDetailLogModel() {
		return storageScheduleSubDetailLogModel;
	}

	public void setStorageScheduleSubDetailLogModel(Set<StorageScheduleSubDetailLogModel> storageScheduleSubDetailLogModel) {
		this.storageScheduleSubDetailLogModel = storageScheduleSubDetailLogModel;
	}
	
	
}
