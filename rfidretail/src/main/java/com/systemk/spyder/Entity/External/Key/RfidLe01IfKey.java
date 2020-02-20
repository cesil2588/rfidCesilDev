package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLe01IfKey implements Serializable{

	private static final long serialVersionUID = 3724536261363081793L;

	// 출고 일자
	@Column(name="le01_chdt")
	private String le01Chdt;

	// 물류 마감일자
	@Column(name="le01_mgdt")
    private String le01Mgdt;

	// 물류 마감구분
	@Column(name="le01_mggb")
    private String le01Mggb;

	// 물류 마감번호
	@Column(name="le01_mgsq")
    private String le01Mgsq;
	
	// 매장
	@Column(name="le01_mjcd")
	private String le01Mjcd;

	// 매장 코너 코드
	@Column(name="le01_mjco")
	private String le01Mjco;

	// 물류 마감인터페이스 순번
	@Column(name="le01_seqn")
    private String le01Seqn;

	public String getLe01Chdt() {
		return le01Chdt;
	}

	public void setLe01Chdt(String le01Chdt) {
		this.le01Chdt = le01Chdt;
	}

	public String getLe01Mgdt() {
		return le01Mgdt;
	}

	public void setLe01Mgdt(String le01Mgdt) {
		this.le01Mgdt = le01Mgdt;
	}

	public String getLe01Mggb() {
		return le01Mggb;
	}

	public void setLe01Mggb(String le01Mggb) {
		this.le01Mggb = le01Mggb;
	}

	public String getLe01Mgsq() {
		return le01Mgsq;
	}

	public void setLe01Mgsq(String le01Mgsq) {
		this.le01Mgsq = le01Mgsq;
	}

	public String getLe01Seqn() {
		return le01Seqn;
	}

	public void setLe01Seqn(String le01Seqn) {
		this.le01Seqn = le01Seqn;
	}

	public String getLe01Mjcd() {
		return le01Mjcd;
	}

	public void setLe01Mjcd(String le01Mjcd) {
		this.le01Mjcd = le01Mjcd;
	}

	public String getLe01Mjco() {
		return le01Mjco;
	}

	public void setLe01Mjco(String le01Mjco) {
		this.le01Mjco = le01Mjco;
	}
	
}
