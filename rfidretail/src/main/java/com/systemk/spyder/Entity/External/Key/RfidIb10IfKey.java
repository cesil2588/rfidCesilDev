package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidIb10IfKey implements Serializable{

	private static final long serialVersionUID = 2205729863639224828L;

	// 박스번호
	@Column(name="lb10_bxno")
	private String lb10Bxno;
	
	// 순번
	@Column(name="lb10_bxsq")
	private BigDecimal lb10Bxsq;

	public String getLb10Bxno() {
		return lb10Bxno;
	}

	public void setLb10Bxno(String lb10Bxno) {
		this.lb10Bxno = lb10Bxno;
	}

	public BigDecimal getLb10Bxsq() {
		return lb10Bxsq;
	}

	public void setLb10Bxsq(BigDecimal lb10Bxsq) {
		this.lb10Bxsq = lb10Bxsq;
	}

	
}
