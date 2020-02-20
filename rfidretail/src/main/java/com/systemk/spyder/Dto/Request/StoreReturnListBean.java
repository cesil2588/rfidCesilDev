package com.systemk.spyder.Dto.Request;

import java.util.List;

public class StoreReturnListBean {

	private Long userSeq;
	
	private String type;
	
	public List<BoxBean> boxList;

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

	public List<BoxBean> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<BoxBean> boxList) {
		this.boxList = boxList;
	}
	
	
}
