package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "임시 생산출고 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempProductionReleaseStyle implements Serializable{
	
	private static final long serialVersionUID = 976218544034499580L;

	// 임시 생산 출고 스타일 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempStyleSeq;
	
	// 생산 재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	private Long productionStorageSeq;
	
	// 태그 리스트
	//@ApiModelProperty(notes = "임시 생산출고 RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempStyleSeq")
	private Set<TempProductionReleaseTag> tagList;
	
	// 임시 생산 출고 헤더 일련번호
	//@ApiModelProperty(notes = "임시 생산출고 헤더 일련번호")
	private Long tempHeaderSeq;
	
	// 임시 생산 출고 박스 일련번호
	//@ApiModelProperty(notes = "임시 생산출고 박스 일련번호")
	private Long tempBoxSeq;
	
	// 임시 스타일 저장
	//@ApiModelProperty(notes = "임시 생산출고 스타일")
	@Transient
	private String style;
	
	// 임시 컬러 저장
	//@ApiModelProperty(notes = "임시 생산출고 컬러")
	@Transient
	private String color;
	
	// 임시 사이즈 저장
	//@ApiModelProperty(notes = "임시 생산출고 사이즈")
	@Transient
	private String size;
	
	// 임시 erp키 저장
	//@ApiModelProperty(notes = "임시 생산출고 ERP 키")
	@Transient
	private String erpKey;
	
	// 임시 startRfidSeq 저장
	//@ApiModelProperty(notes = "임시 생산출고 시작 RFID 태그 시리얼")
	@Transient
	private String startRfidSeq;
	
	// 임시 endRfidSeq 저장
	//@ApiModelProperty(notes = "임시 생산출고 종료 RFID 태그 시리얼")
	@Transient
	private String endRfidSeq;

	public Long getProductionStorageSeq() {
		return productionStorageSeq;
	}

	public void setProductionStorageSeq(Long productionStorageSeq) {
		this.productionStorageSeq = productionStorageSeq;
	}

	public Long getTempStyleSeq() {
		return tempStyleSeq;
	}

	public void setTempStyleSeq(Long tempStyleSeq) {
		this.tempStyleSeq = tempStyleSeq;
	}

	public Set<TempProductionReleaseTag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<TempProductionReleaseTag> tagList) {
		this.tagList = tagList;
	}

	public Long getTempHeaderSeq() {
		return tempHeaderSeq;
	}

	public void setTempHeaderSeq(Long tempHeaderSeq) {
		this.tempHeaderSeq = tempHeaderSeq;
	}

	public Long getTempBoxSeq() {
		return tempBoxSeq;
	}

	public void setTempBoxSeq(Long tempBoxSeq) {
		this.tempBoxSeq = tempBoxSeq;
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

	public String getStartRfidSeq() {
		return startRfidSeq;
	}

	public void setStartRfidSeq(String startRfidSeq) {
		this.startRfidSeq = startRfidSeq;
	}

	public String getEndRfidSeq() {
		return endRfidSeq;
	}

	public void setEndRfidSeq(String endRfidSeq) {
		this.endRfidSeq = endRfidSeq;
	}
}
