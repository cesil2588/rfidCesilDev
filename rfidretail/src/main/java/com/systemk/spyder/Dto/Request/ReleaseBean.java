package com.systemk.spyder.Dto.Request;

import java.util.List;

public class ReleaseBean {

	private Long userSeq;
	
	private String arrivalDate;
	
	private String type;
	
	private List<BoxTagListBean> boxList;
	
	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public List<BoxTagListBean> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<BoxTagListBean> boxList) {
		this.boxList = boxList;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ReleaseBean [userSeq=" + userSeq + ", arrivalDate=" + arrivalDate + ", type=" + type + ", boxList="
				+ boxList + "]";
	}
}