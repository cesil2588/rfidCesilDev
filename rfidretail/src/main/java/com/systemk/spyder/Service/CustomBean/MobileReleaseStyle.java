package com.systemk.spyder.Service.CustomBean;

public class MobileReleaseStyle {

	private Long productionStorageSeq;
	
	private Long stockAmount;
	
	private Long releaseAmount;
	
	private String style;
	
	private String color;
	
	private String size;
	
	private String orderDegree;
	
	private String additionOrderDegree;

	public Long getProductionStorageSeq() {
		return productionStorageSeq;
	}

	public void setProductionStorageSeq(Long productionStorageSeq) {
		this.productionStorageSeq = productionStorageSeq;
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

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getAdditionOrderDegree() {
		return additionOrderDegree;
	}

	public void setAdditionOrderDegree(String additionOrderDegree) {
		this.additionOrderDegree = additionOrderDegree;
	}

	public Long getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Long stockAmount) {
		this.stockAmount = stockAmount;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
	}
}
