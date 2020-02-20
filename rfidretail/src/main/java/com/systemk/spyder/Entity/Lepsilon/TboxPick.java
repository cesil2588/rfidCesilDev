package com.systemk.spyder.Entity.Lepsilon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.systemk.spyder.Entity.Lepsilon.Key.TboxPickKey;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;

@Entity
@Table(name="TBOXPICK")
public class TboxPick implements Serializable{

	private static final long serialVersionUID = -3751844597842908713L;

	@EmbeddedId
	private TboxPickKey key;
	
	@Column(name="STOCK_NM", nullable = false, length = 100)
	private String stockNm;
	
	@Column(name="STOCK", nullable = false, length = 100)
	private String stock;
	
	@Column(name="PICKQTY")
	private BigDecimal pickQty;
	
	@Column(name="RFIDFLAG", nullable = false, length = 1)
	private String rfidFlag;
	
	@Column(name="ORDERQTY")
	private BigDecimal orderQty;
	
	@Column(name="WCODE", nullable = false, length = 10)
	private String wCode;
	
	@Transient
	private String referenceNo;
	
	@Transient
	private ErpStoreSchedule erpStoreSchedule;
	
	@Transient
	private Tshipment tshipment;

	public String getStockNm() {
		return stockNm;
	}

	public void setStockNm(String stockNm) {
		this.stockNm = stockNm;
	}

	public BigDecimal getPickQty() {
		return pickQty;
	}

	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}

	public String getRfidFlag() {
		return rfidFlag;
	}

	public void setRfidFlag(String rfidFlag) {
		this.rfidFlag = rfidFlag;
	}

	public BigDecimal getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}

	public TboxPickKey getKey() {
		return key;
	}

	public void setKey(TboxPickKey key) {
		this.key = key;
	}

	public String getwCode() {
		return wCode;
	}

	public void setwCode(String wCode) {
		this.wCode = wCode;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public ErpStoreSchedule getErpStoreSchedule() {
		return erpStoreSchedule;
	}

	public void setErpStoreSchedule(ErpStoreSchedule erpStoreSchedule) {
		this.erpStoreSchedule = erpStoreSchedule;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Tshipment getTshipment() {
		return tshipment;
	}

	public void setTshipment(Tshipment tshipment) {
		this.tshipment = tshipment;
	}

}
