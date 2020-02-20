package com.systemk.spyder.Entity.OpenDb.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OpenDbScheduleDetailKey implements Serializable{

	private static final long serialVersionUID = 695624150339469176L;

	@Column(name="CUST_CD", length = 7)
	private String custCd;
	
	@Column(name="CUST_ORD_NO", length = 20)
	private String custOrdNo;
	
	@Column(name="CUST_ORD_LINE_NO", length = 15)
	private String custOrdLineNo;

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

	public String getCustOrdLineNo() {
		return custOrdLineNo;
	}

	public void setCustOrdLineNo(String custOrdLineNo) {
		this.custOrdLineNo = custOrdLineNo;
	}

}
