package com.systemk.spyder.Dto.Response;

public class ApiListResult {
	
	public String resultMessage;
	
	public int resultCode;
	
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
	
	public ApiListResult(String resultMessage, int resultCode) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
	}

}
