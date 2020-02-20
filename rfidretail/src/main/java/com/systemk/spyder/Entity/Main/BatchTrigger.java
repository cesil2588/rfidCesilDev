package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "배치 트리거 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BatchTrigger implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 252950481414317129L;

	// 배치 트리거 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long batchTriggerSeq;
	
	// 배치 트리거 타입
	//@ApiModelProperty(notes = "배치 트리거 타입(1: 발행 업로드 배치, 3: 일괄 생산 태그 입고 배치, 4: 재발행 업로드 배치)")
	@Column(nullable = false, length = 1)
	private String type;
	
	// 확인 여부
	//@ApiModelProperty(notes = "확인 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String checkYn;
	
	// 배치 트리거 stat
	//@ApiModelProperty(notes = "상태")
	@Column(nullable = false, length = 1)
	private String stat;
	
	// 배치 트리거 상태
	//@ApiModelProperty(notes = "배치 트리거 상태(SCHEDULE: 예약, COMPLETE: 완료)")
	@Column(nullable = false, length = 10)
	private String status;
	
	// 비고
	//@ApiModelProperty(notes = "비고")
	@Column(nullable = true, length = 1000)
    private String explanatory;
	
	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;
	
	// 배치 시작 예정일
	//@ApiModelProperty(notes = "배치 시작 예정일")
	private Date scheduleDate;
	
	// 배치 시작일
	//@ApiModelProperty(notes = "배치 작업 시작일")
	private Date startDate;
	
	// 배치 종료일
	//@ApiModelProperty(notes = "배치 작업 종료일")
	private Date endDate;
	
	// 에러메시지
	//@ApiModelProperty(notes = "에러 메시지")
	@Lob
	@Column(nullable = true)
	private String errorMessage;
	
	//	배치 트리거 Detail
	//@ApiModelProperty(notes = "배치 트리거 상세 목록")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "batchTriggerSeq")
    private Set<BatchTriggerDetail> detailSet;
	
	public Long getBatchTriggerSeq() {
		return batchTriggerSeq;
	}

	public void setBatchTriggerSeq(Long batchTriggerSeq) {
		this.batchTriggerSeq = batchTriggerSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Set<BatchTriggerDetail> getDetailSet() {
		return detailSet;
	}

	public void setDetailSet(Set<BatchTriggerDetail> detailSet) {
		this.detailSet = detailSet;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
