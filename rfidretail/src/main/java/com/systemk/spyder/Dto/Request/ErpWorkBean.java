package com.systemk.spyder.Dto.Request;

import java.util.*;

public class ErpWorkBean {
	
	
	public Long subSeq;
	
	public Long workBoxNum;
		
	public List<Map<String,Object>> rfidTagList;
	
	public Long seq;
	
	public String style;
	
	public String color;
	
	public String size;
	
	public Long amount;
	
	public String rfidYn;
	
	public String anotherYn;
	
	public Long userSeq;

	
	public Long getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(Long subSeq) {
		this.subSeq = subSeq;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getWorkBoxNum() {
		return workBoxNum;
	}

	public void setWorkBoxNum(Long workBoxNum) {
		this.workBoxNum = workBoxNum;
	}

	public List<Map<String,Object>> getRfidTagList() {
		return rfidTagList;
	}

	public void setRfidTagList(List<Map<String,Object>> rfidTagList) {
		this.rfidTagList = rfidTagList;
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

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public String getAnotherYn() {
		return anotherYn;
	}

	public void setAnotherYn(String anotherYn) {
		this.anotherYn = anotherYn;
	}

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}
	
}
