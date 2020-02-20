package com.systemk.spyder.Entity.Main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "에러 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErrorLog {

	// 에러 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long errorLogSeq;
	
	// 생성일
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = false, length = 8)
	private String createDate;
	
	// 에러코드
	//@ApiModelProperty(notes = "에러코드")
	@Column(nullable = false, length = 4)
	private String errorCode;
	
	// 에러메시지
	//@ApiModelProperty(notes = "에러메시지")
	@Lob
	@Column(nullable = false)
	private String errorMessage;
	
	// request URL
	//@ApiModelProperty(notes = "Request URL")
	@Column(nullable = false, length = 200)
	private String requestUrl;
	
	// request query
	//@ApiModelProperty(notes = "Request Query")
	@Lob
	@Column(nullable = false)
	private String requestQuery;
	
	// request method
	//@ApiModelProperty(notes = "Request Method")
	@Column(nullable = false, length = 10)
	private String requestMethod;
	
	// request ip
	//@ApiModelProperty(notes = "Request IP")
	@Column(nullable = false, length = 20)
	private String remoteIp;
	
	// request 기기 타입
	//@ApiModelProperty(notes = "Request 디바이스 타입")
	@Column(nullable = false, length = 1)
	private String deviceType;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	public Long getErrorLogSeq() {
		return errorLogSeq;
	}

	public void setErrorLogSeq(Long errorLogSeq) {
		this.errorLogSeq = errorLogSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getRequestQuery() {
		return requestQuery;
	}

	public void setRequestQuery(String requestQuery) {
		this.requestQuery = requestQuery;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
}
