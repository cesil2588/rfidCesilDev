package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLd02IfKey implements Serializable{

	private static final long serialVersionUID = 1L;

	// 물류 마감일자
	@Column(name="ld02_mgdt")
	private String ld02Mgdt;

	// 물류 마감일자 구분
	@Column(name="ld02_mggb")
    private String ld02Mggb;

	// 물류 마감번호
	@Column(name="ld02_mgsq")
    private String ld02Mgsq;

	// 물류 마감매장
	@Column(name="ld02_mjcd")
    private String ld02Mjcd;

	// 물류 마감매장 코너
	@Column(name="ld02_mjco")
    private String ld02Mjco;

	// 물류 마감창고
	@Column(name="ld02_cgcd")
    private String ld02Cgcd;

	// 물류 마감창고 코너
	@Column(name="ld02_cgco")
    private String ld02Cgco;

	// 스타일
	@Column(name="ld02_styl")
    private String ld02Styl;

	// 나머지 제품코드
	@Column(name="ld02_stcd")
    private String ld02Stcd;

	// 번들코드
	@Column(name="ld02_bncd")
    private String ld02Bncd;
	
	public String getLd02Mgdt() {
		return ld02Mgdt;
	}

	public void setLd02Mgdt(String ld02Mgdt) {
		this.ld02Mgdt = ld02Mgdt;
	}

	public String getLd02Mggb() {
		return ld02Mggb;
	}

	public void setLd02Mggb(String ld02Mggb) {
		this.ld02Mggb = ld02Mggb;
	}

	public String getLd02Mgsq() {
		return ld02Mgsq;
	}

	public void setLd02Mgsq(String ld02Mgsq) {
		this.ld02Mgsq = ld02Mgsq;
	}

	public String getLd02Mjcd() {
		return ld02Mjcd;
	}

	public void setLd02Mjcd(String ld02Mjcd) {
		this.ld02Mjcd = ld02Mjcd;
	}

	public String getLd02Mjco() {
		return ld02Mjco;
	}

	public void setLd02Mjco(String ld02Mjco) {
		this.ld02Mjco = ld02Mjco;
	}

	public String getLd02Cgcd() {
		return ld02Cgcd;
	}

	public void setLd02Cgcd(String ld02Cgcd) {
		this.ld02Cgcd = ld02Cgcd;
	}

	public String getLd02Cgco() {
		return ld02Cgco;
	}

	public void setLd02Cgco(String ld02Cgco) {
		this.ld02Cgco = ld02Cgco;
	}

	public String getLd02Styl() {
		return ld02Styl;
	}

	public void setLd02Styl(String ld02Styl) {
		this.ld02Styl = ld02Styl;
	}

	public String getLd02Stcd() {
		return ld02Stcd;
	}

	public void setLd02Stcd(String ld02Stcd) {
		this.ld02Stcd = ld02Stcd;
	}

	public String getLd02Bncd() {
		return ld02Bncd;
	}

	public void setLd02Bncd(String ld02Bncd) {
		this.ld02Bncd = ld02Bncd;
	}
}
