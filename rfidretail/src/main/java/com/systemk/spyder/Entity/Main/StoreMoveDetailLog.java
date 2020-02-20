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

//@ApiModel(description = "매장간 이동 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreMoveDetailLog implements Serializable{
	
	private static final long serialVersionUID = -1856220004225585696L;

	// 매장간 이동 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long storeMoveDetailLogSeq;
	
	// 박스 바코드
	//@ApiModelProperty(notes = "박스 바코드")
	@Column(nullable = false, length = 18)
	@JsonView({View.Public.class})
	private String barcode;
	
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
	
	// 요청수량
	//@ApiModelProperty(notes = "요청 수량")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long amount;
	
	// 완료 수량
	//@ApiModelProperty(notes = "완료 수량")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long completeAmount;
	
	// 보낸 매장 storage 일련번호
	//@ApiModelProperty(notes = "보낸 매장 재고 일련번호")
	@JsonView({View.Public.class})
	private Long styleSeq;
	
	// 받는 매장 storage 일련번호
	//@ApiModelProperty(notes = "받는 매장 재고 일련번호")
	@JsonView({View.Public.class})
	private Long completeStyleSeq;
	
	// 매장간 이동 서브 상세
	//@ApiModelProperty(notes = "매장간 이동 RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "storeMoveDetailLogSeq")
	@OrderBy("rfidTag asc")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Set<StoreMoveSubDetailLog> storeMoveSubDetailLog;
	
	//@ApiModelProperty(notes = "매장간 이동 일련번호")
	@JsonView({View.Public.class})
	private Long storeMoveLogSeq;

	public Long getStoreMoveDetailLogSeq() {
		return storeMoveDetailLogSeq;
	}

	public void setStoreMoveDetailLogSeq(Long storeMoveDetailLogSeq) {
		this.storeMoveDetailLogSeq = storeMoveDetailLogSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getStyleSeq() {
		return styleSeq;
	}

	public void setStyleSeq(Long styleSeq) {
		this.styleSeq = styleSeq;
	}

	public Set<StoreMoveSubDetailLog> getStoreMoveSubDetailLog() {
		return storeMoveSubDetailLog;
	}

	public void setStoreMoveSubDetailLog(Set<StoreMoveSubDetailLog> storeMoveSubDetailLog) {
		this.storeMoveSubDetailLog = storeMoveSubDetailLog;
	}

	public Long getStoreMoveLogSeq() {
		return storeMoveLogSeq;
	}

	public void setStoreMoveLogSeq(Long storeMoveLogSeq) {
		this.storeMoveLogSeq = storeMoveLogSeq;
	}

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public Long getCompleteStyleSeq() {
		return completeStyleSeq;
	}

	public void setCompleteStyleSeq(Long completeStyleSeq) {
		this.completeStyleSeq = completeStyleSeq;
	}
}
