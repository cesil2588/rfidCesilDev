package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;

public class RfidSd15IfKey implements Serializable{

	/**
	 * ERP 매장반품지시 실적
	 */
	private static final long serialVersionUID = -5298832632528928897L;
	
	//반품지시일자
	@Column(name="sd15_jsdt")
	private String sd15Jsdt;
	
	//반품지시sequence
	@Column(name="sd15_jssq")
	private Long sd15Jssq;
	
	//반품지시구분
	@Column(name="sd15_mggb")
	private String sd15Mggb;
	
	//From매장코드
	@Column(name="sd15_frcd")
	private String sd15Frcd;
	
	//From매장코너코드
	@Column(name="sd15_frco")
	private String sd15Frco;
	
	//스타일
	@Column(name="sd15_styl")
	private String sd15Styl;
	
	//나머지 제품코드
	@Column(name="sd15_stcd")
	private String sd15Stcd;
		
	//박스번호
	@Column(name="sd15_bxno")
	private Long sd15Bxno;

	public String getSd15Jsdt() {
		return sd15Jsdt;
	}

	public void setSd15Jsdt(String sd15Jsdt) {
		this.sd15Jsdt = sd15Jsdt;
	}

	public Long getSd15Jssq() {
		return sd15Jssq;
	}

	public void setSd15Jssq(Long sd15Jssq) {
		this.sd15Jssq = sd15Jssq;
	}

	public String getSd15Mggb() {
		return sd15Mggb;
	}

	public void setSd15Mggb(String sd15Mggb) {
		this.sd15Mggb = sd15Mggb;
	}

	public String getSd15Frcd() {
		return sd15Frcd;
	}

	public void setSd15Frcd(String sd15Frcd) {
		this.sd15Frcd = sd15Frcd;
	}

	public String getSd15Frco() {
		return sd15Frco;
	}

	public void setSd15Frco(String sd15Frco) {
		this.sd15Frco = sd15Frco;
	}

	public String getSd15Styl() {
		return sd15Styl;
	}

	public void setSd15Styl(String sd15Styl) {
		this.sd15Styl = sd15Styl;
	}

	public String getSd15Stcd() {
		return sd15Stcd;
	}

	public void setSd15Stcd(String sd15Stcd) {
		this.sd15Stcd = sd15Stcd;
	}

	public Long getSd15Bxno() {
		return sd15Bxno;
	}

	public void setSd15Bxno(Long sd15Bxno) {
		this.sd15Bxno = sd15Bxno;
	}
	

}
