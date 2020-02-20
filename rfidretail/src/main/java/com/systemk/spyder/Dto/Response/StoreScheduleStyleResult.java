package com.systemk.spyder.Dto.Response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleSubDetailLog;

/**
 * 매장입고예정 스타일정보
 * @author escho
 *
 */
public class StoreScheduleStyleResult implements Serializable{

	private static final long serialVersionUID = -15798522237318898L;

	private String style;

	private String color;

	private String size;

	private String erpKey;

	private Long amount;

	private String rfidYn;

	// 플래그(S: 신규 스타일, T: 신규 태그 추가, N: 변경 없음)
	@JsonInclude(Include.NON_NULL)
	private String flag;

	// 맵핑 태그
	@JsonInclude(Include.NON_DEFAULT)
	private Set<StoreScheduleTagResult> tagList = new HashSet<StoreScheduleTagResult>();

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

	public Set<StoreScheduleTagResult> getTagList() {
		return tagList;
	}

	public void setTagList(Set<StoreScheduleTagResult> tagList) {
		this.tagList = tagList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public StoreScheduleStyleResult() {

	}

	public StoreScheduleStyleResult(ReleaseScheduleDetailLog param) {

		this.style = param.getStyle();
		this.color = param.getColor();
		this.size = param.getSize();
		this.erpKey = param.getRfidYn().equals("Y") ? param.getErpKey() : "";
		this.amount = param.getAmount();
		this.rfidYn = param.getRfidYn();

		// 태그 정보 셋팅
		for(ReleaseScheduleSubDetailLog subDetail : param.getReleaseScheduleSubDetailLog()) {
			this.tagList.add(new StoreScheduleTagResult(subDetail));
		}
	}
}
