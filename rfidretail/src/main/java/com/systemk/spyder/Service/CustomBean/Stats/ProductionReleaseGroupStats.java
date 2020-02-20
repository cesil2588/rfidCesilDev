package com.systemk.spyder.Service.CustomBean.Stats;

public class ProductionReleaseGroupStats {
	
	private Long amount;
	
	private Long nonConfirmAmount;
	
	private Long confirmAmount;
	
	private Long completeAmount;
	
	private Long disuseAmount;
	
	private String name;
	
	private Long companySeq;
	
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
	public Long getNonConfirmAmount() {
		return nonConfirmAmount;
	}
	public void setNonConfirmAmount(Long nonConfirmAmount) {
		this.nonConfirmAmount = nonConfirmAmount;
	}
	public Long getConfirmAmount() {
		return confirmAmount;
	}
	public void setConfirmAmount(Long confirmAmount) {
		this.confirmAmount = confirmAmount;
	}
	public Long getCompleteAmount() {
		return completeAmount;
	}
	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}
	public Long getDisuseAmount() {
		return disuseAmount;
	}
	public void setDisuseAmount(Long disuseAmount) {
		this.disuseAmount = disuseAmount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}