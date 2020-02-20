package com.systemk.spyder.Entity.Lepsilon.Key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TboxPickKey implements Serializable{

	private static final long serialVersionUID = 2533601509430258612L;

	@Column(name="SHIPMENTNO", nullable = false, length = 20)
	private String shipmentNo;
	
	@Column(name="LINENO", nullable = false, length = 20)
	private String lineNo;

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	
}
