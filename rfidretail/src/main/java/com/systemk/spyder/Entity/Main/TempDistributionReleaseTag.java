package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "임시 물류출고 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempDistributionReleaseTag implements Serializable{

	private static final long serialVersionUID = 1608447393672108988L;

	// 임시믈류출고 태그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
	private Long tempDistributionReleaseTagSeq;

	// RFID Tag
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
	private String rfidTag;

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getTempDistributionReleaseTagSeq() {
		return tempDistributionReleaseTagSeq;
	}

	public void setTempDistributionReleaseTagSeq(Long tempDistributionReleaseTagSeq) {
		this.tempDistributionReleaseTagSeq = tempDistributionReleaseTagSeq;
	}

	public TempDistributionReleaseTag() {

	}

	public TempDistributionReleaseTag(RfidTagBean rfidTag) {
		this.rfidTag = rfidTag.getRfidTag();
	}
}
