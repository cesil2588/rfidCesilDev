package com.systemk.spyder.Entity.External;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd01IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Table(name="rfid_sd01_if")
public class RfidSd01If implements Serializable{

	/**
	 * 매장간 이동 신규매장 등록 확정 반영 ERP Entity
	 */
	private static final long serialVersionUID = 2966340041981751808L;
	
	@EmbeddedId
	private RfidSd01IfKey Key;
	
	//미결구분
	@Column(name="sd01_mggb")
	private String sd01Mggb;
	
	//to거래처 코드
	@Column(name="sd01_tocd")
	private String sd01Tocd;
	
	//to거래처 코너코드
	@Column(name="sd01_toco")
	private String sd01Toco;
	
	//스타일
	@Column(name="sd01_styl")
	private String sd01Styl;
	
	//나머지 제품코드
	@Column(name="sd01_stcd")
	private String sd01Stcd;
	
	//미결수량
	@Column(name="sd01_mqty")
	private Long sd01Mqty;
	
	//비고
	@Column(name="sd01_bigo",insertable=false)
	private String sd01Bigo;
	
	//미결발생일
	@Column(name="sd01_mgdt")
	private String sd01Mgdt;
	
	//일련번호
	@Column(name="sd01_mgsq")
	private Long sd01Mgsq;
	
	//미결 Serial
	@Column(name="sd01_mgsr")
	private Long sd01Mgsr;
	
	//등록자
	@Column(name="sd01_enid")
	private String sd01Enid;
	
	//등록일시
	@Column(name="sd01_entm")
	private String sd01Entm;

	public RfidSd01IfKey getKey() {
		return Key;
	}

	public void setKey(RfidSd01IfKey key) {
		Key = key;
	}

	public String getSd01Mggb() {
		return sd01Mggb;
	}

	public void setSd01Mggb(String sd01Mggb) {
		this.sd01Mggb = sd01Mggb;
	}

	public String getSd01Tocd() {
		return sd01Tocd;
	}

	public void setSd01Tocd(String sd01Tocd) {
		this.sd01Tocd = sd01Tocd;
	}

	public String getSd01Toco() {
		return sd01Toco;
	}

	public void setSd01Toco(String sd01Toco) {
		this.sd01Toco = sd01Toco;
	}

	public String getSd01Styl() {
		return sd01Styl;
	}

	public void setSd01Styl(String sd01Styl) {
		this.sd01Styl = sd01Styl;
	}

	public String getSd01Stcd() {
		return sd01Stcd;
	}

	public void setSd01Stcd(String sd01Stcd) {
		this.sd01Stcd = sd01Stcd;
	}

	public Long getSd01Mqty() {
		return sd01Mqty;
	}

	public void setSd01Mqty(Long sd01Mqty) {
		this.sd01Mqty = sd01Mqty;
	}

	public String getSd01Bigo() {
		return sd01Bigo;
	}

	public void setSd01Bigo(String sd01Bigo) {
		this.sd01Bigo = sd01Bigo;
	}

	public String getSd01Mgdt() {
		return sd01Mgdt;
	}

	public void setSd01Mgdt(String sd01Mgdt) {
		this.sd01Mgdt = sd01Mgdt;
	}

	public Long getSd01Mgsq() {
		return sd01Mgsq;
	}

	public void setSd01Mgsq(Long sd01Mgsq) {
		this.sd01Mgsq = sd01Mgsq;
	}

	public Long getSd01Mgsr() {
		return sd01Mgsr;
	}

	public void setSd01Mgsr(Long sd01Mgsr) {
		this.sd01Mgsr = sd01Mgsr;
	}

	public String getSd01Enid() {
		return sd01Enid;
	}

	public void setSd01Enid(String sd01Enid) {
		this.sd01Enid = sd01Enid;
	}

	public String getSd01Entm() {
		return sd01Entm;
	}

	public void setSd01Entm(String sd01Entm) {
		this.sd01Entm = sd01Entm;
	}


}
