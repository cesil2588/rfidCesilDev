package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//태그 재발행 요청테이블
//@ApiModel(description = "RFID 태그 재발행 요청 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagReissueRequest implements Serializable{

	private static final long serialVersionUID = -3024300905603224448L;

	// 태그 재발행 요청 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long rfidTagReissueRequestSeq;

	// 업체 일련번호
	//@ApiModelProperty(notes = "요청 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companySeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private CompanyInfo companyInfo;

	// 재발행 요청 태그
	//@ApiModelProperty(notes = "재발행 요청 상세 목록")
	@JsonIgnoreProperties({"RfidTagReissueRequest"})
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "rfidTagReissueRequestSeq")
	@OrderBy("rfidTag asc")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Set<RfidTagReissueRequestDetail> rfidTagReissueRequestDetail;

	// 태그 재발행 발행 장소(1: 발행업체, 2: 물류센터)
	//@ApiModelProperty(notes = "발행 장소(1: 발행업체, 2:물류센터)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String publishLocation;

	// 사유
	//@ApiModelProperty(notes = "사유")
	@Column(nullable = false, length = 1000)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String explanatory;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView({View.Public.class})
	private UserInfo regUserInfo;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@JsonView({View.Public.class})
	private Date regDate;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	@JsonView({View.Public.class})
	private UserInfo updUserInfo;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@JsonView({View.Public.class})
	private Date updDate;

	// 태그 재발행 타입(1: 생산업체 > 재발행업체, 2: 생산업체 > 물류센터, 3: 물류센터 > 물류센터, 4: 매장 > 물류센터)
	//@ApiModelProperty(notes = "태그 재발행 타입 (1: 생산업체 > 재발행업체, 2: 생산업체 > 물류센터, 3: 물류센터 > 물류센터, 4: 매장 > 물류센터)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String type;

	// 확정 여부
	//@ApiModelProperty(notes = "확정 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class, View.MobileList.class, View.MobileDetail.class })
	private String confirmYn;

	// 재발행 완료 여부
	//@ApiModelProperty(notes = "재발행 완료 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class, View.MobileList.class, View.MobileDetail.class })
	private String completeYn;

	// 확정일
	//@ApiModelProperty(notes = "확정일")
	@JsonView({ View.Public.class })
	private Date confirmDate;

	// 재발행 완료일
	//@ApiModelProperty(notes = "재발행 완료일")
	@JsonView({ View.Public.class })
	private Date completeDate;

	// 생성일
	//@ApiModelProperty(notes = "생성일자")
	@Column(nullable = true, length = 8)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String createDate;

	// 작업 라인
	//@ApiModelProperty(notes = "작업라인")
	@JsonView({View.Public.class})
	private Long workLine;

	public Long getRfidTagReissueRequestSeq() {
		return rfidTagReissueRequestSeq;
	}

	public void setRfidTagReissueRequestSeq(Long rfidTagReissueRequestSeq) {
		this.rfidTagReissueRequestSeq = rfidTagReissueRequestSeq;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
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

	public Set<RfidTagReissueRequestDetail> getRfidTagReissueRequestDetail() {
		return rfidTagReissueRequestDetail;
	}

	public void setRfidTagReissueRequestDetail(Set<RfidTagReissueRequestDetail> rfidTagReissueRequestDetail) {
		this.rfidTagReissueRequestDetail = rfidTagReissueRequestDetail;
	}

	public String getPublishLocation() {
		return publishLocation;
	}

	public void setPublishLocation(String publishLocation) {
		this.publishLocation = publishLocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getWorkLine() {
		return workLine;
	}

	public void setWorkLine(Long workLine) {
		this.workLine = workLine;
	}

	public RfidTagReissueRequest() {
	}

	public RfidTagReissueRequest(UserInfo userInfo, String explanatory) {
		this.regUserInfo			= userInfo;
		this.regDate				= new Date();
		this.publishLocation		= "2";
		this.type					= "4";
		this.companyInfo			= userInfo.getCompanyInfo();
		this.confirmYn				= "N";
		this.completeYn				= "N";
		this.explanatory			= explanatory;
		this.createDate				= new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
	}
}