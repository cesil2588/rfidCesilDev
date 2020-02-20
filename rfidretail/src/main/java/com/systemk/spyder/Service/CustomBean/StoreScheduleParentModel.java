package com.systemk.spyder.Service.CustomBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StoreScheduleParentModel {

	@JsonInclude(Include.NON_DEFAULT)
	private Long boxSeq;
	
	private String barcode;
	
	private String openDbCode;
	
	private String startCompanyName;
	
	private String endCompanyName;
	
	private String type;
	
	private String arrivalDate;
	
	private String stat;
	
	@JsonInclude(Include.NON_DEFAULT)
	private String completeDate;
	
	private List<StoreScheduleChildModel> storeScheduleChildList;

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

	public List<StoreScheduleChildModel> getStoreScheduleChildList() {
		return storeScheduleChildList;
	}

	public void setStoreScheduleChildList(List<StoreScheduleChildModel> storeScheduleChildList) {
		this.storeScheduleChildList = storeScheduleChildList;
	}
	
	public String getStartCompanyName() {
		return startCompanyName;
	}

	public void setStartCompanyName(String startCompanyName) {
		this.startCompanyName = startCompanyName;
	}

	public String getEndCompanyName() {
		return endCompanyName;
	}

	public void setEndCompanyName(String endCompanyName) {
		this.endCompanyName = endCompanyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return "StoreScheduleParentModel [boxSeq=" + boxSeq + ", barcode=" + barcode + ", openDbCode=" + openDbCode
				+ ", startCompanyName=" + startCompanyName + ", endCompanyName=" + endCompanyName + ", type=" + type
				+ ", arrivalDate=" + arrivalDate + ", stat=" + stat + ", storeScheduleChildList="
				+ storeScheduleChildList + "]";
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}
}
