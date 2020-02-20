package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.ReleaseScheduleSubDetailLog;

/**
 * 매장입고예정 태그 정보
 * @author escho
 *
 */
public class StoreScheduleTagResult implements Serializable{

	private static final long serialVersionUID = 3891030313691678139L;
	
	private String rfidTag;
	
	private String barcode;
	
	// 플래그(T: 신규)
	@JsonInclude(Include.NON_NULL)
	private String flag;

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
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	public StoreScheduleTagResult() {
		
	}
	
	public StoreScheduleTagResult(ReleaseScheduleSubDetailLog param) {
		this.rfidTag = param.getRfidTag();
		this.barcode = param.getRfidTag().substring(0, 10).concat(param.getRfidTag().substring(27, 32));
	}
}
