package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "임시 생산입고 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempProductionStorageTag implements Serializable{

	private static final long serialVersionUID = 3478020526096514265L;
	
	// 임시 생산 입고 태그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempTagSeq;

	// RFID Tag
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
	private String rfidTag;
	
	//@ApiModelProperty(notes = "임시 생산입고 헤더 일련번호")
	private Long tempHeaderSeq;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getTempHeaderSeq() {
		return tempHeaderSeq;
	}

	public void setTempHeaderSeq(Long tempHeaderSeq) {
		this.tempHeaderSeq = tempHeaderSeq;
	}

	public Long getTempTagSeq() {
		return tempTagSeq;
	}

	public void setTempTagSeq(Long tempTagSeq) {
		this.tempTagSeq = tempTagSeq;
	}
}
