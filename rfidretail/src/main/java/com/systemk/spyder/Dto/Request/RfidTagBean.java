package com.systemk.spyder.Dto.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RfidTagBean {

	@JsonInclude(Include.NON_NULL)
	private String rfidTag;

	@JsonInclude(Include.NON_NULL)
	private String barcode;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	@Override
	public String toString() {
		return "RfidTagBean [rfidTag=" + rfidTag + "]";
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}