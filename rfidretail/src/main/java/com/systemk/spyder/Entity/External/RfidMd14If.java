package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidMd14IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_md14_if")
public class RfidMd14If implements Serializable{

	private static final long serialVersionUID = 3305971304036044811L;

	@EmbeddedId
	private RfidMd14IfKey key;

    @Column(name="md14_mjlq")
    private BigDecimal  md14Mjlq;

    @Column(name="md14_endt")
    private String      md14Endt;

    @Column(name="md14_tryn")
    private String      md14Tryn;

    @Column(name="md14_trdt")
    private Date        md14Trdt;

	public RfidMd14IfKey getKey() {
		return key;
	}

	public void setKey(RfidMd14IfKey key) {
		this.key = key;
	}

    public BigDecimal getMd14Mjlq() {
        return md14Mjlq;
    }

    public void setMd14Mjlq(BigDecimal md14Mjlq) {
        this.md14Mjlq = md14Mjlq;
    }

    public String getMd14Endt() {
        return md14Endt;
    }

    public void setMd14Endt(String md14Endt) {
        this.md14Endt = md14Endt;
    }

    public String getMd14Tryn() {
        return md14Tryn;
    }

    public void setMd14Tryn(String md14Tryn) {
        this.md14Tryn = md14Tryn;
    }

    public Date getMd14Trdt() {
        return md14Trdt;
    }

    public void setMd14Trdt(Date md14Trdt) {
        this.md14Trdt = md14Trdt;
    }
}