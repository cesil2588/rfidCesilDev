package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidZa40IfKey implements Serializable{

	private static final long serialVersionUID = -5755623670464769371L;

	@Column(name="za40_crdt")
	private String za40Crdt;

	@Column(name="za40_crno")
    private BigDecimal za40Crno;

	@Column(name="za40_usid", nullable = false, length = 20)
    private String za40Usid;

	public String getZa40Crdt() {
		return za40Crdt;
	}

	public void setZa40Crdt(String za40Crdt) {
		this.za40Crdt = za40Crdt;
	}

	public BigDecimal getZa40Crno() {
		return za40Crno;
	}

	public void setZa40Crno(BigDecimal za40Crno) {
		this.za40Crno = za40Crno;
	}

	public String getZa40Usid() {
		return za40Usid;
	}

	public void setZa40Usid(String za40Usid) {
		this.za40Usid = za40Usid;
	}

}
