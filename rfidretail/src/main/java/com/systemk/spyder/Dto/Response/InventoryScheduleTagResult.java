package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.InventoryScheduleTag;

/**
 * 재고조사 태그 DTO
 * @author escho
 *
 */
public class InventoryScheduleTagResult implements Serializable{

	private static final long serialVersionUID = -1726647866960759285L;

	private String rfidTag;

	private String barcode;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public InventoryScheduleTagResult() {

	}

	public InventoryScheduleTagResult(InventoryScheduleTag tag) {
		this.rfidTag = tag.getRfidTag();
		this.barcode = tag.getBarcode();
	}
}
