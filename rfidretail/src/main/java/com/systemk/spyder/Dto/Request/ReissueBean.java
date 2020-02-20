package com.systemk.spyder.Dto.Request;

public class ReissueBean {

	public String barcode;

	public String style;

	public String color;

	public String size;

	public String rfidSeq;

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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

}
