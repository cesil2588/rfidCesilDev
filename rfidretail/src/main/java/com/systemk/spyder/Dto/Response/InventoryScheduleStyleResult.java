package com.systemk.spyder.Dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;
import com.systemk.spyder.Entity.Main.InventoryScheduleTag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 재고조사 스타일 DTO
 * @author escho
 *
 */
public class InventoryScheduleStyleResult implements Serializable{

	private static final long serialVersionUID = 178635877165546992L;

	// 스타일
	private String style;

	// 컬러
	private String color;

	// 사이즈
	private String size;

	// 수량
	private Long amount;

	// 실사 수량
	private Long completeAmount;

	// 망실 수량
	private Long disuseAmount;

	// erp 키
	private String erpKey;

	// RFID 여부
	private String rfidYn;

	// 신규 여부(S: 신규 스타일)
	private String flag;

	// 태그 목록
	@JsonInclude(Include.NON_DEFAULT)
	private Set<InventoryScheduleTagResult> tagList = new HashSet<InventoryScheduleTagResult>();

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

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public Long getDisuseAmount() {
		return disuseAmount;
	}

	public void setDisuseAmount(Long disuseAmount) {
		this.disuseAmount = disuseAmount;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Set<InventoryScheduleTagResult> getTagList() {
		return tagList;
	}

	public void setTagList(Set<InventoryScheduleTagResult> tagList) {
		this.tagList = tagList;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public InventoryScheduleStyleResult() {

	}

	public InventoryScheduleStyleResult(InventoryScheduleStyle style) {
		this.style                      = style.getStyle();
		this.color                      = style.getColor();
		this.size                       = style.getSize();
		this.amount                     = style.getAmount();
		this.completeAmount             = style.getCompleteAmount();
		this.disuseAmount               = style.getDisuseAmount();
		this.erpKey                     = style.getErpKey();
		this.rfidYn						= style.getRfidYn();
		this.flag                       = style.getFlag();

		for(InventoryScheduleTag tag : style.getRfidTagList()) {
			this.tagList.add(new InventoryScheduleTagResult(tag));
		}
	}
}
