package com.systemk.spyder.Entity.Main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Response.InventoryScheduleTagResult;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "재고조사 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryScheduleTag {

	// 태그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
    private Long inventoryScheduleTagSeq;

	// RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String rfidTag;

	// RFID 바코드
	//@ApiModelProperty(notes = "RFID 태그 바코드")
	@Column(nullable = false, length = 15)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String barcode;

	public Long getInventoryScheduleTagSeq() {
		return inventoryScheduleTagSeq;
	}

	public void setInventoryScheduleTagSeq(Long inventoryScheduleTagSeq) {
		this.inventoryScheduleTagSeq = inventoryScheduleTagSeq;
	}

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

	public InventoryScheduleTag() {

	}

	public InventoryScheduleTag(InventoryScheduleTagResult result) {
		this.rfidTag = result.getRfidTag();
		this.barcode = result.getBarcode();
	}
}
