package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidAc18IfKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5008157860453203224L;

	@Column(name="ac18_crdt")
	private String ac18Crdt;

	@Column(name="ac18_crsq")
    private BigDecimal ac18Crsq;

	@Column(name="ac18_crno")
    private BigDecimal ac18Crno;

	public String getAc18Crdt() {
		return ac18Crdt;
	}

	public void setAc18Crdt(String ac18Crdt) {
		this.ac18Crdt = ac18Crdt;
	}

	public BigDecimal getAc18Crsq() {
		return ac18Crsq;
	}

	public void setAc18Crsq(BigDecimal ac18Crsq) {
		this.ac18Crsq = ac18Crsq;
	}

	public BigDecimal getAc18Crno() {
		return ac18Crno;
	}

	public void setAc18Crno(BigDecimal ac18Crno) {
		this.ac18Crno = ac18Crno;
	}
    
}
