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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "박스 그룹 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoxWorkGroup implements Serializable{

	private static final long serialVersionUID = -5424979175023304197L;

	//  박스 작업 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long boxWorkGroupSeq;
	
	// 박스 목록
	//@ApiModelProperty(notes = "박스 목록")
	@JsonIgnoreProperties({"regUserInfo", "updUserInfo"})
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "boxWorkGroupSeq")
	@OrderBy("boxNum asc")
	private Set<BoxInfo> boxInfo;
	
	// 시작 박스 번호
	//@ApiModelProperty(notes = "시작 박스 번호")
	@Column(nullable = false, length = 4)
	private String startBoxNum;
	
	// 종료 박스 번호
	//@ApiModelProperty(notes = "종료 박스 번호")
	@Column(nullable = false, length = 4)
	private String endBoxNum;
	
	// 출발업체
	//@ApiModelProperty(notes = "출발업체")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="startCompanySeq")
	private CompanyInfo startCompanyInfo;
			
	// 도착업체
	//@ApiModelProperty(notes = "도착업체")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="endCompanySeq")
	private CompanyInfo endCompanyInfo;

	//  변경상태(1: 프린트 출력 안함, 2: 프린트 출력 완료)
	//@ApiModelProperty(notes = "변경상태(1: 프린트 출력 안함, 2: 프린트 출력 완료)")
	@Column(nullable = false, length = 1)
	private String stat;
	
	//  등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
    private UserInfo regUserInfo;

    //  등록일시
	//@ApiModelProperty(notes = "등록일시")
    private Date regDate;
    
    //  수정자
	//@ApiModelProperty(notes = "수정자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
    private UserInfo updUserInfo;
    
    // 수정일시
	//@ApiModelProperty(notes = "수정일시")
    private Date updDate;
    
    //  생산,물류,매장
	//@ApiModelProperty(notes = "타입(1: 생산, 2: 물류, 3: 매장)")
	@Column(nullable = true, length = 2)
    private String type;
	
	// 날짜
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = true, length = 8)
	private String createDate;

	public Long getBoxWorkGroupSeq() {
		return boxWorkGroupSeq;
	}

	public void setBoxWorkGroupSeq(Long boxWorkGroupSeq) {
		this.boxWorkGroupSeq = boxWorkGroupSeq;
	}

	public CompanyInfo getStartCompanyInfo() {
		return startCompanyInfo;
	}

	public void setStartCompanyInfo(CompanyInfo startCompanyInfo) {
		this.startCompanyInfo = startCompanyInfo;
	}

	public CompanyInfo getEndCompanyInfo() {
		return endCompanyInfo;
	}

	public void setEndCompanyInfo(CompanyInfo endCompanyInfo) {
		this.endCompanyInfo = endCompanyInfo;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Set<BoxInfo> getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(Set<BoxInfo> boxInfo) {
		this.boxInfo = boxInfo;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getStartBoxNum() {
		return startBoxNum;
	}

	public void setStartBoxNum(String startBoxNum) {
		this.startBoxNum = startBoxNum;
	}

	public String getEndBoxNum() {
		return endBoxNum;
	}

	public void setEndBoxNum(String endBoxNum) {
		this.endBoxNum = endBoxNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
