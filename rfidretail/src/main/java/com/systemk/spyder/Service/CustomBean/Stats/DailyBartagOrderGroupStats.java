package com.systemk.spyder.Service.CustomBean.Stats;

public class DailyBartagOrderGroupStats {
	
	private Long orderAmount;
	
	private Long nonCheckCompleteAmount;
	
	private Long completeAmount;
	
	private Long additionAmount;
	
	private Long nonCheckAdditionAmount;
	
	private String createDate;
	
	private String name;
	
	private Long companySeq;
	
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getNonCheckCompleteAmount() {
		return nonCheckCompleteAmount;
	}
	public void setNonCheckCompleteAmount(Long nonCheckCompleteAmount) {
		this.nonCheckCompleteAmount = nonCheckCompleteAmount;
	}
	public Long getCompleteAmount() {
		return completeAmount;
	}
	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}
	public Long getAdditionAmount() {
		return additionAmount;
	}
	public void setAdditionAmount(Long additionAmount) {
		this.additionAmount = additionAmount;
	}
	public Long getNonCheckAdditionAmount() {
		return nonCheckAdditionAmount;
	}
	public void setNonCheckAdditionAmount(Long nonCheckAdditionAmount) {
		this.nonCheckAdditionAmount = nonCheckAdditionAmount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}