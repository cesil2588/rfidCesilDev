package com.systemk.spyder.Entity.OpenDb.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OpenDbScheduleHeaderKey implements Serializable{

	private static final long serialVersionUID = -4953770256390345295L;

	@Column(name="CUST_CD", length = 7)
	private String custCd;
	
	@Column(name="CUST_ORD_NO", length = 20)
	private String custOrdNo;

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getCustOrdNo() {
		return custOrdNo;
	}

	public void setCustOrdNo(String custOrdNo) {
		this.custOrdNo = custOrdNo;
	}

	
}
