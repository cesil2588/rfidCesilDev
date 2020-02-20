package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd02IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_sd02_if")
public class RfidSd02If implements Serializable{

	private static final long serialVersionUID = -3038695294899658800L;

	@EmbeddedId
	private RfidSd02IfKey key;

	//미결 발생일
	@Column(name="sd02_mgdt")
	private String sd02Mgdt;

	//from 거래처
	@Column(name="sd02_frcd")
	private String sd02Frcd;

	//from코너
	@Column(name="sd02_frco")
	private String sd02Frco;

	//일련번호
	@Column(name="sd02_mgsq")
	private BigDecimal sd02Mgsq;

	//미결 Serial
	@Column(name="sd02_mgsr")
	private BigDecimal sd02Mgsr;

	//미결구분
	@Column(name="sd02_mggb")
	private String sd02Mggb;

	//to 거래처
	@Column(name="sd02_tocd")
	private String sd02Tocd;

	//to 코너
	@Column(name="sd02_toco")
	private String sd02Toco;

	//스타일
	@Column(name="sd02_styl")
	private String sd02Styl;

	//나머지 제품코드
	@Column(name="sd02_stcd")
	private String sd02Stcd;

	//미결명세서 발행여부
	@Column(name="sd02_mtag")
	private String sd02Mtag;

	//미결명세번호
	@Column(name="sd02_mkrn")
	private BigDecimal sd02Mkrn;

	//미결수량
	@Column(name="sd02_mqty")
	private BigDecimal sd02Mqty;

	//비고
	@Column(name="sd02_bigo")
	private String sd02Bigo;

	//전송여부
	@Column(name="sd02_tryn")
	private String sd02Tryn;

	//전송일시
	@Column(name="sd02_trdt")
	private String sd02Trdt;

	//확정여부
	@Column(name="sd02_cfyn")
	private String sd02Cfyn;

	//확정일자
	@Column(name="sd02_cfdt")
	private String sd02Cfdt;

	//확정자
	@Column(name="sd02_cfid")
	private String sd02Cfid;

	//미결확정수량
	@Column(name="sd02_cfqt")
	private BigDecimal sd02Cfqt;

	//수불반영일자
	@Column(name="sd02_sbdt")
	private String sd02Sbdt;

	//출고 Sequence
	@Column(name="sd02_chsq")
	private BigDecimal sd02Chsq;

	//반품 Sequence
	@Column(name="sd02_bpsq")
	private BigDecimal sd02Bpsq;

	//등록구분
	@Column(name="sd02_engb")
	private String sd02Engb;

	//등록자
	@Column(name="sd02_enid")
	private String sd02Enid;

	//등록일시
	@Column(name="sd02_entm")
	private String sd02Entm;

	//수정자
	@Column(name="sd02_upid")
	private String sd02Upid;

	//수정일시
	@Column(name="sd02_uptm")
	private String sd02Uptm;

	public RfidSd02IfKey getKey() {
		return key;
	}

	public void setKey(RfidSd02IfKey key) {
		this.key = key;
	}

	public String getSd02Mgdt() {
		return sd02Mgdt;
	}

	public void setSd02Mgdt(String sd02Mgdt) {
		this.sd02Mgdt = sd02Mgdt;
	}

	public String getSd02Frcd() {
		return sd02Frcd;
	}

	public void setSd02Frcd(String sd02Frcd) {
		this.sd02Frcd = sd02Frcd;
	}

	public String getSd02Frco() {
		return sd02Frco;
	}

	public void setSd02Frco(String sd02Frco) {
		this.sd02Frco = sd02Frco;
	}

	public BigDecimal getSd02Mgsq() {
		return sd02Mgsq;
	}

	public void setSd02Mgsq(BigDecimal sd02Mgsq) {
		this.sd02Mgsq = sd02Mgsq;
	}

	public BigDecimal getSd02Mgsr() {
		return sd02Mgsr;
	}

	public void setSd02Mgsr(BigDecimal sd02Mgsr) {
		this.sd02Mgsr = sd02Mgsr;
	}

	public String getSd02Mggb() {
		return sd02Mggb;
	}

	public void setSd02Mggb(String sd02Mggb) {
		this.sd02Mggb = sd02Mggb;
	}

