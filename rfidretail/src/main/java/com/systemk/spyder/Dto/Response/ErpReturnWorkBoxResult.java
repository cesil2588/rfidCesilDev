package com.systemk.spyder.Dto.Response;

public class ErpReturnWorkBoxResult {

	public int sumPerBox;
	
	public Long workBoxNum;
	
	public String completeYn;
	
	public String erpInvoiceNum;

	public int getSumPerBox() {
		return sumPerBox;
	}

	public void setSumPerBox(int sumPerBox) {
		this.sumPerBox = sumPerBox;
	}

	public Long getWorkBoxNum() {
		return workBoxNum;
	}

	public void setWorkBoxNum(Long workBoxNum) {
		this.workBoxNum = workBoxNum;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public String getErpInvoiceNum() {
		return erpInvoiceNum;
	}

	public void setErpInvoiceNum(String erpInvoiceNum) {
		this.erpInvoiceNum = erpInvoiceNum;
	}
	
	
}
