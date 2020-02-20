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

//태그 상태 테이블
//@ApiModel(description = "RFID 태그 상태 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagStatus implements Serializable{

	private static final long serialVersionUID = 8656432636456944026L;

	//  태그 상태 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long rfidTagStatusSeq;

    //  ERP 키 + 태그 일련번호
	//@ApiModelProperty(notes = "RFID 태그 바코드")
	@Column(nullable = false, length = 15)
    private String barcode;

    //  ERP 키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
    private String erpKey;

    //  RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
	@Column(nullable = false, length = 32)
    private String rfidTag;
	
	// RFID 일련번호
	//@ApiModelProperty(notes = "RFID 태그 시리얼")
	@Column(nullable = false, length = 5)
	private String rfidSeq;

    //  상태(1:사용 가능, 2:폐기)
	//@ApiModelProperty(notes = "상태(1:사용 가능, 2:폐기)")
	@Column(nullable = false, length = 1)
    private String stat;

    //  등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="regUserSeq")
 	private UserInfo regUserInfo;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
    // 수정자
	//@ApiModelProperty(notes = "수정자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="updUserSeq")
 	private UserInfo updUserInfo;
    
    // 수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;

    //  비고
	//@ApiModelProperty(notes = "비고")
    @Column(nullable = true, length = 100)
    private String explanatory;

    public Long getRfidTagStatusSeq() {
        return rfidTagStatusSeq;
    }

    public void setRfidTagStatusSeq(Long rfidTagStatusSeq) {
        this.rfidTagStatusSeq = rfidTagStatusSeq;
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

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getExplanatory() {
        return explanatory;
    }

    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}
}
