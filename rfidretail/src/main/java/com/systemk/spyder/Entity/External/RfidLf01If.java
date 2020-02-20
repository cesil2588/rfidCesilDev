package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLf01IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Table(name="rfid_lf01_if")
public class RfidLf01If implements Serializable{

	private static final long serialVersionUID = -3038695294899658800L;

	@EmbeddedId
	private RfidLf01IfKey key;

	//미결발생일
	@Column(name="lf01_mgdt")
	private String lf01Mgdt;

	//반품매장
	@Column(name="lf01_mjcd")
	private String lf01Mjcd;

	//반품매장코너코드
	@Column(name="lf01_mjco")
	private String lf01Mjco;

	//일련번호
	@Column(name="lf01_mgsq")
	private BigDecimal lf01Mgsq;

	//미결Serial
	@Column(name="lf01_mgsr")
	private BigDecimal lf01Mgsr;

	//미결구분
	@Column(name="lf01_mggb")
	private String lf01Mggb;

	//스타일
	@Column(name="lf01_styl")
	private String lf01Styl;

	//나머지 제품코드
	@Column(name="lf01_stcd")
	private String lf01Stcd;

	//미결수량
	@Column(name="lf01_mqty")
	private BigDecimal lf01Mqty;

	//반품수량
	@Column(name="lf01_bpqt")
	private BigDecimal lf01Bpqt;

	//반품sequence
	@Column(name="lf01_bpsq", insertable=false)
	private BigDecimal lf01Bpsq;

	//반품Serial
	@Column(name="lf01_bpsr", insertable=false)
	private BigDecimal lf01Bpsr;

	//업무구분
	@Column(name="lf01_emgb", insertable=false)
	private String lf01Emgb;

	//전송여부
	@Column(name="lf01_tryn", insertable=false)
	private String lf01Tryn;

	//전송시분
	@Column(name="lf01_time", insertable=false)
	private String lf01Time;

	//체크여부
	@Column(name="lf01_chyn", insertable=false)
	private String lf01Chyn;

	//비고
	@Column(name="lf01_bigo", insertable=false)
	private String lf01Bigo;

	//에러내역
	@Column(name="lf01_errt", insertable=false)
	private String lf01Errt;

	public RfidLf01IfKey getKey() {
		return key;
	}

	public void setKey(RfidLf01IfKey key) {
		this.key = key;
	}

	public String getLf01Mgdt() {
		return lf01Mgdt;
	}

	public void setLf01Mgdt(String lf01Mgdt) {
		this.lf01Mgdt = lf01Mgdt;
	}

	public String getLf01Mjcd() {
		return lf01Mjcd;
	}

	public void setLf01Mjcd(String lf01Mjcd) {
		this.lf01Mjcd = lf01Mjcd;
	}

	public String getLf01Mjco() {
		return lf01Mjco;
	}

	public void setLf01Mjco(String lf01Mjco) {
		this.lf01Mjco = lf01Mjco;
	}

	public BigDecimal getLf01Mgsq() {
		return lf01Mgsq;
	}

	public void setLf01Mgsq(BigDecimal lf01Mgsq) {
		this.lf01Mgsq = lf01Mgsq;
	}

	public BigDecimal getLf01Mgsr() {
		return lf01Mgsr;
	}

	public void setLf01Mgsr(BigDecimal lf01Mgsr) {
		this.lf01Mgsr = lf01Mgsr;
	}

	public String getLf01Mggb() {
		return lf01Mggb;
	}

	public void setLf01Mggb(String lf01Mggb) {
		this.lf01Mggb = lf01Mggb;
	}

	public String getLf01Styl() {
		return lf01Styl;
	}

	public void setLf01Styl(String lf01Styl) {
		this.lf01Styl = lf01Styl;
	}

	public String getLf01Stcd() {
		return lf01Stcd;
	}

	public void setLf01Stcd(String lf01Stcd) {
		this.lf01Stcd = lf01Stcd;
	}

	public BigDecimal getLf01Mqty() {
		return lf01Mqty;
	}

	public void setLf01Mqty(BigDecimal lf01Mqty) {
		this.lf01Mqty = lf01Mqty;
	}

	public BigDecimal getLf01Bpqt() {
		return lf01Bpqt;
	}

	public void setLf01Bpqt(BigDecimal lf01Bpqt) {
		this.lf01Bpqt = lf01Bpqt;
	}

	public BigDecimal getLf01Bpsq() {
		return lf01Bpsq;
	}

	public void setLf01Bpsq(BigDecimal lf01Bpsq) {
		this.lf01Bpsq = lf01Bpsq;
	}

	public BigDecimal getLf01Bpsr() {
		return lf01Bpsr;
	}

	public void setLf01Bpsr(BigDecimal lf01Bpsr) {
		this.lf01Bpsr = lf01Bpsr;
	}

	public String getLf01Emgb() {
		return lf01Emgb;
	}

	public void setLf01Emgb(String lf01Emgb) {
		this.lf01Emgb = lf01Emgb;
	}

	public String getLf01Tryn() {
		return lf01Tryn;
	}

	public void setLf01Tryn(String lf01Tryn) {
		this.lf01Tryn = lf01Tryn;
	}

	public String getLf01Time() {
		return lf01Time;
	}

	public void setLf01Time(String lf01Time) {
		this.lf01Time = lf01Time;
	}

	public String getLf01Chyn() {
		return lf01Chyn;
	}

	public void setLf01Chyn(String lf01Chyn) {
		this.lf01Chyn = lf01Chyn;
	}

	public String getLf01Bigo() {
		return lf01Bigo;
	}

	public void setLf01Bigo(String lf01Bigo) {
		this.lf01Bigo = lf01Bigo;
	}

	public String getLf01Errt() {
		return lf01Errt;
	}

	public void setLf01Errt(String lf01Errt) {
		this.lf01Errt = lf01Errt;
	}


}
