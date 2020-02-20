package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLa12IfKey implements Serializable{

	private static final long serialVersionUID = -2284152851921013296L;

	@Column(name="la12_jmdt")
	private String la12Jmdt;

	@Column(name="la12_jmid")
    private String la12Jmid;

	@Column(name="la12_jmsr")
    private BigDecimal la12Jmsr;

	public String getLa12Jmdt() {
		return la12Jmdt;
	}

	public void setLa12Jmdt(String la12Jmdt) {
		this.la12Jmdt = la12Jmdt;
	}

	public String getLa12Jmid() {
		return la12Jmid;
	}

	public void setLa12Jmid(String la12Jmid) {
		this.la12Jmid = la12Jmid;
	}

	public BigDecimal getLa12Jmsr() {
		return la12Jmsr;
	}

	public void setLa12Jmsr(BigDecimal la12Jmsr) {
		this.la12Jmsr = la12Jmsr;
	}
    
    
}
