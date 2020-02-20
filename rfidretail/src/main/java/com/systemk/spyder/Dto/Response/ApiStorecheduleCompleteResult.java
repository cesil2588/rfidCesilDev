package com.systemk.spyder.Dto.Response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiStorecheduleCompleteResult {
	
	public String resultMessage;
	
	public int resultCode;
	
	@JsonInclude(Include.NON_NULL)
	public Map<String, Object> storeScheduleBatch;
	
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Map<String, Object> getStoreScheduleBatch() {
		return storeScheduleBatch;
	}

	public void setStoreScheduleBatch(Map<String, Object> storeScheduleBatch) {
		this.storeScheduleBatch = storeScheduleBatch;
	}
	
	public ApiStorecheduleCompleteResult(){
		
	}

	public ApiStorecheduleCompleteResult(String resultMessage, int resultCode) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
	}
	
	public ApiStorecheduleCompleteResult(String resultMessage, int resultCode, Map<String, Object> storeScheduleBatch) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
		this.storeScheduleBatch = storeScheduleBatch;
	}
}
