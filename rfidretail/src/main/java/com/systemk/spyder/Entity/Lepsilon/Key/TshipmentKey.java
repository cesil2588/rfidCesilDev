package com.systemk.spyder.Entity.Lepsilon.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TshipmentKey implements Serializable{
	
	private static final long serialVersionUID = 3762953870771506617L;

	@Column(name="WCODE", nullable = false, length = 20)
	private String wCode;
	
	@Column(name="SHIPMENTNO", nullable = false, length = 20)
	private String shipmentNo;

	public String getwCode() {
		return wCode;
	}

	public void setwCode(String wCode) {
		this.wCode = wCode;
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	
}
