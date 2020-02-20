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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "매장간 이동 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreMoveLog implements Serializable{

	private static final long serialVersionUID = -6653913931827699481L;

	// 매장간이동 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long storeMoveLogSeq;
	
	// 생성일
	//@ApiModelProperty(notes = "생성일자")
	@Column(nullable = false, length = 8)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String createDate;
	
	// 작업 라인
	//@ApiModelProperty(notes = "작업라인")
	@JsonView({View.Public.class})
	private Long workLine;
	
	// 박스 일련번호
	//@ApiModelProperty(notes = "박스 일련번호")
	@JsonIgnoreProperties({"regUserInfo", "updUserInfo"})
	@OneToOne
	@JoinColumn(name="boxSeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private BoxInfo boxInfo;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@JsonView({View.Public.class})
	private Date regDate;
	
	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	@JsonView({View.Public.class})
	private UserInfo regUserInfo;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@JsonView({View.Public.class})
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	@JsonView({View.Public.class})
	private UserInfo updUserInfo;
	
	// 세부 스타일 정보
	//@ApiModelProperty(notes = "매장간 이동 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "storeMoveLogSeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Set<StoreMoveDetailLog> storeMoveDetailLog;
	
	// 시작 업체 작업 여부
	//@ApiModelProperty(notes = "보낸 업체 작업 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String startWorkYn;
	
	// 시작 업체 작업일
	//@ApiModelProperty(notes = "보낸 업체 작업일")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Date startWorkDate;
	
	// 시작 업체 확정 여부
	//@ApiModelProperty(notes = "보낸 업체 확정 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String startConfirmYn;
	
	// 시작 업체 확정일
	//@ApiModelProperty(notes = "보낸 업체 확정일")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Date startConfirmDate;
	
	// 도착 업체 작업 여부
	//@ApiModelProperty(notes = "받는 업체 작업 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String endWorkYn;
	
	// 도착 업체 작업일
	//@ApiModelProperty(notes = "받는 업체 작업일")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Date endWorkDate;
	
	// 도착 업체 확정 여부
	//@ApiModelProperty(notes = "받는 업체 확정 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String endConfirmYn;
		
	// 도착 업체 확정일
	//@ApiModelProperty(notes = "받는 업체 확정일")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Date endConfirmDate;
		
	// 종결 여부
	//@ApiModelProperty(notes = "종결 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String disuseYn;
	
	// 반품 구분
	//@ApiModelProperty(notes = "반품 구분(10:일반반품, 15:이동반품, 20:비용반품, 40:불량반품, 90:계절반품, 80:일반반품대기)")
	@Column(nullable = false, length = 2)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String returnType;
	
	// ERP 여부
	//@ApiModelProperty(notes = "ERP 요청 데이터 여부(Y, N)")
	@Column(nullable = false, length = 2)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String erpYn;
	
	// 사유
	//@ApiModelProperty(notes = "사유")
	@Column(nullable = true, length = 200)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String explanatory;

	public Long getStoreMoveLogSeq() {
		return storeMoveLogSeq;
	}

	public void setStoreMoveLogSeq(Long storeMoveLogSeq) {
		this.storeMoveLogSeq = storeMoveLogSeq;
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

	public BoxInfo getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Set<StoreMoveDetailLog> getStoreMoveDetailLog() {
		return storeMoveDetailLog;
	}

	public void setStoreMoveDetailLog(Set<StoreMoveDetailLog> storeMoveDetailLog) {
		this.storeMoveDetailLog = storeMoveDetailLog;
	}

	public String getDisuseYn() {
		return disuseYn;
	}

	public void setDisuseYn(String disuseYn) {
		this.disuseYn = disuseYn;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getStartConfirmYn() {
		return startConfirmYn;
	}

	public void setStartConfirmYn(String startConfirmYn) {
		this.startConfirmYn = startConfirmYn;
	}

	public Date getStartConfirmDate() {
		return startConfirmDate;
	}

	public void setStartConfirmDate(Date startConfirmDate) {
		this.startConfirmDate = startConfirmDate;
	}

	public String getEndConfirmYn() {
		return endConfirmYn;
	}

	public void setEndConfirmYn(String endConfirmYn) {
		this.endConfirmYn = endConfirmYn;
	}

	public Date getEndConfirmDate() {
		return endConfirmDate;
	}

	public void setEndConfirmDate(Date endConfirmDate) {
		this.endConfirmDate = endConfirmDate;
	}

	public String getErpYn() {
		return erpYn;
	}

	public void setErpYn(String erpYn) {
		this.erpYn = erpYn;
	}

	public String getStartWorkYn() {
		return startWorkYn;
	}

	public void setStartWorkYn(String startWorkYn) {
		this.startWorkYn = startWorkYn;
	}

	public Date getStartWorkDate() {
		return startWorkDate;
	}

	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}

	public String getEndWorkYn() {
		return endWorkYn;
	}

	public void setEndWorkYn(String endWorkYn) {
		this.endWorkYn = endWorkYn;
	}

	public Date getEndWorkDate() {
		return endWorkDate;
	}

	public void setEndWorkDate(Date endWorkDate) {
		this.endWorkDate = endWorkDate;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}
}
