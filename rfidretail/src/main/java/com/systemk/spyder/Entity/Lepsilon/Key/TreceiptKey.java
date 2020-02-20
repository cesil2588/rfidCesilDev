package com.systemk.spyder.Entity.Lepsilon.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TreceiptKey implements Serializable{
	
	private static final long serialVersionUID = 2817780992396667992L;

	@Column(name="WCODE", nullable = false, length = 20)
	private String wCode;
	
	@Column(name="RECEIPTNO", nullable = false, length = 20)
	private String receiptno;

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public String getwCode() {
		return wCode;
	}

	public void setwCode(String wCode) {
		this.wCode = wCode;
	}
	
	
}
