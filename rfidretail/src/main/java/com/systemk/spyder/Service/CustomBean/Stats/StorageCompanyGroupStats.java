package com.systemk.spyder.Service.CustomBean.Stats;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StorageCompanyGroupStats {

	private Long totalAmount;
	
	@JsonInclude(Include.NON_NULL)
	private Long confirmAmount;
	
	@JsonInclude(Include.NON_NULL)
	private Long releaseAmount;
	
	private Long completeAmount;
	
	private String companyName;
	
	private Long companySeq;

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
	}
	
	
}
