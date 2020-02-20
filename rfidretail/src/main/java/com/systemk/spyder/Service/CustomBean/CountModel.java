package com.systemk.spyder.Service.CustomBean;

public class CountModel {

	private Long amount;
	private Long total_amount;
	private Long stat1_amount;
	private Long stat2_amount;
	private Long stat3_amount;
	private Long stat4_amount;
	private Long stat5_amount;
	private Long stat6_amount;
	private Long stat7_amount;
	
	public Long getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Long total_amount) {
		this.total_amount = total_amount;
	}
	public Long getStat1_amount() {
		return stat1_amount;
	}
	public void setStat1_amount(Long stat1_amount) {
		this.stat1_amount = stat1_amount;
	}
	public Long getStat2_amount() {
		return stat2_amount;
	}
	public void setStat2_amount(Long stat2_amount) {
		this.stat2_amount = stat2_amount;
	}
	public Long getStat3_amount() {
		return stat3_amount;
	}
	public void setStat3_amount(Long stat3_amount) {
		this.stat3_amount = stat3_amount;
	}
	public Long getStat4_amount() {
		return stat4_amount;
	}
	public void setStat4_amount(Long stat4_amount) {
		this.stat4_amount = stat4_amount;
	}
	public Long getStat5_amount() {
		return stat5_amount;
	}
	public void setStat5_amount(Long stat5_amount) {
		this.stat5_amount = stat5_amount;
	}
	public Long getStat6_amount() {
		return stat6_amount;
	}
	public void setStat6_amount(Long stat6_amount) {
		this.stat6_amount = stat6_amount;
	}
	public Long getStat7_amount() {
		return stat7_amount;
	}
	public void setStat7_amount(Long stat7_amount) {
		this.stat7_amount = stat7_amount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public CountModel(Long stat1_amount, Long stat2_amount, Long stat3_amount, Long stat4_amount, Long stat5_amount, Long stat6_amount, Long stat7_amount) {
		super();
		this.stat1_amount = stat1_amount;
		this.stat2_amount = stat2_amount;
		this.stat3_amount = stat3_amount;
		this.stat4_amount = stat4_amount;
		this.stat5_amount = stat5_amount;
		this.stat6_amount = stat6_amount;
		this.stat7_amount = stat7_amount;
	}
	public CountModel() {
		super();
	}
}
