package com.systemk.spyder.Dto.Request;

import java.util.List;

public class InventoryScheduleListBean {

	private Long userSeq;
	
	private String type;
	
	private Long companySeq;
	
	private List<InventoryScheduleBean> inventoryScheduleHeaderList;

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

	public Long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}

	public List<InventoryScheduleBean> getInventoryScheduleHeaderList() {
		return inventoryScheduleHeaderList;
	}

	public void setInventoryScheduleHeaderList(List<InventoryScheduleBean> inventoryScheduleHeaderList) {
		this.inventoryScheduleHeaderList = inventoryScheduleHeaderList;
	}
}
