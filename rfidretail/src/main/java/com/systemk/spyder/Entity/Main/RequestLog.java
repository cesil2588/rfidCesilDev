package com.systemk.spyder.Entity.Main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "HTTP 요청 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RequestLog {

	// request 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long requestLogSeq;

	// 생성일
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = false, length = 8)
	private String createDate;

	// request URL
	//@ApiModelProperty(notes = "Request URL")
	@Column(nullable = false, length = 200)
	private String requestUrl;

	// request header
	//@ApiModelProperty(notes = "Request 헤더")
	@Column(nullable = false, length = 1000)
	private String header;

	// request 메소드
	//@ApiModelProperty(notes = "Request Method")
	@Column(nullable = false, length = 10)
	private String requestMethod;

	// request query
	//@ApiModelProperty(notes = "Request Query")
	@Lob
	@Column(nullable = true)
	private String requestQuery;

	// request ip
	//@ApiModelProperty(notes = "Request Ip")
	@Column(nullable = false, length = 20)
	private String remoteIp;

	// user Agent 값
	//@ApiModelProperty(notes = "Request UserAgent")
	@Column(nullable = false, length = 200)
	private String userAgent;

	// session 값
	//@ApiModelProperty(notes = "세션")
	@Column(nullable = false, length = 50)
	private String session;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	// 수정일
	private Date updDate;


	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;

	// 응답 데이터
	@Lob
	@Column(nullable = true)
	private String responseBody;

	// 응답 상태
	@Column(nullable = true, length = 10)
	private String status;

	public Long getRequestLogSeq() {
		return requestLogSeq;
	}

	public void setRequestLogSeq(Long requestLogSeq) {
		this.requestLogSeq = requestLogSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestQuery() {
		return requestQuery;
	}

	public void setRequestQuery(String requestQuery) {
		this.requestQuery = requestQuery;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
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

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
