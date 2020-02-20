package com.systemk.spyder.Dto.Request;

import java.io.Serializable;

import com.systemk.spyder.Dto.Response.StoreScheduleResult;

/**
 * 매장입고예정정보 실적
 * @author escho
 *
 */
public class StoreScheduleCompleteBean implements Serializable{

	private static final long serialVersionUID = -2993155812257005606L;

	// 사용자 일련번호
	private Long userSeq;

	// 타입
	private String type;

	private StoreScheduleResult schedule;

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

	public StoreScheduleResult getSchedule() {
		return schedule;
	}

	public void setSchedule(StoreScheduleResult schedule) {
		this.schedule = schedule;
	}

}
