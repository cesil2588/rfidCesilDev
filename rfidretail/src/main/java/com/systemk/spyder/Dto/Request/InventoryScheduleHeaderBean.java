package com.systemk.spyder.Dto.Request;

import java.io.Serializable;
import java.util.List;

import com.systemk.spyder.Dto.Response.InventoryScheduleResult;

/**
 * 재고조사 실적
 * @author escho
 *
 */
public class InventoryScheduleHeaderBean implements Serializable{

	private static final long serialVersionUID = -2993155812257005606L;

	// 사용자 일련번호
	private Long userSeq;

	// 타입
	private String type;

	private List<InventoryScheduleResult> content;

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

	public List<InventoryScheduleResult> getContent() {
		return content;
	}

	public void setContent(List<InventoryScheduleResult> content) {
		this.content = content;
	}

}
