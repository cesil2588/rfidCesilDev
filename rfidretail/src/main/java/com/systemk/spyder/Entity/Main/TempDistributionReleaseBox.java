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

////@ApiModel(description = "임시 물류출고 박스 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempDistributionReleaseBox implements Serializable{

	private static final long serialVersionUID = 3289989834108714365L;

	// 임시 물류출고박스 일련번호
	////@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempBoxSeq;
	
	// 박스 일련번호
	////@ApiModelProperty(notes = "박스 바코드")
	@Column(nullable = false, length = 20)
	private String barcode;
		
	// 작업자 일련번호
	////@ApiModelProperty(notes = "작업자 일련번호")
	private Long userSeq;
		
	// 작업 타입
	////@ApiModelProperty(notes = "작업타입(1:web, 2:pda, 3:컨베이어)")
	@Column(nullable = false, length = 1)
	private String type;
		
	// 완료 여부
	////@ApiModelProperty(notes = "작업 완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String completeYn;
	
	// 완료 날짜
	////@ApiModelProperty(notes = "작업 완료일")
	private Date completeDate;
	
	// WMS 검증 여부
	////@ApiModelProperty(notes = "WMS 검증 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String wmsValidYn;
			
	// WMS 검증 날짜
	////@ApiModelProperty(notes = "WMS 검증일")
	private Date wmsValidDate;
	
	// WMS 완료 여부
	////@ApiModelProperty(notes = "WMS 완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String wmsCompleteYn;
		
	// WMS 완료 날짜
	////@ApiModelProperty(notes = "WMS 완료일")
	private Date wmsCompleteDate;
	
	// 배치 여부
	////@ApiModelProperty(notes = "배치 작업 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String batchYn;
	
	// 배치 일시
	////@ApiModelProperty(notes = "배치 작업일")
	private Date batchDate;
	
	// 등록일
	////@ApiModelProperty(notes = "등록일")
	private Date regDate;
		
	// 수정일
	////@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 매장/온라인 구분
	////@ApiModelProperty(notes = "매장 온라인 구분(C: 매장, C1: 온라인)")
	@Column(nullable = false, length = 2)
	private String orderType;
	
	// 스타일 리스트
	////@ApiModelProperty(notes = "임시 물류출고 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempBoxSeq")
	private Set<TempDistributionReleaseStyle> styleList;
	
	public Set<TempDistributionReleaseStyle> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<TempDistributionReleaseStyle> styleList) {
		this.styleList = styleList;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getTempBoxSeq() {
		return tempBoxSeq;
	}

	public void setTempBoxSeq(Long tempBoxSeq) {
		this.tempBoxSeq = tempBoxSeq;
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

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
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

	public String getWmsCompleteYn() {
		return wmsCompleteYn;
	}

	public void setWmsCompleteYn(String wmsCompleteYn) {
		this.wmsCompleteYn = wmsCompleteYn;
	}

	public Date getWmsCompleteDate() {
		return wmsCompleteDate;
	}

	public void setWmsCompleteDate(Date wmsCompleteDate) {
		this.wmsCompleteDate = wmsCompleteDate;
	}

	public String getBatchYn() {
		return batchYn;
	}

	public void setBatchYn(String batchYn) {
		this.batchYn = batchYn;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getWmsValidYn() {
		return wmsValidYn;
	}

	public void setWmsValidYn(String wmsValidYn) {
		this.wmsValidYn = wmsValidYn;
	}

	public Date getWmsValidDate() {
		return wmsValidDate;
	}

	public void setWmsValidDate(Date wmsValidDate) {
		this.wmsValidDate = wmsValidDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
