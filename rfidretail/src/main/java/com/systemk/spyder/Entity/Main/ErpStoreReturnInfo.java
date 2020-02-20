package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.ErpStoreReturnNoBean;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "매장 반품 지시 정보 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "sku", "erpOrderKey"})
public class ErpStoreReturnInfo implements Serializable{

	private static final long serialVersionUID = 8747797875075532681L;

	//@ApiModelProperty(notes = "반품지시정보 일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long returnInfoSeq;

	// 지시번호(erp_return_no)
	//@ApiModelProperty(notes = "지시번호")
	@Column(nullable = false, length = 8)
	private Long erpReturnNo;

	//반품 지시일(erp_reg_date)
	//@ApiModelProperty(notes = "반품 지시일")
	@Column(nullable = true, length = 8)
	private Date erpRegDate;

	//반품 마감일(return_confirm_date)
	//@ApiModelProperty(notes = "반품 마감일")
	@Column(nullable = true, length = 8)
	private Date returnConfirmDate;

	//반품 제목(return_title)
	//@ApiModelProperty(notes = "반품 제목")
	@Column(nullable = true, length = 100)
	private String returnTitle;

	//반품 구분(return_type)
	//@ApiModelProperty(notes = "반품 구분")
	@Column(nullable = true, length = 2)
	private String returnType;

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

	//확정 여부(confirm_yn)
	//@ApiModelProperty(notes = "확정 여부")
	@Column(nullable = true, length = 1)
	private String confirmYn;

	//등록 주체 구분(reg_type)
	//@ApiModelProperty(notes = "등록주체(E:ERP, R:RFID)")
	@Column(nullable = true, length = 1)
	private String regType;

	//전송 여부(trans_yn)
	//@ApiModelProperty(notes = "전송여부")
	@Column(nullable = true, length = 1)
	private String transYn;

	//전송 일시(trans_date)
	//@ApiModelProperty(notes = "전송일시")
	@Column(nullable = true, length = 8)
	private Date transDate;

	//rfid_yn(지시 작업의 rfid여부)
	//@ApiModelProperty(notes = "rfid여부")
	@Column(nullable = true, length = 1)
	private String rfidYn;
	
	//from customer_code
	//@ApiModelProperty(notes = "from customer_code")
	@Column(nullable = true, length = 10)
	private String fromCustomerCode;
	
	//from corner_code
	//@ApiModelProperty(notes = "from corner_code")
	@Column(nullable = true, length = 1)
	private String fromCornerCode;
	
	//to customer_code
	//@ApiModelProperty(notes = "to customer_code")
	@Column(nullable = true, length = 10)
	private String toCustomerCode;
	
	//to corner_code
	//@ApiModelProperty(notes = "to corner_code")
	@Column(nullable = true, length = 1)
	private String toCornerCode;

	//sub형성을 위한 임시변수
	@Transient
	//@ApiModelProperty(note="sku")
	private String sku;
	
	//sub형성을 위한 임시변수 erpOrderKey
	@Transient
	//@ApiModelProperty(note="erpOrderKey")
	private String erpOrderKey;
	
	@Transient
	public List<ErpStoreReturnNoBean> erpNoList;

	// ErpStoreReturnInfo 모델 복사
    public void CopyData(ErpStoreReturnInfo param)
    {
        this.returnInfoSeq = param.returnInfoSeq;
        this.erpReturnNo = param.erpReturnNo;
        this.erpRegDate = param.erpRegDate;
        this.returnConfirmDate = param.returnConfirmDate;
        this.returnTitle = param.returnTitle;
        this.returnType = param.returnType;
        this.orderAmount = param.orderAmount;
        this.executeAmount = param.executeAmount;
        this.confirmAmount = param.confirmAmount;
        this.confirmYn = param.confirmYn;
        this.regType = param.regType;
        this.transYn = param.transYn;
        this.transDate = param.transDate;
        this.rfidYn = param.rfidYn;
    }


	public Long getReturnInfoSeq() {
		return returnInfoSeq;
	}


	public void setReturnInfoSeq(Long returnInfoSeq) {
		this.returnInfoSeq = returnInfoSeq;
	}


	public Long getErpReturnNo() {
		return erpReturnNo;
	}


	public void setErpReturnNo(Long erpReturnNo) {
		this.erpReturnNo = erpReturnNo;
	}


	public Date getErpRegDate() {
		return erpRegDate;
	}


	public void setErpRegDate(Date erpRegDate) {
		this.erpRegDate = erpRegDate;
	}


	public Date getReturnConfirmDate() {
		return returnConfirmDate;
	}


	public void setReturnConfirmDate(Date returnConfirmDate) {
		this.returnConfirmDate = returnConfirmDate;
	}


	public String getReturnTitle() {
		return returnTitle;
	}


	public void setReturnTitle(String returnTitle) {
		this.returnTitle = returnTitle;
	}


	public String getReturnType() {
		return returnType;
	}


	public void setReturnType(String returnType) {
		this.returnType = returnType;
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


	public String getConfirmYn() {
		return confirmYn;
	}


	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}


	public String getRegType() {
		return regType;
	}


	public void setRegType(String regType) {
		this.regType = regType;
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


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public String getErpOrderKey() {
		return erpOrderKey;
	}


	public void setErpOrderKey(String erpOrderKey) {
		this.erpOrderKey = erpOrderKey;
	}
	
	public List<ErpStoreReturnNoBean> getErpNoList() {
		return erpNoList;
	}


	public void setErpNoList(List<ErpStoreReturnNoBean> erpNoList) {
		this.erpNoList = erpNoList;
	}


}
