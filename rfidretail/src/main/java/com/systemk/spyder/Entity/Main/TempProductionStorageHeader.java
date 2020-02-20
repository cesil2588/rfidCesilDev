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

//@ApiModel(description = "임시 생산입고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempProductionStorageHeader implements Serializable{
	
	private static final long serialVersionUID = -8354992743026956491L;

	// 임시 생산 입고 헤더 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempHeaderSeq;
	
	// 작업자 일련번호
	//@ApiModelProperty(notes = "작업자 일련번호")
	private Long userSeq;
	
	// 작업 타입
	//@ApiModelProperty(notes = "작업타입(1:web, 2:pda, 3:컨베이어)")
	@Column(nullable = false, length = 1)
	private String type;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 상태
	//@ApiModelProperty(notes = "상태(1: 미완료, 2: 작업완료)")
	@Column(nullable = false, length = 1)
	private String stat;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// RFID 서버 request Yn
	//@ApiModelProperty(notes = "RFID 서버 요청 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String requestYn;
		
	// RFID 서버 request 일
	//@ApiModelProperty(notes = "RFID 서버 요청일")
	private Date requestDate;
	
	// 배치 여부 Yn
	//@ApiModelProperty(notes = "배치 작업 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String batchYn;
	
	// 배치 일시
	//@ApiModelProperty(notes = "배치 작업일")
	private Date batchDate;
		
	// 완료 여부 Yn
	//@ApiModelProperty(notes = "작업 완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String completeYn;
			
	// 완료일
	//@ApiModelProperty(notes = "작업 완료일")
	private Date completeDate;
	
	// 스타일 리스트
	//@ApiModelProperty(notes = "임시 생산입고 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempHeaderSeq")
	private Set<TempProductionStorageStyle> styleList;

	public Long getTempHeaderSeq() {
		return tempHeaderSeq;
	}

	public void setTempHeaderSeq(Long tempHeaderSeq) {
		this.tempHeaderSeq = tempHeaderSeq;
	}

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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Set<TempProductionStorageStyle> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<TempProductionStorageStyle> styleList) {
		this.styleList = styleList;
	}

	public String getRequestYn() {
		return requestYn;
	}

	public void setRequestYn(String requestYn) {
		this.requestYn = requestYn;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	public String getBatchYn() {
		return batchYn;
	}

	public void setBatchYn(String batchYn) {
		this.batchYn = batchYn;
	}
}
