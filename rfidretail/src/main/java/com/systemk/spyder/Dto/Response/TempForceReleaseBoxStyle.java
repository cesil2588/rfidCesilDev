package com.systemk.spyder.Dto.Response;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.TempForceReleaseBox;

public class TempForceReleaseBoxStyle {

	private String style;

	private String color;

	private String size;

	private Long amount;

	private String flag;

	private String rfidYn;

	@JsonInclude(Include.NON_NULL)
	private String erpKey;

	private Set<TempForceReleaseBoxTag> tagList = new HashSet<TempForceReleaseBoxTag>();

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

	public Set<TempForceReleaseBoxTag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<TempForceReleaseBoxTag> tagList) {
		this.tagList = tagList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public TempForceReleaseBoxStyle() {

	}

	public TempForceReleaseBoxStyle(TempForceReleaseBox box) {
		this.style = box.getStyle();
		this.color = box.getColor();
		this.size = box.getSize();
		this.amount = box.getAmount();
		this.flag = box.getStyleFlag();
		this.rfidYn = box.getStyleFlag().equals("F") ? "N" : "Y";

		if(!(box.getStyleFlag().equals("T") || box.getStyleFlag().equals("F"))) {
			this.tagList.add(new TempForceReleaseBoxTag(box));
		}
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}
}
