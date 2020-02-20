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
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//RFID 태그 이력 테이블
//@ApiModel(description = "RFID 태그 이력 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagHistory implements Serializable{

	private static final long serialVersionUID = -4912069455476502213L;

	//  태그 상태 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
    private Long rfidTagHistorySeq;
	
	// 태그 바코드
	//@ApiModelProperty(notes = "RFID 태그 바코드")
	@Column(nullable = false, length = 15)
	@JsonView({View.Public.class})
	private String barcode;
	
	// erp 키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class})
	private String erpKey;
	
	// 태그 시리얼
	//@ApiModelProperty(notes = "RFID 태그 시리얼")
	@Column(nullable = false, length = 5)
	@JsonView({View.Public.class})
	private String rfidSeq;
	
	// 태그 일련번호
	//@ApiModelProperty(notes = "RFID 태그 인코딩 정보")
	@Column(nullable = false, length = 32)
	@JsonView({View.Public.class})
	private String rfidTag;

    //  작업 내역
	//@ApiModelProperty(notes = "작업 내역(1: 발행 완료, 2: 생산 입고, 3: 생산 출고, 4: 생산 재발행요청, 5: 재발행 완료, 6: 생산반품검수, 7: 생산 반품, 8: 생산 폐기, " +
	//								 "11: 물류입고예정, 12: 물류 입고, 13: 물류 출고, 14: 물류 재발행 대기, 15: 물류 재발행 요청, 16: 물류 반품, 17: 물류 폐기, 18: 물류 입고 반품, " + 
	//								 "21: 매장입고예정, 22: 매장 입고, 23: 매장 출고(판매), 24: 반품, 25: 매장 재발행 요청, 26: 매장간 이동, 27: 폐기)")
    @Column(nullable = false, length = 10)
    @JsonView({View.Public.class, View.ExternalView.class})
    private String work;
    
    // 비고
	//@ApiModelProperty(notes = "비고")
    @Column(nullable = true, length = 100)
    @JsonView({View.Public.class})
    private String explanatory;

    //  업체 일련번호
	//@ApiModelProperty(notes = "작업 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companySeq")
    @JsonView({View.Public.class, View.ExternalView.class})
	private CompanyInfo companyInfo;

    // 등록자
	//@ApiModelProperty(notes = "등록자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
 	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="regUserSeq")
 	@JsonView({View.Public.class})
 	private UserInfo regUserInfo;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
 	@JsonView({View.Public.class, View.ExternalView.class})
    private Date regDate;
    
    public Long getRfidTagHistorySeq() {
        return rfidTagHistorySeq;
    }

    public void setRfidTagHistorySeq(Long rfidTagHistorySeq) {
        this.rfidTagHistorySeq = rfidTagHistorySeq;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public RfidTagHistory() {

	}

	public RfidTagHistory(RfidTagMaster tag, UserInfo userInfo, BoxInfo boxInfo, String flag) {
		this.barcode			= tag.getErpKey() + tag.getRfidSeq();
		this.erpKey				= tag.getErpKey();
		this.rfidTag			= tag.getRfidTag();
		this.rfidSeq			= tag.getRfidSeq();
		this.work				= flag;
		this.regUserInfo		= userInfo;
		this.companyInfo		= boxInfo.getEndCompanyInfo();
		this.regDate			= new Date();
	}
}
