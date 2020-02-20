package com.systemk.spyder.Service.CustomBean.Stats;

import java.util.ArrayList;
import java.util.List;

public class ReleaseCompanyGroupStats {

	private String companyName;
	
	private Long companySeq;
	
	private List<ReleaseCompanyDetailGroupStats> detailList = new ArrayList<ReleaseCompanyDetailGroupStats>();

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

	public List<ReleaseCompanyDetailGroupStats> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ReleaseCompanyDetailGroupStats> detailList) {
		this.detailList = detailList;
	}
}
