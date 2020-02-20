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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

////@ApiModel(description = "어플리케이션 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppInfo implements Serializable{

	private static final long serialVersionUID = 2262718531044321985L;

	// App 일련번호
	////@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long appSeq;
	
	
	// App 파일명
	////@ApiModelProperty(notes = "파일명")
	@Column(nullable = false, length = 200)
	private String fileName;
	
	// App 파일path
	////@ApiModelProperty(notes = "파일 path")
	@Column(nullable = false, length = 200)
	private String filePath;
	
	// App 버전
	////@ApiModelProperty(notes = "파일 버전")
	@Column(nullable = false, length = 10)
	private String version;
	
	// 사용여부
	////@ApiModelProperty(notes = "사용여부")
	@Column(nullable = false, length = 1)
	private String useYn;
	
	// 타입
	////@ApiModelProperty(notes = "타입(1: 생산, 2: 물류, 3: 매장, 4: 컨베이어)")
	@Column(nullable = false, length = 1)
	private String type;
	
	// 대표 여부
	////@ApiModelProperty(notes = "대표여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String representYn;
	
	// 다운로드 횟수
	////@ApiModelProperty(notes = "다운로드 횟수")
	private Long downloadCount;
	
	// 등록일
	////@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 등록자
	////@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;
	
	// 수정일
	////@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 수정자
	////@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	private UserInfo updUserInfo;
	
	// App 다운로드 로그
	////@ApiModelProperty(notes = "어플리케이션 다운로드 로그")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "appSeq")
    private Set<AppDownloadLog> appDownloadLog;

	public Long getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(Long appSeq) {
		this.appSeq = appSeq;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRepresentYn() {
		return representYn;
	}

	public void setRepresentYn(String representYn) {
		this.representYn = representYn;
	}

	public Set<AppDownloadLog> getAppDownloadLog() {
		return appDownloadLog;
	}

	public void setAppDownloadLog(Set<AppDownloadLog> appDownloadLog) {
		this.appDownloadLog = appDownloadLog;
	}

	@Override
	public String toString() {
		return "AppInfo [appSeq=" + appSeq + ", fileName=" + fileName + ", filePath=" + filePath + ", version="
				+ version + ", useYn=" + useYn + ", type=" + type + ", representYn=" + representYn + ", downloadCount="
				+ downloadCount + ", regDate=" + regDate + ", regUserInfo=" + regUserInfo + ", updDate=" + updDate
				+ ", updUserInfo=" + updUserInfo + ", appDownloadLog=" + appDownloadLog + "]";
	}

}
