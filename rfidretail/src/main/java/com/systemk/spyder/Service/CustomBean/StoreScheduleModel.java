package com.systemk.spyder.Service.CustomBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StoreScheduleModel {

	public String resultMessage;
	
	public int resultCode;
	
	@JsonInclude(Include.NON_NULL)
	private List<StoreScheduleParentModel> storeScheduleParentList;

	public List<StoreScheduleParentModel> getStoreScheduleParentList() {
		return storeScheduleParentList;
	}

	public void setStoreScheduleParentList(List<StoreScheduleParentModel> storeScheduleParentList) {
		this.storeScheduleParentList = storeScheduleParentList;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	
}
