package com.systemk.spyder.Service.CustomBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StorageScheduleModel {

	@JsonInclude(Include.NON_NULL)
	private String createDate;
	
	@JsonInclude(Include.NON_NULL)
	private String workLine;
	
	@JsonInclude(Include.NON_DEFAULT)
	private Long startCompanySeq;
	
	@JsonInclude(Include.NON_NULL)
	private String companyName;
	
	@JsonInclude(Include.NON_NULL)
	private String arrivalDate;
	
	private Long stat1BoxCount;
	
	private Long stat2BoxCount;
	
	private Long stat3BoxCount;
	
	private Long stat1StyleCount;
	
	private Long stat2StyleCount;
	
	private Long stat3StyleCount;
	
	private Long stat1TagCount;
	
	private Long stat2TagCount;
	
	private Long stat3TagCount;
	
	@JsonInclude(Include.NON_NULL)
	private String returnType;
	
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getWorkLine() {
		return workLine;
	}

	public void setWorkLine(String workLine) {
		this.workLine = workLine;
	}

	public Long getStartCompanySeq() {
		return startCompanySeq;
	}

	public void setStartCompanySeq(Long startCompanySeq) {
		this.startCompanySeq = startCompanySeq;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Long getStat1BoxCount() {
		return stat1BoxCount;
	}

	public void setStat1BoxCount(Long stat1BoxCount) {
		this.stat1BoxCount = stat1BoxCount;
	}

	public Long getStat2BoxCount() {
		return stat2BoxCount;
	}

	public void setStat2BoxCount(Long stat2BoxCount) {
		this.stat2BoxCount = stat2BoxCount;
	}

	public Long getStat1StyleCount() {
		return stat1StyleCount;
	}

	public void setStat1StyleCount(Long stat1StyleCount) {
		this.stat1StyleCount = stat1StyleCount;
	}

	public Long getStat2StyleCount() {
		return stat2StyleCount;
	}

	public void setStat2StyleCount(Long stat2StyleCount) {
		this.stat2StyleCount = stat2StyleCount;
	}

	public Long getStat1TagCount() {
		return stat1TagCount;
	}

	public void setStat1TagCount(Long stat1TagCount) {
		this.stat1TagCount = stat1TagCount;
	}

	public Long getStat2TagCount() {
		return stat2TagCount;
	}

	public void setStat2TagCount(Long stat2TagCount) {
		this.stat2TagCount = stat2TagCount;
	}

	public Long getStat3BoxCount() {
		return stat3BoxCount;
	}

	public void setStat3BoxCount(Long stat3BoxCount) {
		this.stat3BoxCount = stat3BoxCount;
	}

	public Long getStat3StyleCount() {
		return stat3StyleCount;
	}

	public void setStat3StyleCount(Long stat3StyleCount) {
		this.stat3StyleCount = stat3StyleCount;
	}

	public Long getStat3TagCount() {
		return stat3TagCount;
	}

	public void setStat3TagCount(Long stat3TagCount) {
		this.stat3TagCount = stat3TagCount;
	}
}
