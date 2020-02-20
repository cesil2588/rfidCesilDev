package com.systemk.spyder.Service.CustomBean;


public class ReturnBoxModel {

	//반품 미결번호
	private String barcode;
	
	//미결 수량
	private int amount;
	
	//스타일
	private String style;
	
	//컬러
	private String color;
	
	//사이즈
	private String size;
	
	//반품 실적 반영일
	private String completeDate;
	
	//반품 매장명
	private String companyName;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
