package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;

public class ReissueDetailResult implements Serializable{

	private static final long serialVersionUID = 1317181908911799522L;

	// RFID 바코드
	private String barcode;

	// ERP 키
	private String erpKey;

	// 스타일
	private String style;

	// 컬러
	private String color;

	// 사이즈
	private String size;

	// RFID Seq
	private String rfidSeq;

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

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public ReissueDetailResult() {

	}

	public ReissueDetailResult(RfidTagReissueRequestDetail obj) {
		this.barcode        = obj.getRfidTag().substring(0, 10).concat(obj.getRfidTag().substring(27, 32));
		this.erpKey			= obj.getRfidTag().substring(0, 10);
		this.style          = obj.getStyle();
		this.color          = obj.getColor();
		this.size           = obj.getSize();
		this.rfidSeq        = obj.getRfidSeq();
	}
}
