package com.systemk.spyder.Dto.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DistributionReleaseCompleteBean {

	private Long userSeq;

	@JsonInclude(Include.NON_NULL)
	private String referenceNo;

	@JsonInclude(Include.NON_NULL)
	private String type;

	@JsonInclude(Include.NON_NULL)
	private String completeYn;

	public BoxBean boxInfo;

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BoxBean getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxBean boxInfo) {
		this.boxInfo = boxInfo;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}
}
