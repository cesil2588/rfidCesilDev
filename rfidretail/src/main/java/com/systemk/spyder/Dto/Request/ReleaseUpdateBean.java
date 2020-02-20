package com.systemk.spyder.Dto.Request;

public class ReleaseUpdateBean {

	private Long userSeq;
	
	private String type;
	
	private String targetBoxBarcode;
	
	private BoxTagListBean BoxInfo;

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public String getTargetBoxBarcode() {
		return targetBoxBarcode;
	}

	public void setTargetBoxBarcode(String targetBoxBarcode) {
		this.targetBoxBarcode = targetBoxBarcode;
	}

	public BoxTagListBean getBoxInfo() {
		return BoxInfo;
	}

	public void setBoxInfo(BoxTagListBean boxInfo) {
		BoxInfo = boxInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
