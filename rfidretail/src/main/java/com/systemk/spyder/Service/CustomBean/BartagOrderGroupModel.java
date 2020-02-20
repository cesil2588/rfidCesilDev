package com.systemk.spyder.Service.CustomBean;

public class BartagOrderGroupModel {

	private String createDate;
	
	private String productYy;
	
	private String productSeason;
	
	private String name;
	
	private Long companySeq;
	
	private Long orderAmount;
	
	private Long completeAmount;
	
	private Long nonCheckCompleteAmount;
	
	private Long additionAmount;
	
	private Long nonCheckAdditionAmount;
	
	private Long stat1Amount;
	
	private Long stat2Amount;
	
	private Long batchPercent;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getProductYy() {
		return productYy;
	}

	public void setProductYy(String productYy) {
		this.productYy = productYy;
	}

	public String getProductSeason() {
		return productSeason;
	}

	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public Long getBatchPercent() {
		return batchPercent;
	}

	public void setBatchPercent(Long batchPercent) {
		this.batchPercent = batchPercent;
	}

	public Long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}

	public Long getStat1Amount() {
		return stat1Amount;
	}

	public void setStat1Amount(Long stat1Amount) {
		this.stat1Amount = stat1Amount;
	}

	public Long getStat2Amount() {
		return stat2Amount;
	}

	public void setStat2Amount(Long stat2Amount) {
		this.stat2Amount = stat2Amount;
	}

	public Long getAdditionAmount() {
		return additionAmount;
	}

	public void setAdditionAmount(Long additionAmount) {
		this.additionAmount = additionAmount;
	}

	public Long getNonCheckCompleteAmount() {
		return nonCheckCompleteAmount;
	}

	public void setNonCheckCompleteAmount(Long nonCheckCompleteAmount) {
		this.nonCheckCompleteAmount = nonCheckCompleteAmount;
	}

	public Long getNonCheckAdditionAmount() {
		return nonCheckAdditionAmount;
	}

	public void setNonCheckAdditionAmount(Long nonCheckAdditionAmount) {
		this.nonCheckAdditionAmount = nonCheckAdditionAmount;
	}
	
	
}
