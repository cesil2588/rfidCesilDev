package com.systemk.spyder.Dto.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Dto.Request.ProductionTagListBean;

public class ApiInspectionResult {

	public String resultMessage;

	public int resultCode;

	@JsonInclude(Include.NON_NULL)
	private List<ProductionTagListBean> inspectionList;

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public List<ProductionTagListBean> getInspectionList() {
		return inspectionList;
	}

	public void setInspectionList(List<ProductionTagListBean> inspectionList) {
		this.inspectionList = inspectionList;
	}

	public ApiInspectionResult(){

	}

	public ApiInspectionResult(String resultMessage, int resultCode) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
	}

	public ApiInspectionResult(String resultMessage, int resultCode, List<ProductionTagListBean> inspectionList) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
		this.inspectionList = inspectionList;
	}
}
