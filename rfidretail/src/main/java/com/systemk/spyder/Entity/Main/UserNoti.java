package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "사용자 알람 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserNoti implements Serializable {

	private static final long serialVersionUID = -2978630437010819822L;

	// 사용자 알림 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userNotiSeq;
	
	// 알림 내역
	//@ApiModelProperty(notes = "알림내역")
	@Column(nullable = false, length = 100)
	private String notice;
	
	// 카테고리
	//@ApiModelProperty(notes = "카테고리")
	@Column(nullable = false, length = 100)
	private String category;
	
	// 체크여부
	//@ApiModelProperty(notes = "체크 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String checkYn;
	
	// 타겟 일련번호
	//@ApiModelProperty(notes = "알림 일련번호")
	@Column(nullable = false, length = 1)
	private Long targetSeq;
	
	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="notiUserSeq")
	private UserInfo notiUserInfo;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getTargetSeq() {
		return targetSeq;
	}

	public void setTargetSeq(long targetSeq) {
		this.targetSeq = targetSeq;
	}

	public Long getUserNotiSeq() {
		return userNotiSeq;
	}

	public void setUserNotiSeq(Long userNotiSeq) {
		this.userNotiSeq = userNotiSeq;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}

	public UserInfo getNotiUserInfo() {
		return notiUserInfo;
	}

	public void setNotiUserInfo(UserInfo notiUserInfo) {
		this.notiUserInfo = notiUserInfo;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
}
