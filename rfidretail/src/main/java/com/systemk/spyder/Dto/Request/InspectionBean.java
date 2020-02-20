package com.systemk.spyder.Dto.Request;

import java.util.List;

public class InspectionBean {
	
	private Long userSeq;
	
	private List<ProductionTagListBean> inspectionList;

	public List<ProductionTagListBean> getInspectionList() {
		return inspectionList;
	}

	public void setInspectionList(List<ProductionTagListBean> inspectionList) {
		this.inspectionList = inspectionList;
	}

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	@Override
	public String toString() {
		return "InspectionBean [userSeq=" + userSeq + ", inspectionList=" + inspectionList + "]";
	}
}
