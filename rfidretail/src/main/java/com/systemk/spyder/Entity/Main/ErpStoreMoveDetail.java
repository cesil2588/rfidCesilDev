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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" ,"moveNote", "transYn", "transDate"})
public class ErpStoreMoveDetail implements Serializable{

	/**
	 * 매장간 이동지시 상세 항목 Entity
	 */
	private static final long serialVersionUID = 6436058461668423299L;
	
	//이동지시상세 테이블 sequence
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long moveDetailSeq;
	
	//이동지시일자(move_order_seq)
	//@ApiModelProperty(notes="이동지시 테이블 sequence")
	@Column(nullable = false, length = 8)
	private Long moveSeq;
	
	//이동지시 style(move_style)
	//@ApiModelProperty(notes="이동지시 style")
	@Column(nullable = true, length = 8)
	private String moveStyle;
	
	//이동지시 color(move_color)
	//@ApiModelProperty(notes="이동지시 color")
	@Column(nullable = true, length = 10)
	private String moveColor;
	
	//이동지시 size(move_size)
	//@ApiModelProperty(notes="이동지시 size")
	@Column(nullable = true, length = 8)
	private String moveSize;
	
	//이동지시 지시수량(order_amount)
	//@ApiModelProperty(notes="이동지시 지시수량")
	@Column(nullable = true)
	private Long orderAmount;
	
	//이동지시 이행수량(execute_amount)
	//@ApiModelProperty(notes="이동지시 이행수량")
	@Column(nullable = true)
	private Long executeAmount;
	
	//이동지시 확정수량(confirm_amount)
	//@ApiModelProperty(notes="이동지시 확정수량")
	@Column(nullable = true)
	private Long confirmAmount;
	
	//rfid여부
	//@ApiModelProperty(notes="rfid 여부")
	@Column(nullable = true)
	private String rfidYn;
	
	//신규여부
	//@ApiModelProperty(notes="신규여부")
	@Column(nullable = true)
	private String anotherYn;
	
	@Transient
	//비고(move_note)
	//@ApiModelProperty(notes="비고")
	@Column(nullable = true)
	private String moveNote;
	
	//전송여부(trans_yn)
	//@ApiModelProperty(notes="전송여부")
	@Column(nullable = true)
	private String transYn;
	
	//전송일자(trans_date)
	//@ApiModelProperty(notes="전송일자")
	@Column(nullable = true)
	private Date transDate;
	
	//등록자 sequence(reg_user_seq)
	private Long regUserSeq;

	
	public Long getMoveDetailSeq() {
		return moveDetailSeq;
	}

	public void setMoveDetailSeq(Long moveDetailSeq) {
		this.moveDetailSeq = moveDetailSeq;
	}

	public Long getMoveSeq() {
		return moveSeq;
	}

	public void setMoveSeq(Long moveSeq) {
		this.moveSeq = moveSeq;
	}

	public String getMoveStyle() {
		return moveStyle;
	}

	public void setMoveStyle(String moveStyle) {
		this.moveStyle = moveStyle;
	}

	public String getMoveColor() {
		return moveColor;
	}

	public void setMoveColor(String moveColor) {
		this.moveColor = moveColor;
	}

	public String getMoveSize() {
		return moveSize;
	}

	public void setMoveSize(String moveSize) {
		this.moveSize = moveSize;
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

	public String getMoveNote() {
		return moveNote;
	}

	public void setMoveNote(String moveNote) {
		this.moveNote = moveNote;
	}

	public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public String getAnotherYn() {
		return anotherYn;
	}

	public void setAnotherYn(String anotherYn) {
		this.anotherYn = anotherYn;
	}

	public Long getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Long regUserSeq) {
		this.regUserSeq = regUserSeq;
	}
	

}
