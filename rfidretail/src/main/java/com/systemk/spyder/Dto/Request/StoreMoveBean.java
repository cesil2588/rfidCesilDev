package com.systemk.spyder.Dto.Request;

public class StoreMoveBean {

	private Long userSeq;
	
	private String type;
	
	private String companyType;
	
	private String returnType;
	
	private BoxBean boxInfo;
	
	private Long startCompanySeq;
	
	private Long endCompanySeq;
	
	private Long storeMoveSeq;
	
	private String explanatory;

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

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public BoxBean getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxBean boxInfo) {
		this.boxInfo = boxInfo;
	}

	public Long getStartCompanySeq() {
		return startCompanySeq;
	}

	public void setStartCompanySeq(Long startCompanySeq) {
		this.startCompanySeq = startCompanySeq;
	}

	public Long getEndCompanySeq() {
		return endCompanySeq;
	}

	public void setEndCompanySeq(Long endCompanySeq) {
		this.endCompanySeq = endCompanySeq;
	}

	public Long getStoreMoveSeq() {
		return storeMoveSeq;
	}

	public void setStoreMoveSeq(Long storeMoveSeq) {
		this.storeMoveSeq = storeMoveSeq;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}
}
