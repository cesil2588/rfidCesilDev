package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "moveType", "sku"})
public class ErpStoreMove implements Serializable{

	/**
	 * 매장간 이동지시 Entity
	 */
	private static final long serialVersionUID = 2234371857420604336L;
	
	//이동지시 테이블 sequence
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long moveSeq;
	
	//이동지시일자(order_reg_date)
	//@ApiModelProperty(notes="이동지시일자")
	@Column(nullable = false, length = 8)
	private String orderRegDate;
	
	//이동지시 sequence
	//@ApiModelProperty(notes="이동지시 sequence")
	@Column(nullable = false)
	private Long orderSeq;
	
	//이동지시 serial
	//@ApiModelProperty(notes="이동지시 serial")
	@Column(nullable = false)
	private Long orderSerial;
	
	//from 매장 코드
	//@ApiModelProperty(notes="from 매장코드")
	@Column(nullable = true, length = 10)
	private String fromCustomerCode;
	
	//from 매장 코너코드
	//@ApiModelProperty(notes="from 코너코드")
	@Column(nullable = true, length = 1)
	private String fromCornerCode;
	
	//to 매장 코드
	//@ApiModelProperty(notes="to 매장코드")
	@Column(nullable = true, length = 10)
	private String toCustomerCode;
	
	//to 매장 코너코드
	//@ApiModelProperty(notes="to 코너코드")
	@Column(nullable = true, length = 1)
	private String toCornerCode;
	
	//보내기매장 처리여부
	//@ApiModelProperty(notes="보내기매장 처리여부")
	@Column(nullable = true, length = 1)
	private String fromCompleteYn;
	
	//보내기매장 처리일자
	//@ApiModelProperty(notes="보내기매장 처리일자")
	@Column(nullable = true)
	private Date fromCompleteDate;
	
	//받기매장 처리여부
	//@ApiModelProperty(notes="받기매장 처리여부")
	@Column(nullable = true, length = 1)
	private String toCompleteYn;
	
	//받기매장 처리일자
	//@ApiModelProperty(notes="받기매장 처리일자")
	@Column(nullable = true)
	private Date toCompleteDate;
	
	//지시 수량
	//@ApiModelPropdery(notes="지시수량")
	@Column(nullable = true)
	private Long orderAmount;
	
	//이행 수량
	//@ApiModelPropdery(notes="이행수량")
	@Column(nullable = true)
	private Long executeAmount;
		
	//확정 수량
	//@ApiModelPropdery(notes="확정수량")
	@Column(nullable = true)
	private Long confirmAmount;
	
	//매장 이동의 타입(1:이동지시, 2:매장등록, 3:매장등록 완료전)
	//@ApiModelPropdery(notes="매장 이동 타입")
	@Column(nullable = false)
	private String moveType;
	
	//detail에서 사용하기위한 변수
	@Transient
	private String sku;

	
	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public Long getMoveSeq() {
		return moveSeq;
	}

	public void setMoveSeq(Long moveSeq) {
		this.moveSeq = moveSeq;
	}

	public String getOrderRegDate() {
		return orderRegDate;
	}

	public void setOrderRegDate(String orderRegDate) {
		this.orderRegDate = orderRegDate;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Long getOrderSerial() {
		return orderSerial;
	}

	public void setOrderSerial(Long orderSerial) {
		this.orderSerial = orderSerial;
	}

	public String getFromCustomerCode() {
		return fromCustomerCode;
	}

	public void setFromCustomerCode(String fromCustomerCode) {
		this.fromCustomerCode = fromCustomerCode;
	}

	public String getFromCornerCode() {
		return fromCornerCode;
	}

	public void setFromCornerCode(String fromCornerCode) {
		this.fromCornerCode = fromCornerCode;
	}

	public String getToCustomerCode() {
		return toCustomerCode;
	}

	public void setToCustomerCode(String toCustomerCode) {
		this.toCustomerCode = toCustomerCode;
	}

	public String getToCornerCode() {
		return toCornerCode;
	}

	public void setToCornerCode(String toCornerCode) {
		this.toCornerCode = toCornerCode;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getExecuteAmount() {
		return executeAmount;
	}

	public void setExecuteAmount(Long executeAmount) {
		this.executeAmount = executeAmount;
	}

	public Long getConfirmAmount() {
		return confirmAmount;
	}

	public void setConfirmAmount(Long confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	public String getFromCompleteYn() {
		return fromCompleteYn;
	}

	public void setFromCompleteYn(String fromCompleteYn) {
		this.fromCompleteYn = fromCompleteYn;
	}

	public Date getFromCompleteDate() {
		return fromCompleteDate;
	}

	public void setFromCompleteDate(Date fromCompleteDate) {
		this.fromCompleteDate = fromCompleteDate;
	}

	public String getToCompleteYn() {
		return toCompleteYn;
	}

	public void setToCompleteYn(String toCompleteYn) {
		this.toCompleteYn = toCompleteYn;
	}

	public Date getToCompleteDate() {
		return toCompleteDate;
	}

	public void setToCompleteDate(Date toCompleteDate) {
		this.toCompleteDate = toCompleteDate;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	

}
