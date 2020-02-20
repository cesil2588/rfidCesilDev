package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidZa11IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_za11_if")
public class RfidZa11If implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2500600744362852454L;

	@EmbeddedId
	private RfidZa11IfKey key;

	@Column(name="za11_gras", nullable = false, length = 10)
    private String za11Gras;

	@Column(name="za11_corn", nullable = false, length = 1)
    private String za11Corn;

	@Column(name="za11_grnm", nullable = false, length = 60)
    private String za11Grnm;

	@Column(name="za11_cnam", nullable = false, length = 14)
    private String za11Cnam;

	@Column(name="za11_grac", nullable = false, length = 2)
    private String za11Grac;

	@Column(name="za11_grst", nullable = false, length = 1)
    private String za11Grst;

	@Column(name="za11_teln", nullable = false, length = 16)
    private String za11Teln;

	@Column(name="za11_adr1", nullable = false, length = 35)
    private String za11Adr1;

	@Column(name="za11_adr2", nullable = false, length = 35)
    private String za11Adr2;

	@Column(name="za11_time", nullable = false, length = 10)
    private String za11Time;

	@Column(name="za11_stat", nullable = false, length = 1)
    private String za11Stat;

	@Column(name="za11_tryn", nullable = false, length = 1)
    private String za11Tryn;

	@Column(name="za11_trdt", nullable = true)
    private Date za11Trdt;

	public Date getZa11Trdt() {
		return za11Trdt;
	}

	public void setZa11Trdt(Date za11Trdt) {
		this.za11Trdt = za11Trdt;
	}

    public String getZa11Gras() {
        return za11Gras;
    }

    public void setZa11Gras(String za11Gras) {
        this.za11Gras = za11Gras;
    }

    public String getZa11Corn() {
        return za11Corn;
    }

    public void setZa11Corn(String za11Corn) {
        this.za11Corn = za11Corn;
    }

    public String getZa11Grnm() {
        return za11Grnm;
    }

    public void setZa11Grnm(String za11Grnm) {
        this.za11Grnm = za11Grnm;
    }

    public String getZa11Cnam() {
        return za11Cnam;
    }

    public void setZa11Cnam(String za11Cnam) {
        this.za11Cnam = za11Cnam;
    }

    public String getZa11Grac() {
        return za11Grac;
    }

    public void setZa11Grac(String za11Grac) {
        this.za11Grac = za11Grac;
    }

    public String getZa11Grst() {
        return za11Grst;
    }

    public void setZa11Grst(String za11Grst) {
        this.za11Grst = za11Grst;
    }

    public String getZa11Teln() {
        return za11Teln;
    }

    public void setZa11Teln(String za11Teln) {
        this.za11Teln = za11Teln;
    }

    public String getZa11Adr1() {
        return za11Adr1;
    }

    public void setZa11Adr1(String za11Adr1) {
        this.za11Adr1 = za11Adr1;
    }

    public String getZa11Adr2() {
        return za11Adr2;
    }

    public void setZa11Adr2(String za11Adr2) {
        this.za11Adr2 = za11Adr2;
    }

    public String getZa11Time() {
        return za11Time;
    }

    public void setZa11Time(String za11Time) {
        this.za11Time = za11Time;
    }

    public String getZa11Stat() {
        return za11Stat;
    }

    public void setZa11Stat(String za11Stat) {
        this.za11Stat = za11Stat;
    }

    public String getZa11Tryn() {
        return za11Tryn;
    }

    public void setZa11Tryn(String za11Tryn) {
        this.za11Tryn = za11Tryn;
    }

	public RfidZa11IfKey getKey() {
		return key;
	}

	public void setKey(RfidZa11IfKey key) {
		this.key = key;
	}

}
