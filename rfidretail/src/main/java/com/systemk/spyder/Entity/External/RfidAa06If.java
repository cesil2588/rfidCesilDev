package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidAa06IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_aa06_if")
public class RfidAa06If implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 2175189869892692619L;

	@EmbeddedId
    private RfidAa06IfKey key;

    @Column(name="aa06_styl", nullable = false, length = 20)
    private String aa06Styl;

    @Column(name="aa06_jjch", nullable = false, length = 3)
    private String aa06Jjch;

    @Column(name="aa06_stcd", nullable = false, length = 20)
    private String aa06Stcd;

    @Column(name="aa06_it06", nullable = false, length = 5)
    private String aa06It06;

    @Column(name="aa06_it07", nullable = false, length = 5)
    private String aa06It07;

    @Column(name="aa06_cfqt", nullable = false)
    private BigDecimal aa06Cfqt;

    @Column(name="aa06_prod", nullable = false, length = 10)
    private String aa06Prod;

    @Column(name="aa06_jgrs", nullable = false, length = 10)
    private String aa06Jgrs;

    @Column(name="aa06_cor1", nullable = false, length = 1)
    private String aa06Cor1;

    @Column(name="aa06_jmdt", nullable = false, length = 8)
    private String aa06Jmdt;

    @Column(name="aa06_jmno", nullable = false)
    private BigDecimal aa06Jmno;

    @Column(name="aa06_time", nullable = false, length = 10)
    private String aa06Time;

    @Column(name="aa06_stat", nullable = false, length = 1)
    private String aa06Stat;

    @Column(name="aa06_tryn", nullable = false, length = 1)
    private String aa06Tryn;

    @Column(name="aa06_trdt", nullable = true)
    private Date aa06Trdt;

    public String getAa06Styl() {
        return aa06Styl;
    }

    public void setAa06Styl(String aa06Styl) {
        this.aa06Styl = aa06Styl;
    }

    public String getAa06Jjch() {
        return aa06Jjch;
    }

    public void setAa06Jjch(String aa06Jjch) {
        this.aa06Jjch = aa06Jjch;
    }

    public String getAa06Stcd() {
        return aa06Stcd;
    }

    public void setAa06Stcd(String aa06Stcd) {
        this.aa06Stcd = aa06Stcd;
    }

    public String getAa06It06() {
        return aa06It06;
    }

    public void setAa06It06(String aa06It06) {
        this.aa06It06 = aa06It06;
    }

    public String getAa06It07() {
        return aa06It07;
    }

    public void setAa06It07(String aa06It07) {
        this.aa06It07 = aa06It07;
    }

    public String getAa06Time() {
        return aa06Time;
    }

    public void setAa06Time(String aa06Time) {
        this.aa06Time = aa06Time;
    }

    public String getAa06Stat() {
        return aa06Stat;
    }

    public void setAa06Stat(String aa06Stat) {
        this.aa06Stat = aa06Stat;
    }

    public String getAa06Tryn() {
        return aa06Tryn;
    }

    public void setAa06Tryn(String aa06Tryn) {
        this.aa06Tryn = aa06Tryn;
    }

	public Date getAa06Trdt() {
		return aa06Trdt;
	}

	public void setAa06Trdt(Date aa06Trdt) {
		this.aa06Trdt = aa06Trdt;
	}

	public RfidAa06IfKey getKey() {
		return key;
	}

	public void setKey(RfidAa06IfKey key) {
		this.key = key;
	}

	public BigDecimal getAa06Cfqt() {
		return aa06Cfqt;
	}

	public void setAa06Cfqt(BigDecimal aa06Cfqt) {
		this.aa06Cfqt = aa06Cfqt;
	}

	public String getAa06Prod() {
		return aa06Prod;
	}

	public void setAa06Prod(String aa06Prod) {
		this.aa06Prod = aa06Prod;
	}

	public String getAa06Cor1() {
		return aa06Cor1;
	}

	public void setAa06Cor1(String aa06Cor1) {
		this.aa06Cor1 = aa06Cor1;
	}

	public String getAa06Jmdt() {
		return aa06Jmdt;
	}

	public void setAa06Jmdt(String aa06Jmdt) {
		this.aa06Jmdt = aa06Jmdt;
	}

	public BigDecimal getAa06Jmno() {
		return aa06Jmno;
	}

	public void setAa06Jmno(BigDecimal aa06Jmno) {
		this.aa06Jmno = aa06Jmno;
	}

	public String getAa06Jgrs() {
		return aa06Jgrs;
	}

	public void setAa06Jgrs(String aa06Jgrs) {
		this.aa06Jgrs = aa06Jgrs;
	}


}
