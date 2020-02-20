package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd06IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_sd06_if")
public class RfidSd06If implements Serializable{

	/**
	 * 매장 이동지시 Entity
	 */
	private static final long serialVersionUID = 5927764340062265066L;
	
	@EmbeddedId
	private RfidSd06IfKey key;
	
	//from매장코드
	@Column(name="sd06_frcd")
	private String sd06Frcd;
	
	//from매장 코너코드
	@Column(name="sd06_frco")
	private String sd06Frco;
	
	//to매장코드
	@Column(name="sd06_tocd")
	private String sd06Tocd;
	
	//to매장 코너코드
	@Column(name="sd06_toco")
	private String sd06Toco;
	
	//스타일
	@Column(name="sd06_styl")
	private String sd06Styl;
	
	//나머지 제품코드
	@Column(name="sd06_stcd")
	private String sd06Stcd;
	
	//이동지시수량
	@Column(name="sd06_idqt")
	private Long sd06Idqt;
	
	//이동마감일자
	@Column(name="sd06_imdt")
	private String sd06Imdt;
	
	//지시자 확정여부
	@Column(name="sd06_cfm1")
	private String sd06Cfm1;
	
	//지시 확정일자
	@Column(name="sd06_cfmd")
	private String sd06Cfmd;
	
	//비고
	@Column(name="sd06_bigo")
	private String sd06Bigo;
	
	//변경상태
	@Column(name="sd06_stat")
	private String sd06Stat;
	
	//등록일시
	@Column(name="sd06_endt")
	private Date sd06Endt;
	
	//전송여부
	@Column(name="sd06_tryn")
	private String sd06Tryn;
	
	//전송일시
	@Column(name="sd06_trdt")
	private Date sd06Trdt;

	public RfidSd06IfKey getKey() {
		return key;
	}

	public void setKey(RfidSd06IfKey key) {
		this.key = key;
	}

	public String getSd06Frcd() {
		return sd06Frcd;
	}

	public void setSd06Frcd(String sd06Frcd) {
		this.sd06Frcd = sd06Frcd;
	}

	public String getSd06Frco() {
		return sd06Frco;
	}

	public void setSd06Frco(String sd06Frco) {
		this.sd06Frco = sd06Frco;
	}

	public String getSd06Tocd() {
		return sd06Tocd;
	}

	public void setSd06Tocd(String sd06Tocd) {
		this.sd06Tocd = sd06Tocd;
	}

	public String getSd06Toco() {
		return sd06Toco;
	}

	public void setSd06Toco(String sd06Toco) {
		this.sd06Toco = sd06Toco;
	}

	public String getSd06Styl() {
		return sd06Styl;
	}

	public void setSd06Styl(String sd06Styl) {
		this.sd06Styl = sd06Styl;
	}

	public String getSd06Stcd() {
		return sd06Stcd;
	}

	public void setSd06Stcd(String sd06Stcd) {
		this.sd06Stcd = sd06Stcd;
	}

	public Long getSd06Idqt() {
		return sd06Idqt;
	}

	public void setSd06Idqt(Long sd06Idqt) {
		this.sd06Idqt = sd06Idqt;
	}

	public String getSd06Imdt() {
		return sd06Imdt;
	}

	public void setSd06Imdt(String sd06Imdt) {
		this.sd06Imdt = sd06Imdt;
	}

	public String getSd06Cfm1() {
		return sd06Cfm1;
	}

	public void setSd06Cfm1(String sd06Cfm1) {
		this.sd06Cfm1 = sd06Cfm1;
	}

	public String getSd06Cfmd() {
		return sd06Cfmd;
	}

	public void setSd06Cfmd(String sd06Cfmd) {
		this.sd06Cfmd = sd06Cfmd;
	}

	public String getSd06Bigo() {
		return sd06Bigo;
	}

	public void setSd06Bigo(String sd06Bigo) {
		this.sd06Bigo = sd06Bigo;
	}

	public String getSd06Stat() {
		return sd06Stat;
	}

	public void setSd06Stat(String sd06Stat) {
		this.sd06Stat = sd06Stat;
	}

	public Date getSd06Endt() {
		return sd06Endt;
	}

	public void setSd06Endt(Date sd06Endt) {
		this.sd06Endt = sd06Endt;
	}

	public String getSd06Tryn() {
		return sd06Tryn;
	}

	public void setSd06Tryn(String sd06Tryn) {
		this.sd06Tryn = sd06Tryn;
	}

	public Date getSd06Trdt() {
		return sd06Trdt;
	}

	public void setSd06Trdt(Date sd06Trdt) {
		this.sd06Trdt = sd06Trdt;
	}

}
