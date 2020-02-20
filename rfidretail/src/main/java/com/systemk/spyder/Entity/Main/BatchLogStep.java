package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "배치 Job 로그 스텝 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BatchLogStep implements Serializable{

	private static final long serialVersionUID = -7527691746782830286L;

	// 배치 로그 스텝 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long BatchLogStepSeq;
	
	// 배치 Job 스텝 명
	//@ApiModelProperty(notes = "배치 Job 스텝 명")
	@Column(nullable = false, length = 50)
	private String name;
	
	// 배치 Job 스텝 상태
	//@ApiModelProperty(notes = "상태")
	@Column(nullable = false, length = 20)
	private String status;
	
	// 배치 Job 스텝 최종 상태
	//@ApiModelProperty(notes = "최종 상태")
	@Column(nullable = false, length = 20)
	private String exitStatus;
	
	// 읽은 건수
	//@ApiModelProperty(notes = "읽은 건수")
	private int readCount;
	
	// 필터링 건수
	//@ApiModelProperty(notes = "필터링 건수")
	private int filterCount;
	
	// 저장 건수
	//@ApiModelProperty(notes = "저장 건수")
	private int writeCount;
	
	// 읽기 스킵 건수
	//@ApiModelProperty(notes = "읽기 스킵 건수")
	private int readSkipCount;
	
	// 저장 스킵 건수
	//@ApiModelProperty(notes = "저장 스킵 건수")
	private int writeSkipCount;
	
	// 프로세서 스킵 건수
	//@ApiModelProperty(notes = "프로세서 스킵 건수")
	private int processSkipCount;
	
	// 커밋 건수
	//@ApiModelProperty(notes = "커밋 건수")
	private int commitCount;
	
	// 롤백 건수
	//@ApiModelProperty(notes = "롤백 건수")
	private int rollbackCount;
	
	// 배치 Job step 시작일시
	//@ApiModelProperty(notes = "작업 시작일시")
	private Date startTime;
	
	// 배치 Job step 종료일시
	//@ApiModelProperty(notes = "작업 종료일시")
	private Date endTime;
	
	// 에러메시지
	//@ApiModelProperty(notes = "에러 메시지")
	@Lob
	@Column(nullable = true)
	private String errorMessage;

	public Long getBatchLogStepSeq() {
		return BatchLogStepSeq;
	}

	public void setBatchLogStepSeq(Long batchLogStepSeq) {
		BatchLogStepSeq = batchLogStepSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(String exitStatus) {
		this.exitStatus = exitStatus;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(int writeCount) {
		this.writeCount = writeCount;
	}

	public int getReadSkipCount() {
		return readSkipCount;
	}

	public void setReadSkipCount(int readSkipCount) {
		this.readSkipCount = readSkipCount;
	}

	public int getWriteSkipCount() {
		return writeSkipCount;
	}

	public void setWriteSkipCount(int writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}

	public int getProcessSkipCount() {
		return processSkipCount;
	}

	public void setProcessSkipCount(int processSkipCount) {
		this.processSkipCount = processSkipCount;
	}

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public int getRollbackCount() {
		return rollbackCount;
	}

	public void setRollbackCount(int rollbackCount) {
		this.rollbackCount = rollbackCount;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
