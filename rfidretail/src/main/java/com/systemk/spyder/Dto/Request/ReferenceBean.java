package com.systemk.spyder.Dto.Request;

import java.util.List;

public class ReferenceBean {
	
	public String custOrderTypeCd;
	
	public List<ReferenceBoxListBean> barcodeList;
	
	public List<ReferenceBoxListBean> getBarcodeList() {
		return barcodeList;
	}

	public void setBarcodeList(List<ReferenceBoxListBean> barcodeList) {
		this.barcodeList = barcodeList;
	}

	public String getCustOrderTypeCd() {
		return custOrderTypeCd;
	}

	public void setCustOrderTypeCd(String custOrderTypeCd) {
		this.custOrderTypeCd = custOrderTypeCd;
	}

}
