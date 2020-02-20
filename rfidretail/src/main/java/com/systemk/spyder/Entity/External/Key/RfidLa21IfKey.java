package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLa21IfKey implements Serializable{

	private static final long serialVersionUID = -4222377222428405985L;

	@Column(name="la21_jmdt")
	private String la21Jmdt;

	@Column(name="la21_jmid")
    private String la21Jmid;

	@Column(name="la21_jmsr")
    private BigDecimal la21Jmsr;

	@Column(name="la21_ordno")
    private String la21Ordno;

	@Column(name="la21_ordsr")
    private BigDecimal la21Ordsr;

	public String getLa21Jmdt() {
		return la21Jmdt;
	}

	public void setLa21Jmdt(String la21Jmdt) {
		this.la21Jmdt = la21Jmdt;
	}

	public String getLa21Jmid() {
		return la21Jmid;
	}

	public void setLa21Jmid(String la21Jmid) {
		this.la21Jmid = la21Jmid;
	}

	public BigDecimal getLa21Jmsr() {
		return la21Jmsr;
	}

	public void setLa21Jmsr(BigDecimal la21Jmsr) {
		this.la21Jmsr = la21Jmsr;
	}

	public String getLa21Ordno() {
		return la21Ordno;
	}

	public void setLa21Ordno(String la21Ordno) {
		this.la21Ordno = la21Ordno;
	}

	public BigDecimal getLa21Ordsr() {
		return la21Ordsr;
	}

	public void setLa21Ordsr(BigDecimal la21Ordsr) {
		this.la21Ordsr = la21Ordsr;
	}
    
    
}
