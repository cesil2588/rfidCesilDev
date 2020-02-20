package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLa31IfKey implements Serializable{

	private static final long serialVersionUID = 2968253616535483840L;

	@Column(name="la31_jmdt")
	private String la31Jmdt;

	@Column(name="la31_jmid")
	private String la31Jmid;

	@Column(name="la31_jmsr")
	private BigDecimal la31Jmsr;

	public String getLa31Jmdt() {
		return la31Jmdt;
	}

	public void setLa31Jmdt(String la31Jmdt) {
		this.la31Jmdt = la31Jmdt;
	}

	public String getLa31Jmid() {
		return la31Jmid;
	}

	public void setLa31Jmid(String la31Jmid) {
		this.la31Jmid = la31Jmid;
	}

	public BigDecimal getLa31Jmsr() {
		return la31Jmsr;
	}

	public void setLa31Jmsr(BigDecimal la31Jmsr) {
		this.la31Jmsr = la31Jmsr;
	}
	
	
}
