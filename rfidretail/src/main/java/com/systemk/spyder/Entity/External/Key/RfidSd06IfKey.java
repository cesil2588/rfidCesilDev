package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;

public class RfidSd06IfKey implements Serializable {

	/**
	 * 매장이동지시 Entity key
	 */
	private static final long serialVersionUID = -6157566355512648758L;
	
	//이동지시일자
	@Column(name="sd06_iddt")
	private String sd06Iddt;
	
	//이동지시 Sequence
	@Column(name="sd06_idsq")
	private Long sd06Idsq;
	
	//이동지시 Serial
	@Column(name="sd06_idsr")
	private Long sd06Idsr;

	public String getSd06Iddt() {
		return sd06Iddt;
	}

	public void setSd06Iddt(String sd06Iddt) {
		this.sd06Iddt = sd06Iddt;
	}

	public Long getSd06Idsq() {
		return sd06Idsq;
	}

	public void setSd06Idsq(Long sd06Idsq) {
		this.sd06Idsq = sd06Idsq;
	}

	public Long getSd06Idsr() {
		return sd06Idsr;
	}

	public void setSd06Idsr(Long sd06Idsr) {
		this.sd06Idsr = sd06Idsr;
	}
	

}
