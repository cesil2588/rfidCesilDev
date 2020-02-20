package com.systemk.spyder.Dto.Request;

public class StoreReturnBean {

	private Long userSeq;
	
	private String type;
	
	private String arrivalDate;
	
	private String returnType;
	
	public BoxBean boxInfo;

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public BoxBean getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxBean boxInfo) {
		this.boxInfo = boxInfo;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
