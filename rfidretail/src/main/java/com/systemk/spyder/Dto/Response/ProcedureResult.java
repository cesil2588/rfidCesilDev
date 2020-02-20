package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

public class ProcedureResult implements Serializable{

	/**
	 * 프로시저 결과값을 받아오기 위한 DTO
	 */
	private static final long serialVersionUID = 4934099951352503121L;
	
	//미결일자
	private String completeDate;
	
	//미결번호
	private Long completeSequence;
	
	//에러코드
	private Long errorCode;
	
	//에러메세지
	private String errorMsg;

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public Long getCompleteSequence() {
		return completeSequence;
	}

	public void setCompleteSequence(Long completeSequence) {
		this.completeSequence = completeSequence;
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
