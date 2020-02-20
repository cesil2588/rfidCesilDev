package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidZa11IfKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5798281526062919723L;

	@Column(name="za11_crdt")
	private String za11Crdt;

	@Column(name="za11_crno")
    private BigDecimal za11Crno;

	public String getZa11Crdt() {
		return za11Crdt;
	}

	public void setZa11Crdt(String za11Crdt) {
		this.za11Crdt = za11Crdt;
	}

	public BigDecimal getZa11Crno() {
		return za11Crno;
	}

	public void setZa11Crno(BigDecimal za11Crno) {
		this.za11Crno = za11Crno;
	}
    
    

}