	public String getSd02Tocd() {
		return sd02Tocd;
	}

	public void setSd02Tocd(String sd02Tocd) {
		this.sd02Tocd = sd02Tocd;
	}

	public String getSd02Toco() {
		return sd02Toco;
	}

	public void setSd02Toco(String sd02Toco) {
		this.sd02Toco = sd02Toco;
	}

	public String getSd02Styl() {
		return sd02Styl;
	}

	public void setSd02Styl(String sd02Styl) {
		this.sd02Styl = sd02Styl;
	}

	public String getSd02Stcd() {
		return sd02Stcd;
	}

	public void setSd02Stcd(String sd02Stcd) {
		this.sd02Stcd = sd02Stcd;
	}

	public String getSd02Mtag() {
		return sd02Mtag;
	}

	public void setSd02Mtag(String sd02Mtag) {
		this.sd02Mtag = sd02Mtag;
	}

	public BigDecimal getSd02Mkrn() {
		return sd02Mkrn;
	}

	public void setSd02Mkrn(BigDecimal sd02Mkrn) {
		this.sd02Mkrn = sd02Mkrn;
	}

	public BigDecimal getSd02Mqty() {
		return sd02Mqty;
	}

	public void setSd02Mqty(BigDecimal sd02Mqty) {
		this.sd02Mqty = sd02Mqty;
	}

	public String getSd02Bigo() {
		return sd02Bigo;
	}

	public void setSd02Bigo(String sd02Bigo) {
		this.sd02Bigo = sd02Bigo;
	}

	public String getSd02Tryn() {
		return sd02Tryn;
	}

	public void setSd02Tryn(String sd02Tryn) {
		this.sd02Tryn = sd02Tryn;
	}

	public String getSd02Trdt() {
		return sd02Trdt;
	}

	public void setSd02Trdt(String sd02Trdt) {
		this.sd02Trdt = sd02Trdt;
	}

	public String getSd02Cfyn() {
		return sd02Cfyn;
	}

	public void setSd02Cfyn(String sd02Cfyn) {
		this.sd02Cfyn = sd02Cfyn;
	}

	public String getSd02Cfdt() {
		return sd02Cfdt;
	}

	public void setSd02Cfdt(String sd02Cfdt) {
		this.sd02Cfdt = sd02Cfdt;
	}

	public String getSd02Cfid() {
		return sd02Cfid;
	}

	public void setSd02Cfid(String sd02Cfid) {
		this.sd02Cfid = sd02Cfid;
	}

	public BigDecimal getSd02Cfqt() {
		return sd02Cfqt;
	}

	public void setSd02Cfqt(BigDecimal sd02Cfqt) {
		this.sd02Cfqt = sd02Cfqt;
	}

	public String getSd02Sbdt() {
		return sd02Sbdt;
	}

	public void setSd02Sbdt(String sd02Sbdt) {
		this.sd02Sbdt = sd02Sbdt;
	}

	public BigDecimal getSd02Chsq() {
		return sd02Chsq;
	}

	public void setSd02Chsq(BigDecimal sd02Chsq) {
		this.sd02Chsq = sd02Chsq;
	}

	public BigDecimal getSd02Bpsq() {
		return sd02Bpsq;
	}

	public void setSd02Bpsq(BigDecimal sd02Bpsq) {
		this.sd02Bpsq = sd02Bpsq;
	}

	public String getSd02Engb() {
		return sd02Engb;
	}

	public void setSd02Engb(String sd02Engb) {
		this.sd02Engb = sd02Engb;
	}

	public String getSd02Enid() {
		return sd02Enid;
	}

	public void setSd02Enid(String sd02Enid) {
		this.sd02Enid = sd02Enid;
	}

	public String getSd02Entm() {
		return sd02Entm;
	}

	public void setSd02Entm(String sd02Entm) {
		this.sd02Entm = sd02Entm;
	}

	public String getSd02Upid() {
		return sd02Upid;
	}

	public void setSd02Upid(String sd02Upid) {
		this.sd02Upid = sd02Upid;
	}

	public String getSd02Uptm() {
		return sd02Uptm;
	}

	public void setSd02Uptm(String sd02Uptm) {
		this.sd02Uptm = sd02Uptm;
	}


}
