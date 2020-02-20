package com.systemk.spyder.Entity.Lepsilon.Key;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TreceiptDetailKey implements Serializable{

	private static final long serialVersionUID = 7852484764891593198L;

	@Column(name="WCODE", nullable = false, length = 20)
	private String wCode;
	
	@Column(name="RECEIPTNO", nullable = false, length = 20)
	private String receiptNo;
	
	@Column(name="LINENO")
	private BigDecimal lineNo;

	public String getwCode() {
		return wCode;
	}

	public void setwCode(String wCode) {
		this.wCode = wCode;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}
}
