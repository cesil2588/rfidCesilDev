package com.systemk.spyder.Dto.Response;

import com.systemk.spyder.Entity.Main.TempForceReleaseBox;

public class TempForceReleaseBoxTag {

	private String rfidTag;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public TempForceReleaseBoxTag() {

	}

	public TempForceReleaseBoxTag(TempForceReleaseBox box) {
		this.rfidTag = box.getRfidTag();
	}
}
