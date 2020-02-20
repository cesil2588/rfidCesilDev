 package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "임시 물류출고 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempDistributionReleaseStyle implements Serializable{

	private static final long serialVersionUID = 976218544034499580L;

	// 임시 물류출고 스타일 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempStyleSeq;

	// 레퍼런스 번호
	////@ApiModelProperty(notes = "ReferenceNo")
	@Column(nullable = true, length = 30)
	private String referenceNo;

	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	public String style;

	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
	public String color;

	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
	public String size;

	// erp 키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = true, length = 10)
	public String erpKey;

	// RFID 부착 여부
	//@ApiModelProperty(notes = "RFID 부착 여부(Y, N)")
	@Column(nullable = true, length = 1)
	public String rfidYn;

	// 출고 수량
	//@ApiModelProperty(notes = "출고 수량")
	public Long releaseAmount;

	// 태그 리스트
	//@ApiModelProperty(notes = "임시 물류출고 RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempStyleSeq")
	private Set<TempDistributionReleaseTag> tagList;

	// 임시 ERP 물류출고예정정보 키 연결
	@Transient
    private Long erpStoreScheduleSeq;

	public Long getTempStyleSeq() {
		return tempStyleSeq;
	}

	public void setTempStyleSeq(Long tempStyleSeq) {
		this.tempStyleSeq = tempStyleSeq;
	}

	public Set<TempDistributionReleaseTag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<TempDistributionReleaseTag> tagList) {
		this.tagList = tagList;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Long getErpStoreScheduleSeq() {
		return erpStoreScheduleSeq;
	}

	public void setErpStoreScheduleSeq(Long erpStoreScheduleSeq) {
		this.erpStoreScheduleSeq = erpStoreScheduleSeq;
	}

}
