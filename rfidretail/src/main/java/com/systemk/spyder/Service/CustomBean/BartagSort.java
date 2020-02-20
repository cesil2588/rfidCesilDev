package com.systemk.spyder.Service.CustomBean;

import java.util.Date;

public class BartagSort {

	private Long bartagSeq;
	private Long amount;
	private Long companySeq;
	private String name;
	private String detailProductionCompanyName;
	private String style;
	private String color;
	private String size;
	private String orderDegree;
	private Date regDate;
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getDetailProductionCompanyName() {
		return detailProductionCompanyName;
	}
	public void setDetailProductionCompanyName(String detailProductionCompanyName) {
		this.detailProductionCompanyName = detailProductionCompanyName;
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
	public Long getBartagSeq() {
		return bartagSeq;
	}
	public void setBartagSeq(Long bartagSeq) {
		this.bartagSeq = bartagSeq;
	}
	
	public String getOrderDegree() {
		return orderDegree;
	}
	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Long getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BartagSort [bartagSeq=" + bartagSeq + ", amount=" + amount + ", companySeq=" + companySeq + ", name="
				+ name + ", detailProductionCompanyName=" + detailProductionCompanyName + ", style=" + style
				+ ", color=" + color + ", size=" + size + ", orderDegree=" + orderDegree + ", regDate=" + regDate + "]";
	}
	
}
