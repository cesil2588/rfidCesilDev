package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidAc16IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_ac16_if")
public class RfidAc16If implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 2175189869892692619L;

	@EmbeddedId
    private RfidAc16IfKey key;

	@Column(name="ac16_erpk", nullable = false, length = 10)
    private String ac16Erpk;

	@Column(name="ac16_styl", nullable = false, length = 20)
    private String ac16Styl;

	@Column(name="ac16_stcd", nullable = false, length = 20)
    private String ac16Stcd;

	@Column(name="ac16_jpyy", nullable = false, length = 4)
    private String ac16Jpyy;

	@Column(name="ac16_jpss", nullable = false, length = 5)
    private String ac16Jpss;

	@Column(name="ac16_it06", nullable = false, length = 5)
    private String ac16It06;

	@Column(name="ac16_it07", nullable = false, length = 5)
    private String ac16It07;

	@Column(name="ac16_time", nullable = false, length = 10)
    private String ac16Time;

	@Column(name="ac16_stat", nullable = false, length = 1)
    private String ac16Stat;

	@Column(name="ac16_tryn", nullable = false, length = 1)
    private String ac16Tryn;

	@Column(name="ac16_trdt", nullable = true)
    private Date ac16Trdt;

	public String getAc16Erpk() {
		return ac16Erpk;
	}

	public void setAc16Erpk(String ac16Erpk) {
		this.ac16Erpk = ac16Erpk;
	}

	public String getAc16Styl() {
		return ac16Styl;
	}

	public void setAc16Styl(String ac16Styl) {
		this.ac16Styl = ac16Styl;
	}

	public String getAc16Stcd() {
		return ac16Stcd;
	}

	public void setAc16Stcd(String ac16Stcd) {
		this.ac16Stcd = ac16Stcd;
	}

	public String getAc16Jpyy() {
		return ac16Jpyy;
	}

	public void setAc16Jpyy(String ac16Jpyy) {
		this.ac16Jpyy = ac16Jpyy;
	}

	public String getAc16Jpss() {
		return ac16Jpss;
	}

	public void setAc16Jpss(String ac16Jpss) {
		this.ac16Jpss = ac16Jpss;
	}

	public String getAc16It06() {
		return ac16It06;
	}

	public void setAc16It06(String ac16It06) {
		this.ac16It06 = ac16It06;
	}

	public String getAc16It07() {
		return ac16It07;
	}

	public void setAc16It07(String ac16It07) {
		this.ac16It07 = ac16It07;
	}

	public String getAc16Time() {
		return ac16Time;
	}

	public void setAc16Time(String ac16Time) {
		this.ac16Time = ac16Time;
	}

	public String getAc16Stat() {
		return ac16Stat;
	}

	public void setAc16Stat(String ac16Stat) {
		this.ac16Stat = ac16Stat;
	}

	public String getAc16Tryn() {
		return ac16Tryn;
	}

	public void setAc16Tryn(String ac16Tryn) {
		this.ac16Tryn = ac16Tryn;
	}

	public Date getAc16Trdt() {
		return ac16Trdt;
	}

	public void setAc16Trdt(Date ac16Trdt) {
		this.ac16Trdt = ac16Trdt;
	}

	public RfidAc16IfKey getKey() {
		return key;
	}

	public void setKey(RfidAc16IfKey key) {
		this.key = key;
	}

}
