package com.systemk.spyder.Entity.Lepsilon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptKey;

@Entity
@Table(name="TRECEIPT") 
public class Treceipt implements Serializable{ 
	
	private static final long serialVersionUID = 6341844744681065098L;

	@EmbeddedId
	private TreceiptKey key;

	public TreceiptKey getKey() {
		return key;
	}

	public void setKey(TreceiptKey key) {
		this.key = key;
	}
	
	@Column(name="CUSTOMERPONO", nullable = true, length = 20)
	private String customerPoNo;

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	
}
