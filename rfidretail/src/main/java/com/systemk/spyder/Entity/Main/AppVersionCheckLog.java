package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

////@ApiModel(description = "어플리케이션 버전 체크 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppVersionCheckLog implements Serializable{

	private static final long serialVersionUID = 3608605341626514617L;

	// App 버전 체크 일련번호
	////@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long appVersionCheckLogSeq;
	
	// 등록일
	////@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// PDA 시리얼
	////@ApiModelProperty(notes = "디바이스 시리얼")
	@Column(nullable = false, length = 100)
	private String serial;
	
	// PDA App 버전
	////@ApiModelProperty(notes = "디바이스 어플리케이션 버전")
	@Column(nullable = false, length = 10)
	private String pdaAppVersion;
	
	// App 현재 버전
	////@ApiModelProperty(notes = "어플리케이션 마지막 버전")
	@Column(nullable = false, length = 10)
	private String appLastVersion;

	public Long getAppVersionCheckLogSeq() {
		return appVersionCheckLogSeq;
	}

	public void setAppVersionCheckLogSeq(Long appVersionCheckLogSeq) {
		this.appVersionCheckLogSeq = appVersionCheckLogSeq;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPdaAppVersion() {
		return pdaAppVersion;
	}

	public void setPdaAppVersion(String pdaAppVersion) {
		this.pdaAppVersion = pdaAppVersion;
	}

	public String getAppLastVersion() {
		return appLastVersion;
	}

	public void setAppLastVersion(String appLastVersion) {
		this.appLastVersion = appLastVersion;
	}

	@Override
	public String toString() {
		return "AppVersionCheckLog [appVersionCheckLogSeq=" + appVersionCheckLogSeq + ", regDate=" + regDate
				+ ", serial=" + serial + ", pdaAppVersion=" + pdaAppVersion + ", appLastVersion=" + appLastVersion
				+ "]";
	}
}
