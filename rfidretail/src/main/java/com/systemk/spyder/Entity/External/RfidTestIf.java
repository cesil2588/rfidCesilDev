package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidZa11IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_test_if")
public class RfidTestIf implements Serializable{

	/**
	 * 프로시저 실행과 트랜잭션 테스트를 위한 테스트 엔터티 클래스
	 */
	private static final long serialVersionUID = -2500600744362852454L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private String testSeq;
	
	@Column(name="test_name", nullable = false, length=5)
	private String testName;
	
	@Column(name="test_pw", nullable = false)
	private Long testPw;

	public String getTestSeq() {
		return testSeq;
	}

	public void setTestSeq(String testSeq) {
		this.testSeq = testSeq;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Long getTestPw() {
		return testPw;
	}

	public void setTestPw(Long testPw) {
		this.testPw = testPw;
	}
	
	
}