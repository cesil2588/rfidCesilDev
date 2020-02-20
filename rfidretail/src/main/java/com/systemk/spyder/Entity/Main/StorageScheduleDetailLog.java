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
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "물류입고예정 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StorageScheduleDetailLog implements Serializable{
	
	private static final long serialVersionUID = -1856220004225585696L;

	// 물류입고예정 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long storageScheduleDetailLogSeq;
	
	// openDB 코드
	//@ApiModelProperty(notes = "WMS 입고 코드(default: R2455)")
	@Column(nullable = false, length = 7)
	@JsonView({View.Public.class})
	private String openDbCode;
	
	// 바코드
	//@ApiModelProperty(notes = "박스 바코드")
	@Column(nullable = false, length = 18)
	@JsonView({View.Public.class})
	private String barcode;
	
	// 라인번호
	//@ApiModelProperty(notes = "WMS 라인 번호")
	@JsonView({View.Public.class})
	private Long lineNum;

	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String style;
	
	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 20)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String color;
	
	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 20)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String size;
	
	// 오더차수
	//@ApiModelProperty(notes = "오더차수")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String orderDegree;
	
	// 수량
	//@ApiModelProperty(notes = "수량")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long amount;
	
	// 스타일 일련번호
	//@ApiModelProperty(notes = "스타일 재고 일련번호(생산, 매장)")
	@JsonView({View.Public.class})
	private Long styleSeq;
	
	// 물류입고예정 RFID 목록
	//@ApiModelProperty(notes = "물류입고예정 RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "storageScheduleDetailLogSeq")
	@OrderBy("rfidTag asc")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Set<StorageScheduleSubDetailLog> storageScheduleSubDetailLog;
	
	// 물류입고예정 일련번호
	//@ApiModelProperty(notes = "물류입고예정 일련번호")
	@JsonView({View.Public.class})
	private Long storageScheduleLogSeq;
	
	//물류 실적 반영일
	//@JsonProperty("completeDate")
	//private Date completeDate;
	
	//반품 매장 이름
	//@JsonProperty("companyName")
	//private String companyName;
	
	
	public String getOpenDbCode() {
		return openDbCode;
	}

	public void setOpenDbCode(String openDbCode) {
		this.openDbCode = openDbCode;
	}

	public Long getLineNum() {
		return lineNum;
	}

	public void setLineNum(Long lineNum) {
		this.lineNum = lineNum;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public Long getStorageScheduleDetailLogSeq() {
		return storageScheduleDetailLogSeq;
	}

	public void setStorageScheduleDetailLogSeq(Long storageScheduleDetailLogSeq) {
		this.storageScheduleDetailLogSeq = storageScheduleDetailLogSeq;
	}

	public Set<StorageScheduleSubDetailLog> getStorageScheduleSubDetailLog() {
		return storageScheduleSubDetailLog;
	}

	public void setStorageScheduleSubDetailLog(Set<StorageScheduleSubDetailLog> storageScheduleSubDetailLog) {
		this.storageScheduleSubDetailLog = storageScheduleSubDetailLog;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getStyleSeq() {
		return styleSeq;
	}

	public void setStyleSeq(Long styleSeq) {
		this.styleSeq = styleSeq;
	}

	public Long getStorageScheduleLogSeq() {
		return storageScheduleLogSeq;
	}

	public void setStorageScheduleLogSeq(Long storageScheduleLogSeq) {
		this.storageScheduleLogSeq = storageScheduleLogSeq;
	}
/*
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	*/	
}
