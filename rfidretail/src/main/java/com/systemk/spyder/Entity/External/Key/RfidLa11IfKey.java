package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLa11IfKey implements Serializable{

	private static final long serialVersionUID = 1121854995354742916L;

	@Column(name="la11_jmdt")
	private String la11Jmdt;

	@Column(name="la11_jmid")
	private String la11Jmid;

	@Column(name="la11_jmsr")
	private BigDecimal la11Jmsr;

	public String getLa11Jmdt() {
		return la11Jmdt;
	}

	public void setLa11Jmdt(String la11Jmdt) {
		this.la11Jmdt = la11Jmdt;
	}

	public String getLa11Jmid() {
		return la11Jmid;
	}

	public void setLa11Jmid(String la11Jmid) {
		this.la11Jmid = la11Jmid;
	}

	public BigDecimal getLa11Jmsr() {
		return la11Jmsr;
	}

	public void setLa11Jmsr(BigDecimal la11Jmsr) {
		this.la11Jmsr = la11Jmsr;
	}
	
	
}
