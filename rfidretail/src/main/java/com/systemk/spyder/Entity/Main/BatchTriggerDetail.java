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

//@ApiModel(description = "배치 트리거 상세 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BatchTriggerDetail implements Serializable{

	private static final long serialVersionUID = -546080289777372411L;

	// 배치 트리거 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long batchTriggerDetailSeq;
	
	// 생성일
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = false, length = 8)
	private String createDate;
	
	// 파일명
	//@ApiModelProperty(notes = "비고(파일명으로 사용)")
	@Column(nullable = false, length = 200)
	private String explanatory;
	
	// 결과 메시지
	//@ApiModelProperty(notes = "결과 메시지")
	@Lob
	@Column(nullable = true)
	private String resultMessage;
	
	// 총 수량
	//@ApiModelProperty(notes = "총 수량")
	private Long totalAmount;
	
	// 성공 수량
	//@ApiModelProperty(notes = "성공 수량")
	private Long successAmount;
	
	// 실패 수량
	//@ApiModelProperty(notes = "실패 수량")
	private Long failAmount;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	public Long getBatchTriggerDetailSeq() {
		return batchTriggerDetailSeq;
	}

	public void setBatchTriggerDetailSeq(Long batchTriggerDetailSeq) {
		this.batchTriggerDetailSeq = batchTriggerDetailSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(Long successAmount) {
		this.successAmount = successAmount;
	}

	public Long getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(Long failAmount) {
		this.failAmount = failAmount;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
