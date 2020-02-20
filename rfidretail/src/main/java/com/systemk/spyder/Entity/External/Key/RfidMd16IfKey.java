package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidMd16IfKey implements Serializable{

	private static final long serialVersionUID = 3272917395915339259L;

	@Column(name="md16_bsdt")
	private String md16Bsdt;

	@Column(name="md16_cgcd")
    private String md16Cgcd;

	@Column(name="md16_corn")
    private String md16Corn;

	@Column(name="md16_styl")
    private String md16Styl;

	@Column(name="md16_stcd")
    private String md16Stcd;

	public String getMd16Bsdt() {
		return md16Bsdt;
	}

	public void setMd16Bsdt(String md16Bsdt) {
		this.md16Bsdt = md16Bsdt;
	}

	public String getMd16Cgcd() {
		return md16Cgcd;
	}

	public void setMd16Cgcd(String md16Cgcd) {
		this.md16Cgcd = md16Cgcd;
	}

	public String getMd16Corn() {
		return md16Corn;
	}

	public void setMd16Corn(String md16Corn) {
		this.md16Corn = md16Corn;
	}

	public String getMd16Styl() {
		return md16Styl;
	}

	public void setMd16Styl(String md16Styl) {
		this.md16Styl = md16Styl;
	}

	public String getMd16Stcd() {
		return md16Stcd;
	}

	public void setMd16Stcd(String md16Stcd) {
		this.md16Stcd = md16Stcd;
	}
    
    
}
