package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

////@ApiModel(description = "어플리케이션 다운로드 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppDownloadLog implements Serializable{

	private static final long serialVersionUID = 2303859858152519016L;

	// app 다운로드 로그 일련번호
	////@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long appDownloadLogSeq;
	
	// app info 일련번호
	////@ApiModelProperty(notes = "어플리케이션 Entity 일련번호")
	private Long appSeq;
	
	// 등록일
	////@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 등록자
	////@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;
	
	public Long getAppDownloadLogSeq() {
		return appDownloadLogSeq;
	}

	public void setAppDownloadLogSeq(Long appDownloadLogSeq) {
		this.appDownloadLogSeq = appDownloadLogSeq;
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

	public Long getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(Long appSeq) {
		this.appSeq = appSeq;
	}
}
