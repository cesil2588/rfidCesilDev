package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "메일 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MailLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3096594793130008506L;

	// 메일 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long mailSeq;
	
	// 보내는 메일
	//@ApiModelProperty(notes = "보내는 메일")
	@Column(nullable = false, length = 100)
	private String mailFrom;
	
	// 받는 메일
	//@ApiModelProperty(notes = "받는 메일")
	@Column(nullable = false, length = 100)
	private String mailSend;
	
	// 제목
	//@ApiModelProperty(notes = "제목")
	@Column(nullable = false, length = 100)
	private String mailTitle;
	
	// 상세내용
	//@ApiModelProperty(notes = "상세내용")
	@Column(nullable = false, length = 1000)
	private String mailContents;
	
	// 메일타입
	//@ApiModelProperty(notes = "타입(1: 회원가입, 2: 바택발행, 3: 태그발행완료, 4: 태그재발행완료, 5: 태그재발행요청)")
	@Column(nullable = false, length = 1)
	private String type;
	
	// 보낸일
	//@ApiModelProperty(notes = "보낸일")
	private Date sendDate;
	
	// 비고
	//@ApiModelProperty(notes = "비고")
	@Column(nullable = true, length = 500)
	private String mailRmks;
	
	// 확인 여부
	//@ApiModelProperty(notes = "확인 여부(Y, N)")
	@Column(nullable = true, length = 1)
	private String checkYn;
	
	// 에러메시지
	//@ApiModelProperty(notes = "에러 메시지")
	@Column(nullable = true, length = 1000)
	private String errorMessage;
	
	// 상태
	//@ApiModelProperty(notes = "상태(1: 성공, 2: 실패)")
	@Column(nullable = true, length = 1)
	private String stat;

	public long getMailSeq() {
		return mailSeq;
	}

	public void setMailSeq(long mailSeq) {
		this.mailSeq = mailSeq;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContents() {
		return mailContents;
	}

	public void setMailContents(String mailContents) {
		this.mailContents = mailContents;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getMailRmks() {
		return mailRmks;
	}

	public void setMailRmks(String mailRmks) {
		this.mailRmks = mailRmks;
	}
	
	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailSend() {
		return mailSend;
	}

	public void setMailSend(String mailSend) {
		this.mailSend = mailSend;
	}

	public MailLog(){
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public MailLog(String mailFrom, String mailSend, String mailTitle, String mailContents, Date sendDate,
			String mailRmks, String type, String checkYn, String stat, String errorMessage) {
		super();
		this.mailFrom = mailFrom;
		this.mailSend = mailSend;
		this.mailTitle = mailTitle;
		this.mailContents = mailContents;
		this.sendDate = sendDate;
		this.mailRmks = mailRmks;
		this.type = type;
		this.checkYn = checkYn;
		this.stat = stat;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "MailLog [mailSeq=" + mailSeq + ", mailFrom=" + mailFrom + ", mailSend=" + mailSend + ", mailTitle="
				+ mailTitle + ", mailContents=" + mailContents + ", sendDate=" + sendDate + ", mailRmks=" + mailRmks
				+ "]";
	}

	
}
