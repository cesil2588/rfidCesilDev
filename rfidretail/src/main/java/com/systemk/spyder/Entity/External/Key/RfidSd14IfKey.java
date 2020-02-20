package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;

public class RfidSd14IfKey implements Serializable{

	/**
	 * ERP 매장 반품지시 Entity key
	 */
	private static final long serialVersionUID = 2502915556331361484L;
	
	//반품 지시일자
	@Column(name="sd14_jsdt")
	private String sd14Jsdt;
	
	//반품지시 sequence
	@Column(name="sd14_jssq")
	private Long sd14Jssq;
	
	//반품지시구분(81:물류반품, 82:온라인반품, 90:계절반품)
	@Column(name="sd14_mggb")
	private String sd14Mggb;
	
	//From매장코드
	@Column(name="sd14_frcd")
	private String sd14Frcd;
	
	//From매장코너코드
	@Column(name="sd14_frco")
	private String sd14Rfco;
	
	//스타일
	@Column(name="sd14_styl")
	private String sd14Styl;
	
	//나머지 제품코드
	@Column(name="sd14_stcd")
	private String sd14Stcd;

	public String getSd14Jsdt() {
		return sd14Jsdt;
	}

	public void setSd14Jsdt(String sd14Jsdt) {
		this.sd14Jsdt = sd14Jsdt;
	}

	public Long getSd14Jssq() {
		return sd14Jssq;
	}

	public void setSd14Jssq(Long sd14Jssq) {
		this.sd14Jssq = sd14Jssq;
	}

	public String getSd14Mggb() {
		return sd14Mggb;
	}

	public void setSd14Mggb(String sd14Mggb) {
		this.sd14Mggb = sd14Mggb;
	}

	public String getSd14Frcd() {
		return sd14Frcd;
	}

	public void setSd14Rfcd(String sd14Frcd) {
		this.sd14Frcd = sd14Frcd;
	}

	public String getSd14Rfco() {
		return sd14Rfco;
	}

	public void setSd14Rfco(String sd14Rfco) {
		this.sd14Rfco = sd14Rfco;
	}

	public String getSd14Styl() {
		return sd14Styl;
	}

	public void setSd14Styl(String sd14Styl) {
		this.sd14Styl = sd14Styl;
	}

	public String getSd14Stcd() {
		return sd14Stcd;
	}

	public void setSd14Stcd(String sd14Stcd) {
		this.sd14Stcd = sd14Stcd;
	}
	
	

}
