package com.systemk.spyder.Service.CustomBean;

public class TextUploadValid {

	private Long bartagSeq;
	
	private int count;
	
	private String CreateDate;
	
	private Long seq;
	
	private Long lineSeq;
	
	private Long amount;

	public Long getBartagSeq() {
		return bartagSeq;
	}

	public void setBartagSeq(Long bartagSeq) {
		this.bartagSeq = bartagSeq;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Long lineSeq) {
		this.lineSeq = lineSeq;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TextUploadValid [bartagSeq=" + bartagSeq + ", count=" + count + ", CreateDate=" + CreateDate + ", seq="
				+ seq + ", lineSeq=" + lineSeq + ", amount=" + amount + "]";
	}
	
	
}
