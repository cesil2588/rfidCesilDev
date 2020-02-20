package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "매장간 이동 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreMoveSubDetailLog implements Serializable{
	
	private static final long serialVersionUID = -1124055586468557155L;

	// 매장간 이동 서브 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
	private Long storeMoveSubDetailLogSeq;
	
	// RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String rfidTag;
	
	// 상태(1: 요청, 2: 입고완료, 3: 신규입고)
	//@ApiModelProperty(notes = "상태(1: 요청, 2: 입고완료, 3: 신규입고)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String stat;
	
	// RFID 순번(ERP 연동)
	//@ApiModelProperty(notes = "RFID 순번(ERP)")
	@JsonView({View.Public.class})
	private Long rfidLineNum;
	
	// RFID 생성일자(ERP 연동)
	//@ApiModelProperty(notes = "RFID 생성일자(ERP)")
	@Column(nullable = true, length = 8)
	@JsonView({View.Public.class})
	private String rfidCreateDate;
	
	//@ApiModelProperty(notes = "매장간 이동 스타일 일련번호")
	@JsonView({View.Public.class})
	private Long storeMoveDetailLogSeq;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getStoreMoveSubDetailLogSeq() {
		return storeMoveSubDetailLogSeq;
	}

	public void setStoreMoveSubDetailLogSeq(Long storeMoveSubDetailLogSeq) {
		this.storeMoveSubDetailLogSeq = storeMoveSubDetailLogSeq;
	}

	public Long getStoreMoveDetailLogSeq() {
		return storeMoveDetailLogSeq;
	}

	public void setStoreMoveDetailLogSeq(Long storeMoveDetailLogSeq) {
		this.storeMoveDetailLogSeq = storeMoveDetailLogSeq;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Long getRfidLineNum() {
		return rfidLineNum;
	}

	public void setRfidLineNum(Long rfidLineNum) {
		this.rfidLineNum = rfidLineNum;
	}

	public String getRfidCreateDate() {
		return rfidCreateDate;
	}

	public void setRfidCreateDate(String rfidCreateDate) {
		this.rfidCreateDate = rfidCreateDate;
	}
}
