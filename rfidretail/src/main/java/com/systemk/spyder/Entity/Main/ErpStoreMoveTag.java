package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpStoreMoveTag implements Serializable{

	/**
	 * 매장 이동지시 작업 엔터티
	 */
	private static final long serialVersionUID = 4491869944261896423L;

	//이동지시 작업 테이블 sequence
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long moveTagSeq;
	
	//rfid_tag(rfid_tag)
	//@ApiModelProperty(notes="태그 값")
	@Column(nullable = true, length = 32)
	private String rfidTag;
	
	//매장 이동지시 상세 sequence
	//@ApiModelProperty(notes="매장 이동지시 상세 sequence")
	@Column(nullable = true)
	private Long moveDetailSeq;
	
	//작업 박스 번호
	//@ApiModelProperty(notes="작업 박스 번호")
	@Column(nullable = true)
	private Long workBoxNum;
	
	//확정여부
	//@ApiModelProperty(notes="확정 여부")
	@Column(nullable = true)
	private String completeYn;
	
	//미결명세번호
	//@ApiModelProperty(notes="미결명세번호")
	@Column(nullable = true)
	private Long erpReturnInvoiceNum;
	
	//확정일자
	//@ApiModelProperty(notes="확정일자")
	@Column(nullable = true)
	private Date completeDate;
	
	//작업자 seq
	//@ApiModelProperty(notes="작업자 sequence")
	@Column(nullable = true)
	private Long updUserSeq;

	
	public Long getMoveTagSeq() {
		return moveTagSeq;
	}

	public void setMoveTagSeq(Long moveTagSeq) {
		this.moveTagSeq = moveTagSeq;
	}

	public Long getMoveDetailSeq() {
		return moveDetailSeq;
	}

	public void setMoveDetailSeq(Long moveDetailSeq) {
		this.moveDetailSeq = moveDetailSeq;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Long getWorkBoxNum() {
		return workBoxNum;
	}

	public void setWorkBoxNum(Long workBoxNum) {
		this.workBoxNum = workBoxNum;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Long getErpReturnInvoiceNum() {
		return erpReturnInvoiceNum;
	}

	public void setErpReturnInvoiceNum(Long erpReturnInvoiceNum) {
		this.erpReturnInvoiceNum = erpReturnInvoiceNum;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Long getUpdUserSeq() {
		return updUserSeq;
	}

	public void setUpdUserSeq(Long updUserSeq) {
		this.updUserSeq = updUserSeq;
	}
	
	

}
