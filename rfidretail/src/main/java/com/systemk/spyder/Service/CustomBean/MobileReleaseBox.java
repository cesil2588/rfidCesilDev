package com.systemk.spyder.Service.CustomBean;

public class MobileReleaseBox {

	private Long boxSeq;
	
	private String barcode;
	
	private String boxNum;
	
	private String type;
	
	private String returnYn;
	
	private Long startCompanySeq;
	
	private Long endCompanySeq;
	
	private String stat;

	public Long getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Long boxSeq) {
		this.boxSeq = boxSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReturnYn() {
		return returnYn;
	}

	public void setReturnYn(String returnYn) {
		this.returnYn = returnYn;
	}

	public Long getStartCompanySeq() {
		return startCompanySeq;
	}

	public void setStartCompanySeq(Long startCompanySeq) {
		this.startCompanySeq = startCompanySeq;
	}

	public Long getEndCompanySeq() {
		return endCompanySeq;
	}

	public void setEndCompanySeq(Long endCompanySeq) {
		this.endCompanySeq = endCompanySeq;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
}
