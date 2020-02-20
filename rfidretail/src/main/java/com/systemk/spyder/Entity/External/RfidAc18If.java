package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_ac18_if")
public class RfidAc18If implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 4179396231119298921L;

	@EmbeddedId
	private RfidAc18IfKey key;

	@Column(name="ac18_styl", nullable = false, length = 20)
    private String ac18Styl;

	@Column(name="ac18_jjch", nullable = false, length = 3)
    private String ac18Jjch;

	@Column(name="ac18_stcd", nullable = false, length = 20)
    private String ac18Stcd;

	@Column(name="ac18_it06", nullable = false, length = 5)
    private String ac18It06;

	@Column(name="ac18_it07", nullable = false, length = 5)
    private String ac18It07;

	@Column(name="ac18_btqt")
    private BigDecimal ac18Btqt;

    @Column(name="ac18_prod", nullable = false, length = 10)
    private String ac18Prod;

    @Column(name="ac18_cor1", nullable = false, length = 1)
    private String ac18Cor1;

    @Column(name="ac18_jgrs", nullable = true, length = 1)
    private String ac18Jgrs;

    @Column(name="ac18_erpk", nullable = false, length = 10)
    private String ac18Erpk;

    @Column(name="ac18_data", nullable = false, length = 100)
    private String ac18Data;

    @Column(name="ac18_time", nullable = false, length = 10)
    private String ac18Time;

    @Column(name="ac18_tryn", nullable = false, length = 1)
    private String ac18Tryn;

    @Column(name="ac18_trdt", nullable = true)
    private Date ac18Trdt;

    @Column(name="ac18_sndt", nullable = true)
    private Date ac18Sndt;

    @Column(name="ac18_prdt", nullable = true)
    private Date ac18Prdt;

    @Column(name="ac18_snyn", nullable = true, length = 1)
    private String ac18Snyn;

    @Column(name="ac18_pryn", nullable = true, length = 1)
    private String ac18Pryn;

    public String getAc18Styl() {
        return ac18Styl;
    }

    public void setAc18Styl(String ac18Styl) {
        this.ac18Styl = ac18Styl;
    }

    public String getAc18Jjch() {
        return ac18Jjch;
    }

    public void setAc18Jjch(String ac18Jjch) {
        this.ac18Jjch = ac18Jjch;
    }

    public String getAc18Stcd() {
        return ac18Stcd;
    }

    public void setAc18Stcd(String ac18Stcd) {
        this.ac18Stcd = ac18Stcd;
    }

    public String getAc18It06() {
        return ac18It06;
    }

    public void setAc18It06(String ac18It06) {
        this.ac18It06 = ac18It06;
    }

    public String getAc18It07() {
        return ac18It07;
    }

    public void setAc18It07(String ac18It07) {
        this.ac18It07 = ac18It07;
    }

    public String getAc18Data() {
        return ac18Data;
    }

    public void setAc18Data(String ac18Data) {
        this.ac18Data = ac18Data;
    }

    public String getAc18Time() {
        return ac18Time;
    }

    public void setAc18Time(String ac18Time) {
        this.ac18Time = ac18Time;
    }

    public String getAc18Tryn() {
        return ac18Tryn;
    }

    public void setAc18Tryn(String ac18Tryn) {
        this.ac18Tryn = ac18Tryn;
    }

	public BigDecimal getAc18Btqt() {
		return ac18Btqt;
	}

	public void setAc18Btqt(BigDecimal ac18Btqt) {
		this.ac18Btqt = ac18Btqt;
	}

	public Date getAc18Trdt() {
		return ac18Trdt;
	}

	public void setAc18Trdt(Date ac18Trdt) {
		this.ac18Trdt = ac18Trdt;
	}

	public RfidAc18IfKey getKey() {
		return key;
	}

	public void setKey(RfidAc18IfKey key) {
		this.key = key;
	}

	public String getAc18Prod() {
		return ac18Prod;
	}

	public void setAc18Prod(String ac18Prod) {
		this.ac18Prod = ac18Prod;
	}

	public String getAc18Cor1() {
		return ac18Cor1;
	}

	public void setAc18Cor1(String ac18Cor1) {
		this.ac18Cor1 = ac18Cor1;
	}

	public String getAc18Erpk() {
		return ac18Erpk;
	}

	public void setAc18Erpk(String ac18Erpk) {
		this.ac18Erpk = ac18Erpk;
	}

	public Date getAc18Sndt() {
		return ac18Sndt;
	}

	public void setAc18Sndt(Date ac18Sndt) {
		this.ac18Sndt = ac18Sndt;
	}

	public Date getAc18Prdt() {
		return ac18Prdt;
	}

	public void setAc18Prdt(Date ac18Prdt) {
		this.ac18Prdt = ac18Prdt;
	}

	public String getAc18Snyn() {
		return ac18Snyn;
	}

	public void setAc18Snyn(String ac18Snyn) {
		this.ac18Snyn = ac18Snyn;
	}

	public String getAc18Pryn() {
		return ac18Pryn;
	}

	public void setAc18Pryn(String ac18Pryn) {
		this.ac18Pryn = ac18Pryn;
	}

	public String getAc18Jgrs() {
		return ac18Jgrs;
	}

	public void setAc18Jgrs(String ac18Jgrs) {
		this.ac18Jgrs = ac18Jgrs;
	}
}
