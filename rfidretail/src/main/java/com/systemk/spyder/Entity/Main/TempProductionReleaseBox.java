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

//@ApiModel(description = "임시 생산출고 박스 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempProductionReleaseBox implements Serializable{

	private static final long serialVersionUID = 3289989834108714365L;

	// 임시 생산 출고 박스 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempBoxSeq;
	
	// 박스 바코드
	//@ApiModelProperty(notes = "박스 바코드")
	@Column(nullable = false, length = 20)
	private String barcode;
	
	// 스타일 리스트
	//@ApiModelProperty(notes = "임시 생산 출고 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempBoxSeq")
	private Set<TempProductionReleaseStyle> styleList;
	
	//@ApiModelProperty(notes = "임시 생산 출고 헤더 일련번호")
	private Long tempHeaderSeq;
	
	// 임시 박스정보 저장
	//@ApiModelProperty(notes = "임시 생산 출고 박스 정보")
	@Transient
	private BoxInfo boxInfo;

	public Set<TempProductionReleaseStyle> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<TempProductionReleaseStyle> styleList) {
		this.styleList = styleList;
	}

	public Long getTempHeaderSeq() {
		return tempHeaderSeq;
	}

	public void setTempHeaderSeq(Long tempHeaderSeq) {
		this.tempHeaderSeq = tempHeaderSeq;
	}

	public BoxInfo getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}

	public Long getTempBoxSeq() {
		return tempBoxSeq;
	}

	public void setTempBoxSeq(Long tempBoxSeq) {
		this.tempBoxSeq = tempBoxSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}
