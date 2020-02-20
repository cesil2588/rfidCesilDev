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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@ApiModel(description = "사용자 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2170806115080157979L;

	// 사용자 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userSeq;

	// 사용자 ID
	//@ApiModelProperty(notes = "사용자 ID")
	@Column(nullable = false, length = 50)
	private String userId;

	// 패스워드
	//@ApiModelProperty(notes = "패스워드")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, length = 200)
	private String password;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;

	// 사용여부
	//@ApiModelProperty(notes = "사용 여부(Y, N)")
	@Column(nullable = false, length = 2)
	private String useYn;

	// 확인여부
	//@ApiModelProperty(notes = "관리자 확인 여부(Y, N)")
	@Column(nullable = false, length = 2)
	private String checkYn;

	// 마지막 로그인일시
	//@ApiModelProperty(notes = "마지막 로그인 일시")
	private Date lastLoginDate;

	// 사용자 맵핑 업체
	//@ApiModelProperty(notes = "사용자 맵핑 업체 일련번호")
	@OneToOne
	@JsonIgnoreProperties(value = {"userInfo"}, allowSetters = true)
	@JoinColumn(name = "companySeq")
	private CompanyInfo companyInfo;

	// 사용자 이메일 목록
	//@ApiModelProperty(notes = "사용자 맵핑 이메일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnoreProperties({ "userInfo" })
	@JoinColumn(name = "userSeq")
	private Set<UserEmailInfo> userEmailInfo;

	public long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(long userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Set<UserEmailInfo> getUserEmailInfo() {
		return userEmailInfo;
	}

	public void setUserEmailInfo(Set<UserEmailInfo> userEmailInfo) {
		this.userEmailInfo = userEmailInfo;
	}

	public UserInfo() {

	}

	@Override
	public String toString() {
		return "UserInfo [userSeq=" + userSeq + ", userId=" + userId + ", password=" + password + ", regDate=" + regDate
				+ ", updDate=" + updDate + ", useYn=" + useYn + ", checkYn=" + checkYn + ", lastLoginDate="
				+ lastLoginDate + ", companyInfo=" + companyInfo + ", userEmailInfo=" + userEmailInfo + "]";
	}

}