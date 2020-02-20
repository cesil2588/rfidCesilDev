package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.systemk.spyder.Dto.Response.StoreScheduleTagResult;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxTag;

//@ApiModel(description = "물류출고예정 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReleaseScheduleSubDetailLog implements Serializable{

	private static final long serialVersionUID = -1124055586468557155L;

	// 물류출고예정 RFID 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long releaseScheduleSubDetailLogSeq;

	// RFID 일련번호
	//@ApiModelProperty(notes = "RFID 태그 인코딩 정보")
	@Column(nullable = false, length = 32)
	private String rfidTag;

	// 태그 바코드
	@Column(nullable = true, length = 32)
	private String barcode;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getReleaseScheduleSubDetailLogSeq() {
		return releaseScheduleSubDetailLogSeq;
	}

	public void setReleaseScheduleSubDetailLogSeq(Long releaseScheduleSubDetailLogSeq) {
		this.releaseScheduleSubDetailLogSeq = releaseScheduleSubDetailLogSeq;
	}

	public ReleaseScheduleSubDetailLog() {

	}

	public ReleaseScheduleSubDetailLog(StoreScheduleTagResult tag) {
		this.rfidTag = tag.getRfidTag();
		this.barcode = tag.getBarcode();
	}

	public ReleaseScheduleSubDetailLog(TempForceReleaseBoxTag tag) {
		this.rfidTag = tag.getRfidTag();
		this.barcode = tag.getRfidTag().substring(0, 10).concat(tag.getRfidTag().substring(27, 32));
	}
}
