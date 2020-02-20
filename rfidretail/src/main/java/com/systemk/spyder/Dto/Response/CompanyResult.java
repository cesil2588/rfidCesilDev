package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.CompanyInfo;

public class CompanyResult implements Serializable{

	private static final long serialVersionUID = -6622743342455879876L;

	// 업체 일련번호
	private Long companySeq;

	// 업체 명
	private String name;

	// 업체코드
	private String customerCode;

	// 업체코너코드
	private String cornerCode;

	// 타입(1: 관리자, 2: 태그발행, 3: 생산, 4: 물류, 5: 매장, 6: 특약, 7:태그발행관리)
	private String type;

	// 전화번호
	private String telNo;

	// 사용여부
	private String useYn;

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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCornerCode() {
		return cornerCode;
	}

	public void setCornerCode(String cornerCode) {
		this.cornerCode = cornerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public CompanyResult() {

	}

	public CompanyResult(CompanyInfo companyInfo) {
		this.companySeq		= companyInfo.getCompanySeq();
		this.name			= companyInfo.getName();
		this.customerCode   = companyInfo.getCustomerCode();
		this.cornerCode     = companyInfo.getCornerCode();
		this.type           = companyInfo.getType();
		this.telNo          = companyInfo.getTelNo();
		this.useYn          = companyInfo.getUseYn();
	}
}
