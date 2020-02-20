package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidIb01IfKey implements Serializable{

	private static final long serialVersionUID = 3110540142177762551L;
	
	// 입고 일자
	@Column(name="lb01_ipdt")
	private String lb01Ipdt;

	// 박스 번호
	@Column(name="lb01_bxno")
	private String lb01Bxno;
	
	// 순번
	@Column(name="lb01_ipsno")
	private BigDecimal lb01Ipsno;

	public String getLb01Ipdt() {
		return lb01Ipdt;
	}

	public void setLb01Ipdt(String lb01Ipdt) {
		this.lb01Ipdt = lb01Ipdt;
	}
	public String getLb01Bxno() {
		return lb01Bxno;
	}

	public void setLb01Bxno(String lb01Bxno) {
		this.lb01Bxno = lb01Bxno;
	}

	public BigDecimal getLb01Ipsno() {
		return lb01Ipsno;
	}

	public void setLb01Ipsno(BigDecimal lb01Ipsno) {
		this.lb01Ipsno = lb01Ipsno;
	}
	
}
