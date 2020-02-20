package com.systemk.spyder.Dto.Response;

import java.util.HashSet;
import java.util.Set;

import com.systemk.spyder.Entity.Main.TempForceReleaseBox;

public class TempForceReleaseBoxHeader {

	private String boxBarcode;

	private String flag;

	private Set<TempForceReleaseBoxStyle> styleList = new HashSet<TempForceReleaseBoxStyle>();

	public String getBoxBarcode() {
		return boxBarcode;
	}

	public void setBoxBarcode(String boxBarcode) {
		this.boxBarcode = boxBarcode;
	}

	public Set<TempForceReleaseBoxStyle> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<TempForceReleaseBoxStyle> styleList) {
		this.styleList = styleList;
	}

	public TempForceReleaseBoxHeader() {

	}

	public TempForceReleaseBoxHeader(TempForceReleaseBox box) {
		this.boxBarcode = box.getBoxBarcode();
		this.flag = box.getBoxFlag();
		this.styleList.add(new TempForceReleaseBoxStyle(box));
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
