package com.systemk.spyder.Service.CustomBean.Select;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SelectBartagModel {
	
	@JsonInclude(Include.NON_NULL)
	private String data;
	
	@JsonInclude(Include.NON_DEFAULT)
	private Long bartagSeq;
	
	@JsonInclude(Include.NON_DEFAULT)
	private Long seq;
	
	@JsonInclude(Include.NON_NULL)
	private String style;
	
	@JsonInclude(Include.NON_NULL)
	private String color;
	
	@JsonInclude(Include.NON_NULL)
	private String size;
	
	@JsonInclude(Include.NON_NULL)
	private String startRfidSeq;
	
	@JsonInclude(Include.NON_NULL)
	private String endRfidSeq;
	
	@JsonInclude(Include.NON_NULL)
	private String erpKey;
	
	@JsonInclude(Include.NON_NULL)
	private String explanatory;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getBartagSeq() {
		return bartagSeq;
	}

	public void setBartagSeq(Long bartagSeq) {
		this.bartagSeq = bartagSeq;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getStartRfidSeq() {
		return startRfidSeq;
	}

	public void setStartRfidSeq(String startRfidSeq) {
		this.startRfidSeq = startRfidSeq;
	}

	public String getEndRfidSeq() {
		return endRfidSeq;
	}

	public void setEndRfidSeq(String endRfidSeq) {
		this.endRfidSeq = endRfidSeq;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
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

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}
}
