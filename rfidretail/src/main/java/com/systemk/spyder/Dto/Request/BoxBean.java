package com.systemk.spyder.Dto.Request;

import java.util.List;

public class BoxBean {

	private String barcode;

	private List<StyleBean> styleList;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public List<StyleBean> getStyleList() {
		return styleList;
	}

	public void setStyleList(List<StyleBean> styleList) {
		this.styleList = styleList;
	}

}
