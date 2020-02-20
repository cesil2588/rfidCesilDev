package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//@ApiModel(description = "하위 코드 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CodeInfo implements Serializable{

	private static final long serialVersionUID = -3031457966925175842L;

	// 코드 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long codeSeq;
	
	// 상위 코드 일련번호
	//@ApiModelProperty(notes = "상위 코드 일련번호")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "parentCodeSeq", scope = ParentCodeInfo.class)
	@ManyToOne
	@JoinColumn(name = "parentCodeSeq")
	private ParentCodeInfo parentCodeInfo;
	
	// 코드 값
	//@ApiModelProperty(notes = "코드 값")
	@Column(nullable = false, length = 20)
	private String codeValue;
	
	// erp 코드 값
	//@ApiModelProperty(notes = "ERP 코드 값")
	@Column(nullable = true, length = 20)
	private String erpCodeValue;
	
	// 코드명
	//@ApiModelProperty(notes = "코드 명")
	@Column(nullable = false, length = 100)
	private String name;
	
	// 사용여부
	//@ApiModelProperty(notes = "사용 여부(Y, N)")
	@Column(nullable = false, length = 2)
    private String useYn;
	
	// 순서
	//@ApiModelProperty(notes = "순서")
    private long sort;
	
    // 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
    
	// 수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;
    
    // 등록자
	//@ApiModelProperty(notes = "등록자")
	private Long regUserSeq;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	private Long updUserSeq;

	public long getCodeSeq() {
		return codeSeq;
	}

	public void setCodeSeq(long codeSeq) {
		this.codeSeq = codeSeq;
	}
	
	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Long regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public Long getUpdUserSeq() {
		return updUserSeq;
	}

	public void setUpdUserSeq(Long updUserSeq) {
		this.updUserSeq = updUserSeq;
	}

	public ParentCodeInfo getParentCodeInfo() {
		return parentCodeInfo;
	}

	public void setParentCodeInfo(ParentCodeInfo parentCodeInfo) {
		this.parentCodeInfo = parentCodeInfo;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getErpCodeValue() {
		return erpCodeValue;
	}

	public void setErpCodeValue(String erpCodeValue) {
		this.erpCodeValue = erpCodeValue;
	}
}
