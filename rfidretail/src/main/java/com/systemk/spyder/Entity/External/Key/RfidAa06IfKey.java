package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidAa06IfKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5125007575683275632L;

	@Column(name="aa06_crdt")
	private String aa06Crdt;
	
	@Column(name="aa06_crsq")
	private BigDecimal aa06Crsq;
	
	@Column(name="aa06_crno")
	private BigDecimal aa06Crno;

	public String getAa06Crdt() {
		return aa06Crdt;
	}

	public void setAa06Crdt(String aa06Crdt) {
		this.aa06Crdt = aa06Crdt;
	}

	public BigDecimal getAa06Crno() {
		return aa06Crno;
	}

	public void setAa06Crno(BigDecimal aa06Crno) {
		this.aa06Crno = aa06Crno;
	}

	public BigDecimal getAa06Crsq() {
		return aa06Crsq;
	}

	public void setAa06Crsq(BigDecimal aa06Crsq) {
		this.aa06Crsq = aa06Crsq;
	}
}
