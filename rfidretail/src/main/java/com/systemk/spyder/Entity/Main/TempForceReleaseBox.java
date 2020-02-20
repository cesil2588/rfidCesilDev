package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempForceReleaseBox implements Serializable{

	private static final long serialVersionUID = 3891473636785868759L;

	@Id
	private Long seq;

	@Column(nullable = false, length = 32)
	private String boxBarcode;

	@Column(nullable = false, length = 15)
	private String style;

	@Column(nullable = false, length = 3)
	private String color;

	@Column(nullable = false, length = 3)
	private String size;

	private Long amount;

	@Column(nullable = false, length = 32)
	private String rfidTag;

	@Column(nullable = false, length = 1)
	private String boxFlag;

	@Column(nullable = false, length = 1)
	private String styleFlag;


	public String getBoxBarcode() {
		return boxBarcode;
	}

	public void setBoxBarcode(String boxBarcode) {
		this.boxBarcode = boxBarcode;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getBoxFlag() {
		return boxFlag;
	}

	public void setBoxFlag(String boxFlag) {
		this.boxFlag = boxFlag;
	}

	public String getStyleFlag() {
		return styleFlag;
	}

	public void setStyleFlag(String styleFlag) {
		this.styleFlag = styleFlag;
	}


}
