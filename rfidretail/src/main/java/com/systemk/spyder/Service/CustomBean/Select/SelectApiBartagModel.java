package com.systemk.spyder.Service.CustomBean.Select;

import java.util.List;

import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public class SelectApiBartagModel {
	
	private String data;
	
	private List<SelectBartagModel> detailList;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<SelectBartagModel> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SelectBartagModel> detailList) {
		this.detailList = detailList;
	}

}
