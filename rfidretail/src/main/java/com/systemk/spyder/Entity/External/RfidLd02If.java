package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLd02IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_ld02_if")
public class RfidLd02If implements Serializable{

	private static final long serialVersionUID = -6484851656964364211L;

	@EmbeddedId
	private RfidLd02IfKey key;

	@Column(name="ld02_seqn")
	private String ld02Seqn;

	// 칼라
	@Column(name="ld02_it06")
    private String ld02It06;

	// 사이즈
	@Column(name="ld02_it07")
    private String ld02It07;

	// 주문수량
	@Column(name="ld02_jmqt")
    private BigDecimal ld02Jmqt;

	// Sorting 수량
	@Column(name="ld02_sqty")
    private BigDecimal ld02Sqty;

	// 출고 수량
	@Column(name="ld02_chqt")
    private BigDecimal ld02Chqt;

	// 출고 일자
	@Column(name="ld02_chdt")
    private String ld02Chdt;

	// 출고 일련번호
	@Column(name="ld02_chsq")
    private BigDecimal ld02Chsq;

	// 출고 시리얼
	@Column(name="ld02_chsr")
    private BigDecimal ld02Chsr;

	// 출고 등록 ID
	@Column(name="ld02_chid")
    private String ld02Chid;

	// 전송 구분
	@Column(name="ld02_tryn")
    private String ld02Tryn;

	// 데이터 여부
	@Column(name="ld02_stat")
    private String ld02Stat;

	// 등록 일자
	@Column(name="ld02_endt")
    private String ld02Endt;

	// 등록 ID
	@Column(name="ld02_enid")
    private String ld02Enid;

	public String getLd02It06() {
		return ld02It06;
	}

	public void setLd02It06(String ld02It06) {
		this.ld02It06 = ld02It06;
	}

	public String getLd02It07() {
		return ld02It07;
	}

	public void setLd02It07(String ld02It07) {
		this.ld02It07 = ld02It07;
	}

	public BigDecimal getLd02Jmqt() {
		return ld02Jmqt;
	}

	public void setLd02Jmqt(BigDecimal ld02Jmqt) {
		this.ld02Jmqt = ld02Jmqt;
	}

	public BigDecimal getLd02Sqty() {
		return ld02Sqty;
	}

	public void setLd02Sqty(BigDecimal ld02Sqty) {
		this.ld02Sqty = ld02Sqty;
	}

	public BigDecimal getLd02Chqt() {
		return ld02Chqt;
	}

	public void setLd02Chqt(BigDecimal ld02Chqt) {
		this.ld02Chqt = ld02Chqt;
	}

	public String getLd02Chdt() {
		return ld02Chdt;
	}

	public void setLd02Chdt(String ld02Chdt) {
		this.ld02Chdt = ld02Chdt;
	}

	public BigDecimal getLd02Chsq() {
		return ld02Chsq;
	}

	public void setLd02Chsq(BigDecimal ld02Chsq) {
		this.ld02Chsq = ld02Chsq;
	}

	public BigDecimal getLd02Chsr() {
		return ld02Chsr;
	}

	public void setLd02Chsr(BigDecimal ld02Chsr) {
		this.ld02Chsr = ld02Chsr;
	}

	public String getLd02Chid() {
		return ld02Chid;
	}

	public void setLd02Chid(String ld02Chid) {
		this.ld02Chid = ld02Chid;
	}

	public String getLd02Tryn() {
		return ld02Tryn;
	}

	public void setLd02Tryn(String ld02Tryn) {
		this.ld02Tryn = ld02Tryn;
	}

	public String getLd02Stat() {
		return ld02Stat;
	}

	public void setLd02Stat(String ld02Stat) {
		this.ld02Stat = ld02Stat;
	}

	public String getLd02Endt() {
		return ld02Endt;
	}

	public void setLd02Endt(String ld02Endt) {
		this.ld02Endt = ld02Endt;
	}

	public RfidLd02IfKey getKey() {
		return key;
	}

	public void setKey(RfidLd02IfKey key) {
		this.key = key;
	}

	public String getLd02Seqn() {
		return ld02Seqn;
	}

	public void setLd02Seqn(String ld02Seqn) {
		this.ld02Seqn = ld02Seqn;
	}

	public String getLd02Enid() {
		return ld02Enid;
	}

	public void setLd02Enid(String ld02Enid) {
		this.ld02Enid = ld02Enid;
	}
}
