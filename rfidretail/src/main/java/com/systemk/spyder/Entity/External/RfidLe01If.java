package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLe01IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_le01_if")
public class RfidLe01If implements Serializable{

	private static final long serialVersionUID = -7177047766586318838L;

	@EmbeddedId
	private RfidLe01IfKey key;

	// 출고 일련번호
	@Column(name="le01_chsq")
	private BigDecimal le01Chsq;

	// 출고 시리얼
	@Column(name="le01_chsr")
    private BigDecimal le01Chsr;

	// 업무 구분
	@Column(name="le01_emgb")
    private String le01Emgb;

	// 처리 구분1
	@Column(name="le01_crg1")
    private String le01Crg1;

	// 처리 구분2
	@Column(name="le01_crg2")
    private String le01Crg2;

	// 스타일
	@Column(name="le01_styl")
    private String le01Styl;

	// 나머지 제품코드
	@Column(name="le01_stcd")
    private String le01Stcd;

	// Sorting 수량
	@Column(name="le01_sqty")
    private BigDecimal le01Sqty;

	// 출고 수량
	@Column(name="le01_chqt")
    private BigDecimal le01Chqt;

	// 전송 여부
	@Column(name="le01_tryn")
    private String le01Tryn;

	// 전송 시분
	@Column(name="le01_time")
    private String le01Time;

	// 체크 여부
	@Column(name="le01_chyn")
    private String le01Chyn;

	// 비고
	@Column(name="le01_bigo")
    private String le01Bigo;

	// 등록일시
	@Column(name="le01_endt")
    private Date le01Endt;

	public RfidLe01IfKey getKey() {
		return key;
	}

	public void setKey(RfidLe01IfKey key) {
		this.key = key;
	}

	public BigDecimal getLe01Chsq() {
		return le01Chsq;
	}

	public void setLe01Chsq(BigDecimal le01Chsq) {
		this.le01Chsq = le01Chsq;
	}

	public BigDecimal getLe01Chsr() {
		return le01Chsr;
	}

	public void setLe01Chsr(BigDecimal le01Chsr) {
		this.le01Chsr = le01Chsr;
	}

	public String getLe01Emgb() {
		return le01Emgb;
	}

	public void setLe01Emgb(String le01Emgb) {
		this.le01Emgb = le01Emgb;
	}

	public String getLe01Crg1() {
		return le01Crg1;
	}

	public void setLe01Crg1(String le01Crg1) {
		this.le01Crg1 = le01Crg1;
	}

	public String getLe01Crg2() {
		return le01Crg2;
	}

	public void setLe01Crg2(String le01Crg2) {
		this.le01Crg2 = le01Crg2;
	}

	public String getLe01Styl() {
		return le01Styl;
	}

	public void setLe01Styl(String le01Styl) {
		this.le01Styl = le01Styl;
	}

	public String getLe01Stcd() {
		return le01Stcd;
	}

	public void setLe01Stcd(String le01Stcd) {
		this.le01Stcd = le01Stcd;
	}

	public BigDecimal getLe01Sqty() {
		return le01Sqty;
	}

	public void setLe01Sqty(BigDecimal le01Sqty) {
		this.le01Sqty = le01Sqty;
	}

	public BigDecimal getLe01Chqt() {
		return le01Chqt;
	}

	public void setLe01Chqt(BigDecimal le01Chqt) {
		this.le01Chqt = le01Chqt;
	}

	public String getLe01Tryn() {
		return le01Tryn;
	}

	public void setLe01Tryn(String le01Tryn) {
		this.le01Tryn = le01Tryn;
	}

	public String getLe01Time() {
		return le01Time;
	}

	public void setLe01Time(String le01Time) {
		this.le01Time = le01Time;
	}

	public String getLe01Chyn() {
		return le01Chyn;
	}

	public void setLe01Chyn(String le01Chyn) {
		this.le01Chyn = le01Chyn;
	}

	public String getLe01Bigo() {
		return le01Bigo;
	}

	public void setLe01Bigo(String le01Bigo) {
		this.le01Bigo = le01Bigo;
	}

	public Date getLe01Endt() {
		return le01Endt;
	}

	public void setLe01Endt(Date le01Endt) {
		this.le01Endt = le01Endt;
	}
}
