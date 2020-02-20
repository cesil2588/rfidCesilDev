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

//@ApiModel(description = "물류입고예정 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StorageScheduleSubDetailLog implements Serializable{
	
	private static final long serialVersionUID = -1124055586468557155L;

	// 물류입고예정 RFID 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
	private Long storageScheduleSubDetailLogSeq;
	
	// RFID 태그 일련번호
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String rfidTag;
	
	//@ApiModelProperty(notes = "물류입고예정 스타일 일련번호")
	@JsonView({View.Public.class})
	private Long storageScheduleDetailLogSeq;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getStorageScheduleSubDetailLogSeq() {
		return storageScheduleSubDetailLogSeq;
	}

	public void setStorageScheduleSubDetailLogSeq(Long storageScheduleSubDetailLogSeq) {
		this.storageScheduleSubDetailLogSeq = storageScheduleSubDetailLogSeq;
	}

	public Long getStorageScheduleDetailLogSeq() {
		return storageScheduleDetailLogSeq;
	}

	public void setStorageScheduleDetailLogSeq(Long storageScheduleDetailLogSeq) {
		this.storageScheduleDetailLogSeq = storageScheduleDetailLogSeq;
	}
	
	
}
