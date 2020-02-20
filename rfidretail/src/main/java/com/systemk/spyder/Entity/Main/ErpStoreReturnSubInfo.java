package com.systemk.spyder.Entity.Main;

import java.io.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.systemk.spyder.Entity.Main.View.*;

//@ApiModel(description = "매장 반품 지시 스타일 정보 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "erpSd14Key"})
public class ErpStoreReturnSubInfo implements Serializable{

	private static final long serialVersionUID = 8747797875075532681L;
	
	//@ApiModelProperty(notes = "반품지시 서브 정보 일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long returnInfoSubSeq;
		
	//지시 정보 테이블 시퀀스(return_info_seq)
	//@ApiModelProperty(notes = "지시정보 테이블 시퀀스")
	@Column(nullable = false)
	private Long returnInfoSeq;
	
	//반품 스타일(return_style)
	//@ApiModelProperty(notes = "반품 스타일")
	@Column(nullable = true, length = 20)
	private String returnStyle;
	
	//반품 color(return_color)
	//@ApiModelProperty(notes = "반품 컬러")
	@Column(nullable = true, length = 20)
	private String returnColor;
	
	//반품 사이즈(return_size)
	//@ApiModelProperty(notes = "반품 사이즈")
	@Column(nullable = true, length = 20)
	private String returnSize;
	
	//지시 수량(order_amount)
	//@ApiModelProperty(notes = "지시 수량")
	@Column(nullable = true, length = 8)
	private Long orderAmount;
	
	//이행 수량(execute_amount)
	//@ApiModelProperty(notes = "이행 수량")
	@Column(nullable = true, length = 8)
	private Long executeAmount;
	
	//확정 수량(confirm_amount)
	//@ApiModelProperty(notes = "확정 수량")
	@Column(nullable = true, length = 8)
	private Long confirmAmount;
	
	//RFID 여부(rfid_yn)
	//@ApiModelProperty(notes = "rfid 여부")
	@Column(nullable = true, length = 1)
	private String rfidYn;
	
	//신규 여부
	//@ApiModelProperty(note="신규 여부")
	@Column(nullable = true, length =1)
	private String anotherYn;
	
	//erp_sd14_key
	//@ApiModelProperty(note="rfid_sd14_if 테이블의 키 조합")
	@Column(nullable = true, length = 100)
	private String erpSd14Key;

	
	// ErpStoreReturnSubInfo 모델 복사
    public void CopyData(ErpStoreReturnSubInfo param)
    {
        this.returnInfoSubSeq = param.returnInfoSubSeq;
        this.returnInfoSeq = param.returnInfoSeq;
        this.returnStyle = param.returnStyle;
        this.returnColor = param.returnColor;
        this.returnSize = param.returnSize;
        this.orderAmount = param.orderAmount;
        this.executeAmount = param.executeAmount;
        this.confirmAmount = param.confirmAmount;
        this.rfidYn = param.rfidYn;
    }



	public Long getReturnInfoSubSeq() {
		return returnInfoSubSeq;
	}



	public void setReturnInfoSubSeq(Long returnInfoSubSeq) {
		this.returnInfoSubSeq = returnInfoSubSeq;
	}



	public Long getReturnInfoSeq() {
		return returnInfoSeq;
	}



	public void setReturnInfoSeq(Long returnInfoSeq) {
		this.returnInfoSeq = returnInfoSeq;
	}



	public String getReturnStyle() {
		return returnStyle;
	}



	public void setReturnStyle(String returnStyle) {
		this.returnStyle = returnStyle;
	}



	public String getReturnColor() {
		return returnColor;
	}



	public void setReturnColor(String returnColor) {
		this.returnColor = returnColor;
	}



	public String getReturnSize() {
		return returnSize;
	}



	public void setReturnSize(String returnSize) {
		this.returnSize = returnSize;
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



	public String getErpSd14Key() {
		return erpSd14Key;
	}



	public void setErpSd14Key(String erpSd14Key) {
		this.erpSd14Key = erpSd14Key;
	}

		
}
