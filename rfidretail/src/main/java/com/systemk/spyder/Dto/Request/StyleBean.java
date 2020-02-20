package com.systemk.spyder.Dto.Request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StyleBean {

	@JsonInclude(Include.NON_NULL)
	public String style;

	@JsonInclude(Include.NON_NULL)
	public String color;

	@JsonInclude(Include.NON_NULL)
	public String size;

	@JsonInclude(Include.NON_NULL)
	public String erpKey;

	@JsonInclude(Include.NON_NULL)
	public String orderDegree;

	@JsonInclude(Include.NON_NULL)
	public String referenceNo;

	@JsonInclude(Include.NON_NULL)
	public Long count;

	@JsonInclude(Include.NON_NULL)
	public Long amount;

	@JsonInclude(Include.NON_NULL)
	public Long storageSeq;

	@JsonInclude(Include.NON_NULL)
	public String rfidYn;

	@JsonInclude(Include.NON_NULL)
	public List<RfidTagBean> rfidTagList;

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

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public List<RfidTagBean> getRfidTagList() {
		return rfidTagList;
	}

	public void setRfidTagList(List<RfidTagBean> rfidTagList) {
		this.rfidTagList = rfidTagList;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public Long getStorageSeq() {
		return storageSeq;
	}

	public void setStorageSeq(Long storageSeq) {
		this.storageSeq = storageSeq;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
}
