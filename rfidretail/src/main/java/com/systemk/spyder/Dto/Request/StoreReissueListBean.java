package com.systemk.spyder.Dto.Request;

import java.util.List;

public class StoreReissueListBean {

	private Long userSeq;

	private String type;

	private List<ReissueRequestBean> content;

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ReissueRequestBean> getContent() {
		return content;
	}

	public void setContent(List<ReissueRequestBean> content) {
		this.content = content;
	}

}
