package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "매장 반품 지시 태그 정보 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpStoreReturnTag implements Serializable{

	private static final long serialVersionUID = 8747797875075532681L;
	
	//@ApiModelProperty(notes = "반품지시 태그 정보 일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	@JsonIgnore
	private Long returnTagSeq;
		
	//지시 정보 서브 테이블 시퀀스(return_info_sub_seq)
	//@ApiModelProperty(notes = "지시정보 서브 테이블 시퀀스")
	@Column(nullable = true, length = 8)
	@JsonIgnore
	private Long returnInfoSubSeq;
	
	//rfid_tag(rfid_tag)
	//@ApiModelProperty(notes = "반품 태그")
	@Column(nullable = true, length = 32)
	@JsonIgnore
	private String rfidTag;
	
	//저장 작업 박스 번호(work_box_num)
	//@ApiModelProperty(notes = "작업 박스 번호")
	@Column(nullable = false)
	private Long workBoxNum;
	
	//확정 여부(complete_yn)
	//@ApiModelProperty(notes = "확정 여부")
	@Column(nullable = true, length=1)
	private String completeYn;
	
	//확정 일자(complete_date)
	//@ApiModelProperty(notes = "확정 일자")
	@Column(nullable = true, length=20)
	private Date completeDate;
	
	//erp에서 프로시저 호출후 받게되는 미결명세서 번호
	//@ApiModelProperty(notes = "erp 미결명세서번호"
	@Column(nullable = true, length = 20)
	private String erpReturnInvoiceNum;
	
	//작업자 seq
	//@ApiModelProperty(note="작업자 시퀀스")
	@Column(nullable = true)
	private Long updUserSeq;
	
	// 지시 상세 일련번호
	//@ApiModelProperty(notes = "지시 상세 일련번호")
	@OneToOne
	@JsonIgnoreProperties(value = {"returnInfoSubSeq", "returnInfoSeq", "orderAmount", "confirmAmount", "erpStoreReturnTag"}, allowSetters = true)
	@JoinColumn(name = "returnInfoSubSeq", insertable = false, updatable = false)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private ErpStoreReturnSubInfo erpStoreReturnSubInfo;
	
	
	// ErpStoreReturnTag 모델 복사
    public void CopyData(ErpStoreReturnTag param)
    {
        this.returnTagSeq = param.getReturnTagSeq();
        this.returnInfoSubSeq = param.getReturnInfoSubSeq();
        this.rfidTag = param.getRfidTag();
        this.workBoxNum = param.getWorkBoxNum();
        this.completeYn = param.getCompleteYn();
        this.completeDate = param.getCompleteDate();
        this.erpReturnInvoiceNum = param.getErpReturnInvoiceNum();
    }


	public Long getReturnTagSeq() {
		return returnTagSeq;
	}


	public void setReturnTagSeq(Long returnTagSeq) {
		this.returnTagSeq = returnTagSeq;
	}


	public Long getReturnInfoSubSeq() {
		return returnInfoSubSeq;
	}


	public void setReturnInfoSubSeq(Long returnInfoSubSeq) {
		this.returnInfoSubSeq = returnInfoSubSeq;
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


	public String getErpReturnInvoiceNum() {
		return erpReturnInvoiceNum;
	}


	public void setErpReturnInvoiceNum(String erpReturnInvoiceNum) {
		this.erpReturnInvoiceNum = erpReturnInvoiceNum;
	}


	public Date getCompleteDate() {
		return completeDate;
	}


	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}


	public ErpStoreReturnSubInfo getErpStoreReturnSubInfo() {
		return erpStoreReturnSubInfo;
	}


	public void setErpStoreReturnSubInfo(ErpStoreReturnSubInfo erpStoreReturnSubInfo) {
		this.erpStoreReturnSubInfo = erpStoreReturnSubInfo;
	}


	public Long getUpdUserSeq() {
		return updUserSeq;
	}


	public void setUpdUserSeq(Long updUserSeq) {
		this.updUserSeq = updUserSeq;
	}

}
