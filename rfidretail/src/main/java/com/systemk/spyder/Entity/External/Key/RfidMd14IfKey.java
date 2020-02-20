package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidMd14IfKey implements Serializable{

	private static final long serialVersionUID = 5610843740347575880L;

	@Column(name="md14_mjcd")
	private String md14Mjcd;

	@Column(name="md14_corn")
    private String md14Corn;

	@Column(name="md14_bsdt")
    private String md14Bsdt;

	@Column(name="md14_engb")
    private String md14Engb;

	@Column(name="md14_styl")
    private String md14Styl;

	@Column(name="md14_stcd")
	private String md14Stcd;

	public String getMd14Mjcd() {
		return md14Mjcd;
	}

	public void setMd14Mjcd(String md14Mjcd) {
		this.md14Mjcd = md14Mjcd;
	}

	public String getMd14Corn() {
		return md14Corn;
	}

	public void setMd14Corn(String md14Corn) {
		this.md14Corn = md14Corn;
	}

	public String getMd14Bsdt() {
		return md14Bsdt;
	}

	public void setMd14Bsdt(String md14Bsdt) {
		this.md14Bsdt = md14Bsdt;
	}

	public String getMd14Engb() {
		return md14Engb;
	}

	public void setMd14Engb(String md14Engb) {
		this.md14Engb = md14Engb;
	}

	public String getMd14Styl() {
		return md14Styl;
	}

	public void setMd14Styl(String md14Styl) {
		this.md14Styl = md14Styl;
	}

	public String getMd14Stcd() {
		return md14Stcd;
	}

	public void setMd14Stcd(String md14Stcd) {
		this.md14Stcd = md14Stcd;
	}
}
