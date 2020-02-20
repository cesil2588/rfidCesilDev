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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "배치 Job 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BatchLog implements Serializable{

	private static final long serialVersionUID = 770285962168790176L;

	// 배치 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long batchLogSeq;
	
	// 배치 Job 명
	//@ApiModelProperty(notes = "배치 Job 명")
	@Column(nullable = false, length = 50)
	private String jobName;
	
	// 배치 Job 상태
	//@ApiModelProperty(notes = "상태")
	@Column(nullable = false, length = 10)
	private String status;
	
	// 배치 Job 생성일
	//@ApiModelProperty(notes = "생성일")
	private Date createTime;
	
	// 배치 Job 시작일
	//@ApiModelProperty(notes = "작업 시작일")
	private Date startTime;
	
	// 배치 Job 종료일
	//@ApiModelProperty(notes = "작업 종료일")
	private Date endTime;
	
	// 확인여부
	//@ApiModelProperty(notes = "확인 여부(Y, N)")
	@Column(nullable = true, length = 1)
	private String checkYn;
	
	//	배치 Step
	//@ApiModelProperty(notes = "배치 Job Step별 로그 목록")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "batchLogSeq")
    private Set<BatchLogStep> BatchLogStep;

	public long getBatchLogSeq() {
		return batchLogSeq;
	}

	public void setBatchLogSeq(long batchLogSeq) {
		this.batchLogSeq = batchLogSeq;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Set<BatchLogStep> getBatchLogStep() {
		return BatchLogStep;
	}

	public void setBatchLogStep(Set<BatchLogStep> batchLogStep) {
		BatchLogStep = batchLogStep;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}
}
