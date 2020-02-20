package com.systemk.spyder.Dto.Response;

public class BartagMinMaxResult {

	private String erpKey;

	private String startRfidSeq;

	private String endRfidSeq;

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
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

	public BartagMinMaxResult() {

	}

	public BartagMinMaxResult(String erpKey, String startRfidSeq, String endRfidSeq) {
		this.erpKey = erpKey;
		this.startRfidSeq = startRfidSeq;
		this.endRfidSeq = endRfidSeq;
	}
}
