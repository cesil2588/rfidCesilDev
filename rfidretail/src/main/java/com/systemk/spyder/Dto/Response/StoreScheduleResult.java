package com.systemk.spyder.Dto.Response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

/**
 * 매장입고예정정보
 * @author escho
 *
 */
public class StoreScheduleResult implements Serializable{

	private static final long serialVersionUID = -4969238497695509938L;

	// 박스 타입
	@JsonInclude(Include.NON_NULL)
	private String type;
	
	// 출고날짜
	@JsonInclude(Include.NON_NULL)
	private String releaseDate;
	
	// 출발지
	@JsonInclude(Include.NON_NULL)
	private String startCompanyName;
	
	// 도착지
	@JsonInclude(Include.NON_NULL)
	private String endCompanyName;
	
	// 상태
	@JsonInclude(Include.NON_NULL)
	private String stat;
	
	// 바코드(운송장번호)
	private String barcode;
	
	// 맵핑 여부
	@JsonInclude(Include.NON_NULL)
	private String mappingYn;
	
	// 플래그(Y: 변경 있음, N: 변경 없음)
	@JsonInclude(Include.NON_NULL)
	private String flag;
	
	// 맵핑 스타일
	private Set<StoreScheduleStyleResult> styleList = new HashSet<StoreScheduleStyleResult>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getStartCompanyName() {
		return startCompanyName;
	}

	public void setStartCompanyName(String startCompanyName) {
		this.startCompanyName = startCompanyName;
	}

	public String getEndCompanyName() {
		return endCompanyName;
	}

	public void setEndCompanyName(String endCompanyName) {
		this.endCompanyName = endCompanyName;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Set<StoreScheduleStyleResult> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<StoreScheduleStyleResult> styleList) {
		this.styleList = styleList;
	}
	
	public String getMappingYn() {
		return mappingYn;
	}

	public void setMappingYn(String mappingYn) {
		this.mappingYn = mappingYn;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public StoreScheduleResult() {
		
	}

	public StoreScheduleResult(ReleaseScheduleLog param) {
		
		this.type = param.getBoxInfo().getType();
		
		//TODO ERP 출고날짜로 변경
		this.releaseDate = param.getBoxInfo().getCreateDate();
		this.startCompanyName = param.getBoxInfo().getStartCompanyInfo().getName();
		this.endCompanyName = param.getBoxInfo().getEndCompanyInfo().getName();
		this.stat = param.getBoxInfo().getStat();
		this.barcode = param.getBoxInfo().getBarcode();
		this.mappingYn = param.getMappingYn();
		
		// Style 데이터 셋팅
		for(ReleaseScheduleDetailLog detail : param.getReleaseScheduleDetailLog()) {
			this.styleList.add(new StoreScheduleStyleResult(detail));
		}
	}
}
