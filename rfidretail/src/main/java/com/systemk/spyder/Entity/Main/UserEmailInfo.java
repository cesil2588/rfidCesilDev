package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "사용자 이메일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEmailInfo implements Serializable{

	private static final long serialVersionUID = 8477186157248895743L;

	// 사용자 이메일 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userEmailSeq;
	
	// 이메일 주소
	//@ApiModelProperty(notes = "이메일 주소")
	@Column(nullable = false, length = 100)
    private String email;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public long getUserEmailSeq() {
		return userEmailSeq;
	}

	public void setUserEmailSeq(long userEmailSeq) {
		this.userEmailSeq = userEmailSeq;
	}
}
