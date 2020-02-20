package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidAc16IfKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5125007575683275632L;

	@Column(name="ac16_crdt")
	private String ac16Crdt;
	
	@Column(name="ac16_crsq")
	private BigDecimal ac16Crsq;
	
	@Column(name="ac16_crno")
	private BigDecimal ac16Crno;

	public String getAc16Crdt() {
		return ac16Crdt;
	}

	public void setAc16Crdt(String ac16Crdt) {
		this.ac16Crdt = ac16Crdt;
	}

	public BigDecimal getAc16Crno() {
		return ac16Crno;
	}

	public void setAc16Crno(BigDecimal ac16Crno) {
		this.ac16Crno = ac16Crno;
	}

	public BigDecimal getAc16Crsq() {
		return ac16Crsq;
	}

	public void setAc16Crsq(BigDecimal ac16Crsq) {
		this.ac16Crsq = ac16Crsq;
	}
}
