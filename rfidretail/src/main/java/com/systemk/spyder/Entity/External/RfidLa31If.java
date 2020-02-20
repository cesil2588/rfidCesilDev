package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLa31IfKey;

// 온라인 반품실적
@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_la31_if")
public class RfidLa31If implements Serializable{

	private static final long serialVersionUID = -241442272775181043L;

	@EmbeddedId
	private RfidLa31IfKey key;

	@Column(name="la31_ordno")
	private String la31Ordno;

	@Column(name="la31_ordsr")
	private BigDecimal la31Ordsr;

	@Column(name="la31_bpdt")
	private String la31Bpdt;

	@Column(name="la31_mjcd")
	private String la31Mjcd;

	@Column(name="la31_corn")
	private String la31Corn;

	@Column(name="la31_styl")
	private String la31Styl;

	@Column(name="la31_stcd")
	private String la31Stcd;

	@Column(name="la31_bpqt")
	private BigDecimal la31Bpqt;

	@Column(name="la31_bigo")
	private String la31Bigo;

	@Column(name="la31_tryn")
	private String la31Tryn;

	@Column(name="la31_trdt")
	private String la31Trdt;

	@Column(name="la31_trmh")
	private String la31Trmh;

	@Column(name="la31_errt")
	private String la31Errt;

	@Column(name="la31_endt")
	private String la31Endt;

	@Column(name="la31_enid")
	private String la31Enid;

	@Column(name="la31_updt")
	private String la31Updt;

	@Column(name="la31_upid")
	private String la31Upid;

	public RfidLa31IfKey getKey() {
		return key;
	}

	public void setKey(RfidLa31IfKey key) {
		this.key = key;
	}

	public String getLa31Ordno() {
		return la31Ordno;
	}

	public void setLa31Ordno(String la31Ordno) {
		this.la31Ordno = la31Ordno;
	}

	public BigDecimal getLa31Ordsr() {
		return la31Ordsr;
	}

	public void setLa31Ordsr(BigDecimal la31Ordsr) {
		this.la31Ordsr = la31Ordsr;
	}

	public String getLa31Bpdt() {
		return la31Bpdt;
	}

	public void setLa31Bpdt(String la31Bpdt) {
		this.la31Bpdt = la31Bpdt;
	}

	public String getLa31Mjcd() {
		return la31Mjcd;
	}

	public void setLa31Mjcd(String la31Mjcd) {
		this.la31Mjcd = la31Mjcd;
	}

	public String getLa31Corn() {
		return la31Corn;
	}

	public void setLa31Corn(String la31Corn) {
		this.la31Corn = la31Corn;
	}

	public String getLa31Styl() {
		return la31Styl;
	}

	public void setLa31Styl(String la31Styl) {
		this.la31Styl = la31Styl;
	}

	public String getLa31Stcd() {
		return la31Stcd;
	}

	public void setLa31Stcd(String la31Stcd) {
		this.la31Stcd = la31Stcd;
	}

	public BigDecimal getLa31Bpqt() {
		return la31Bpqt;
	}

	public void setLa31Bpqt(BigDecimal la31Bpqt) {
		this.la31Bpqt = la31Bpqt;
	}

	public String getLa31Bigo() {
		return la31Bigo;
	}

	public void setLa31Bigo(String la31Bigo) {
		this.la31Bigo = la31Bigo;
	}

	public String getLa31Tryn() {
		return la31Tryn;
	}

	public void setLa31Tryn(String la31Tryn) {
		this.la31Tryn = la31Tryn;
	}

	public String getLa31Trdt() {
		return la31Trdt;
	}

	public void setLa31Trdt(String la31Trdt) {
		this.la31Trdt = la31Trdt;
	}

	public String getLa31Trmh() {
		return la31Trmh;
	}

	public void setLa31Trmh(String la31Trmh) {
		this.la31Trmh = la31Trmh;
	}

	public String getLa31Errt() {
		return la31Errt;
	}

	public void setLa31Errt(String la31Errt) {
		this.la31Errt = la31Errt;
	}

	public String getLa31Endt() {
		return la31Endt;
	}

	public void setLa31Endt(String la31Endt) {
		this.la31Endt = la31Endt;
	}

	public String getLa31Enid() {
		return la31Enid;
	}

	public void setLa31Enid(String la31Enid) {
		this.la31Enid = la31Enid;
	}

	public String getLa31Updt() {
		return la31Updt;
	}

	public void setLa31Updt(String la31Updt) {
		this.la31Updt = la31Updt;
	}

	public String getLa31Upid() {
		return la31Upid;
	}

	public void setLa31Upid(String la31Upid) {
		this.la31Upid = la31Upid;
	}

	// RfidLa31If 모델 복사
	public void CopyData(RfidLa31If param) {
		this.la31Ordno = param.getLa31Ordno();
		this.la31Ordsr = param.getLa31Ordsr();
		this.la31Bpdt = param.getLa31Bpdt();
		this.la31Mjcd = param.getLa31Mjcd();
		this.la31Corn = param.getLa31Corn();
		this.la31Styl = param.getLa31Styl();
		this.la31Stcd = param.getLa31Stcd();
		this.la31Bpqt = param.getLa31Bpqt();
		this.la31Bigo = param.getLa31Bigo();
		this.la31Tryn = param.getLa31Tryn();
		this.la31Trdt = param.getLa31Trdt();
		this.la31Trmh = param.getLa31Trmh();
		this.la31Errt = param.getLa31Errt();
		this.la31Endt = param.getLa31Endt();
		this.la31Enid = param.getLa31Enid();
		this.la31Updt = param.getLa31Updt();
		this.la31Upid = param.getLa31Upid();
	}
}