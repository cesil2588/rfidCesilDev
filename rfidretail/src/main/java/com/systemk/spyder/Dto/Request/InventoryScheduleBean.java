package com.systemk.spyder.Dto.Request;

import java.util.List;

public class InventoryScheduleBean {

	private Long userSeq;
	
	private String type;
	
	private String companyType;
	
	private Long companySeq;
	
	private Long inventoryScheduleHeaderSeq;
	
	private String explanatory;
	
	private List<StyleBean> styleList;

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

	public Long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}

	public List<StyleBean> getStyleList() {
		return styleList;
	}

	public void setStyleList(List<StyleBean> styleList) {
		this.styleList = styleList;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public Long getInventoryScheduleHeaderSeq() {
		return inventoryScheduleHeaderSeq;
	}

	public void setInventoryScheduleHeaderSeq(Long inventoryScheduleHeaderSeq) {
		this.inventoryScheduleHeaderSeq = inventoryScheduleHeaderSeq;
	}
}
