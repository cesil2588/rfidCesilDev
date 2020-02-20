package com.systemk.spyder.Dto.Request;

import java.util.List;

public class StoreMoveSendBean {
	
	private String fromCustomerCode;
	
	private String fromCornerCode;
	
	private String toCustomerCode;
	
	private String toCornerCode;

	private List<StoreMoveDetailSendBean> detailBeanList;

	public String getFromCustomerCode() {
		return fromCustomerCode;
	}

	public void setFromCustomerCode(String fromCustomerCode) {
		this.fromCustomerCode = fromCustomerCode;
	}

	public String getFromCornerCode() {
		return fromCornerCode;
	}

	public void setFromCornerCode(String fromCornerCode) {
		this.fromCornerCode = fromCornerCode;
	}

	public String getToCustomerCode() {
		return toCustomerCode;
	}

	public void setToCustomerCode(String toCustomerCode) {
		this.toCustomerCode = toCustomerCode;
	}

	public String getToCornerCode() {
		return toCornerCode;
	}

	public void setToCornerCode(String toCornerCode) {
		this.toCornerCode = toCornerCode;
	}

	public List<StoreMoveDetailSendBean> getDetailBeanList() {
		return detailBeanList;
	}

	public void setDetailBeanList(List<StoreMoveDetailSendBean> detailBeanList) {
		this.detailBeanList = detailBeanList;
	}

}
