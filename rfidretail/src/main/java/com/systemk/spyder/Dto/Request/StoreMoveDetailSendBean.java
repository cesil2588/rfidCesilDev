package com.systemk.spyder.Dto.Request;

public class StoreMoveDetailSendBean {

	private String style;
	
	private String color;
	
	private String size;
	
	private Long orderAmount;
	
	private String rfidYn;
	
	private Long regUserSeq;

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

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public Long getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Long regUserSeq) {
		this.regUserSeq = regUserSeq;
	}
	
	
}
