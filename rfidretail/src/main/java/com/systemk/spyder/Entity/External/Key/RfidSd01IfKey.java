package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;

public class RfidSd01IfKey implements Serializable{

	/**
	 * 매장 신규 등록 확정을 ERP에 넘기기위한 Entity
	 */
	private static final long serialVersionUID = 2802564478868861804L;
	
	//등록일자
	@Column(name="sd01_endt")
	private String sd01Endt;
	
	//From거래처코드
	@Column(name="sd01_frcd")
	private String sd01Frcd;
	
	//From 코너코드
	@Column(name="sd01_frco")
	private String sd01Frco;
	
	//등록번호
	@Column(name="sd01_ensq")
	private Long sd01Ensq;
	
	//등록 Serial
	@Column(name="sd01_ensr")
	private Long sd01Ensr;

	public String getSd01Endt() {
		return sd01Endt;
	}

	public void setSd01Endt(String sd01Endt) {
		this.sd01Endt = sd01Endt;
	}

	public String getSd01Frcd() {
		return sd01Frcd;
	}

	public void setSd01Frcd(String sd01Frcd) {
		this.sd01Frcd = sd01Frcd;
	}

	public String getSd01Frco() {
		return sd01Frco;
	}

	public void setSd01Frco(String sd01Frco) {
		this.sd01Frco = sd01Frco;
	}

	public Long getSd01Ensq() {
		return sd01Ensq;
	}

	public void setSd01Ensq(Long sd01Ensq) {
		this.sd01Ensq = sd01Ensq;
	}

	public Long getSd01Ensr() {
		return sd01Ensr;
	}

	public void setSd01Ensr(Long sd01Ensr) {
		this.sd01Ensr = sd01Ensr;
	}


}
