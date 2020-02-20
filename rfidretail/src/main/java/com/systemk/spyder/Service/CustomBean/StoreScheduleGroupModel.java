package com.systemk.spyder.Service.CustomBean;

public class StoreScheduleGroupModel {

	public String completeDate;
	
	public String completeType;
	
	public String completeSeq;
	
	public String style;
	
	public String color;
	
	public String size;
	
	public Long orderAmount;
	
	public Long releaseAmount;
	
	private Long batchPercent;

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompleteType() {
		return completeType;
	}

	public void setCompleteType(String completeType) {
		this.completeType = completeType;
	}

	public String getCompleteSeq() {
		return completeSeq;
	}

	public void setCompleteSeq(String completeSeq) {
		this.completeSeq = completeSeq;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
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

	public Long getBatchPercent() {
		return batchPercent;
	}

	public void setBatchPercent(Long batchPercent) {
		this.batchPercent = batchPercent;
	}
}
