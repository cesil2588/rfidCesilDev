package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidZa40IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_za40_if")
public class RfidZa40If implements Serializable{

	private static final long serialVersionUID = -2500600744362852454L;

	@EmbeddedId
	private RfidZa40IfKey key;

	@Column(name="za40_sosk", nullable = false, length = 2)
    private String za40Sosk;

	@Column(name="za40_slyn", nullable = false, length = 2)
    private String za40Slyn;

	@Column(name="za40_name", nullable = false, length = 40)
    private String za40Name;

	@Column(name="za40_gras", nullable = false, length = 20)
    private String za40Gras;

	@Column(name="za40_corn", nullable = false, length = 2)
    private String za40Corn;

	@Column(name="za40_time", nullable = false, length = 20)
    private String za40Time;

	@Column(name="za40_stat", nullable = false, length = 2)
    private String za40Stat;

	@Column(name="za40_tryn", nullable = false, length = 2)
    private String za40Tryn;

	@Column(name="za40_trdt", nullable = false, length = 8)
    private Date za40Trdt;

	public RfidZa40IfKey getKey() {
		return key;
	}

	public void setKey(RfidZa40IfKey key) {
		this.key = key;
	}

	public String getZa40Sosk() {
		return za40Sosk;
	}

	public void setZa40Sosk(String za40Sosk) {
		this.za40Sosk = za40Sosk;
	}

	public String getZa40Slyn() {
		return za40Slyn;
	}

	public void setZa40Slyn(String za40Slyn) {
		this.za40Slyn = za40Slyn;
	}

	public String getZa40Name() {
		return za40Name;
	}

	public void setZa40Name(String za40Name) {
		this.za40Name = za40Name;
	}

	public String getZa40Gras() {
		return za40Gras;
	}

	public void setZa40Gras(String za40Gras) {
		this.za40Gras = za40Gras;
	}

	public String getZa40Corn() {
		return za40Corn;
	}

	public void setZa40Corn(String za40Corn) {
		this.za40Corn = za40Corn;
	}

	public String getZa40Time() {
		return za40Time;
	}

	public void setZa40Time(String za40Time) {
		this.za40Time = za40Time;
	}

	public String getZa40Stat() {
		return za40Stat;
	}

	public void setZa40Stat(String za40Stat) {
		this.za40Stat = za40Stat;
	}

	public String getZa40Tryn() {
		return za40Tryn;
	}

	public void setZa40Tryn(String za40Tryn) {
		this.za40Tryn = za40Tryn;
	}

	public Date getZa40Trdt() {
		return za40Trdt;
	}

	public void setZa40Trdt(Date za40Trdt) {
		this.za40Trdt = za40Trdt;
	}

}
