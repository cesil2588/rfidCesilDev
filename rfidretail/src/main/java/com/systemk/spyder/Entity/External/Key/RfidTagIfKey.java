package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidTagIfKey implements Serializable{

	private static final long serialVersionUID = -5439704923721030600L;

	@Column(name="tag_crdt")
	private String tagCrdt;

	@Column(name="tag_crsq")
    private BigDecimal tagCrsq;

	@Column(name="tag_crno")
    private BigDecimal tagCrno;
	
	@Column(name="tag_rfid")
	private String tagRfid;

	public String getTagCrdt() {
		return tagCrdt;
	}

	public void setTagCrdt(String tagCrdt) {
		this.tagCrdt = tagCrdt;
	}

	public BigDecimal getTagCrsq() {
		return tagCrsq;
	}

	public void setTagCrsq(BigDecimal tagCrsq) {
		this.tagCrsq = tagCrsq;
	}

	public BigDecimal getTagCrno() {
		return tagCrno;
	}

	public void setTagCrno(BigDecimal tagCrno) {
		this.tagCrno = tagCrno;
	}

	public String getTagRfid() {
		return tagRfid;
	}

	public void setTagRfid(String tagRfid) {
		this.tagRfid = tagRfid;
	}
}
