package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd14IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_sd14_if")
public class RfidSd14If implements Serializable{

	/**
	 * ERP 매장 반품지시 Entity
	 */
	private static final long serialVersionUID = 6068165488236649063L;
	
	@EmbeddedId
	private RfidSd14IfKey key;
	
	//RFID전송대상여부
	@Column(name="sd14_rfyn")
	private String sd14Rfyn;
	
	//반품지시명
	@Column(name="sd14_note")
	private String sd14Note;
	
	//To창고코드
	@Column(name="sd14_tocd")
	private String sd14Tocd;
	
	//To창고코너코드
	@Column(name="sd14_toco")
	private String sd14Toco;
	
	//반품지시수량
	@Column(name="sd14_jsqt")
	private Long sd14Jsqt;
	
	//반품마감일자
	@Column(name="sd14_imdt")
	private String sd14Imdt;
	
	//변경상태(C:생성, U:수정, D:삭제, Z:완료)
	@Column(name="sd14_stat")
	private String sd14Stat;
	
	//등록일시
	@Column(name="sd14_endt")
	private Date sd14Endt;
	
	//전송여부
	@Column(name="sd14_tryn")
	private String sd14Tryn;
	
	//전송일시
	@Column(name="sd14_trdt")
	private Date sd14Trdt;


	public RfidSd14IfKey getKey() {
		return key;
	}

	public void setKey(RfidSd14IfKey key) {
		this.key = key;
	}

	public String getSd14Rfyn() {
		return sd14Rfyn;
	}

	public void setSd14Rfyn(String sd14Rfyn) {
		this.sd14Rfyn = sd14Rfyn;
	}

	public String getSd14Note() {
		return sd14Note;
	}

	public void setSd14Note(String sd14Note) {
		this.sd14Note = sd14Note;
	}

	public String getSd14Tocd() {
		return sd14Tocd;
	}

	public void setSd14Tocd(String sd14Tocd) {
		this.sd14Tocd = sd14Tocd;
	}

	public String getSd14Toco() {
		return sd14Toco;
	}

	public void setSd14Toco(String sd14Toco) {
		this.sd14Toco = sd14Toco;
	}

	public Long getSd14Jsqt() {
		return sd14Jsqt;
	}

	public void setSd14Jsqt(Long sd14Jsqt) {
		this.sd14Jsqt = sd14Jsqt;
	}

	public String getSd14Imdt() {
		return sd14Imdt;
	}

	public void setSd14Imdt(String sd14Imdt) {
		this.sd14Imdt = sd14Imdt;
	}

	public String getSd14Stat() {
		return sd14Stat;
	}

	public void setSd14Stat(String sd14Stat) {
		this.sd14Stat = sd14Stat;
	}

	public Date getSd14Endt() {
		return sd14Endt;
	}

	public void setSd14Endt(Date sd14Endt) {
		this.sd14Endt = sd14Endt;
	}

	public String getSd14Tryn() {
		return sd14Tryn;
	}

	public void setSd14Tryn(String sd14Tryn) {
		this.sd14Tryn = sd14Tryn;
	}

	public Date getSd14Trdt() {
		return sd14Trdt;
	}

	public void setSd14Trdt(Date sd14Trdt) {
		this.sd14Trdt = sd14Trdt;
	}
	
	

}
