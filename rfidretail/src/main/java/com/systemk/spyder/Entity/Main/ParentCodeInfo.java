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
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "상위 코드 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentCodeInfo implements Serializable{

	private static final long serialVersionUID = -8759598448077181308L;

	// 상위코드 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long parentCodeSeq;
	
	// 상위코드명
	//@ApiModelProperty(notes = "상위코드명")
	@Column(nullable = false, length = 100)
	private String name;
	
	// 상위코드값
	//@ApiModelProperty(notes = "상위코드값")
	@Column(nullable = true, length = 20)
	private String codeValue;
	
	// 사용여부
	//@ApiModelProperty(notes = "사용여부(Y, N)")
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
	
	// 하위 코드 목록
	//@ApiModelProperty(notes = "하위 코드 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("sort asc")
	@JoinColumn(name = "parentCodeSeq")
	private Set<CodeInfo> codeInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getParentCodeSeq() {
		return parentCodeSeq;
	}

	public void setParentCodeSeq(long parentCodeSeq) {
		this.parentCodeSeq = parentCodeSeq;
	}

	public Set<CodeInfo> getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(Set<CodeInfo> codeInfo) {
		this.codeInfo = codeInfo;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	
}
