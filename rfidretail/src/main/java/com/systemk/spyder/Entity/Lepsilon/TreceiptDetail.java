package com.systemk.spyder.Entity.Lepsilon;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Transient;

import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptDetailKey;

@Entity
@Table(name="TRECEIPTDETAIL")
public class TreceiptDetail implements Serializable{

	private static final long serialVersionUID = -6695494734833709980L;

	@EmbeddedId
	private TreceiptDetailKey key;
	
	@Transient
	@Column(name="STYLE", nullable = false, length = 20)
	private String style;
	
	@Transient
	@Column(name="COLOR", nullable = false, length = 10)
	private String color;
	
	@Transient
	@Column(name="STYLESIZE", nullable = false, length = 10)
	private String styleSize;
	
	@Transient
	private BigDecimal amount;
	
	@Column(name="STATUS", nullable = false, length = 3)
	private String status;
	
	@Column(name="DELFLAG", nullable = false, length = 1)
	private String delFlag;

	public TreceiptDetailKey getKey() {
		return key;
	}

	public void setKey(TreceiptDetailKey key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getStyleSize() {
		return styleSize;
	}

	public void setStyleSize(String styleSize) {
		this.styleSize = styleSize;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
