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
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "업체 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CompanyInfo implements Serializable {

	private static final long serialVersionUID = 1322330624660536852L;

	// 업체 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({ View.Public.class, View.MobileList.class, View.MobileDetail.class })
	private long companySeq;

	// 업체 명
	//@ApiModelProperty(notes = "업체 명")
	@Column(nullable = false, length = 100)
	@JsonView({ View.Public.class, View.MobileList.class, View.MobileDetail.class, View.ExternalView.class })
	private String name;

	// 업체코드(RFID 인코딩용)
	//@ApiModelProperty(notes = "업체코드(RFID 인코딩 코드)")
	@Column(nullable = true, length = 3)
	@JsonView({ View.Public.class })
	private String code;

	// 업체코너명
	//@ApiModelProperty(notes = "업체코너명")
	@Column(nullable = true, length = 14)
	@JsonView({ View.Public.class, View.ExternalView.class })
	private String cornerName;

	// 업체코드
	//@ApiModelProperty(notes = "업체코드")
	@Column(nullable = true, length = 10)
	@JsonView({ View.Public.class, View.ExternalView.class })
	private String customerCode;

	// 업체코너코드
	//@ApiModelProperty(notes = "업체코너코드")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class, View.ExternalView.class })
	private String cornerCode;

	// 타입
	//@ApiModelProperty(notes = "타입(1: 관리자, 2: 태그발행, 3: 생산, 4: 물류, 5: 매장, 6: 특약, 7: 태그발행관리)")
	@Column(nullable = false, length = 20)
	@JsonView({ View.Public.class })
	private String type;

	// 폐업여부
	//@ApiModelProperty(notes = "폐업 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class })
	private String closeYn;

	// 주소1
	//@ApiModelProperty(notes = "주소 1")
	@Column(nullable = true, length = 100)
	@JsonView({ View.Public.class })
	private String address1;

	// 주소2
	//@ApiModelProperty(notes = "주소 2")
	@Column(nullable = true, length = 100)
	@JsonView({ View.Public.class })
	private String address2;

	// 전화번호
	//@ApiModelProperty(notes = "전화번호")
	@Column(nullable = false, length = 20)
	@JsonView({ View.Public.class })
	private String telNo;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@JsonView({ View.Public.class })
	private Date regDate;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@JsonView({ View.Public.class })
	private Date updDate;

	// 사용여부
	//@ApiModelProperty(notes = "사용여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({ View.Public.class })
	private String useYn;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonView({ View.Public.class })
	private Long regUserSeq;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonView({ View.Public.class })
	private Long updUserSeq;

	// 생성일(ERP)
	//@ApiModelProperty(notes = "생성일(ERP)")
	@Column(nullable = true, length = 8)
	@JsonView({ View.Public.class })
	private String createDate;

	// 생성번호(ERP)
	//@ApiModelProperty(notes = "생성 번호(ERP)")
	@Column(nullable = true)
	@ColumnDefault("0")
	@JsonView({ View.Public.class })
	private long createNo;

	// ERP 전달 데이터 여부
	//@ApiModelProperty(notes = "ERP 인터페이스 데이터 여부")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class })
	private String erpYn;

	//@ApiModelProperty(notes = "ERP 상태")
	@Transient
	@JsonView({ View.Public.class })
	private String erpStat;

	//@ApiModelProperty(notes = "권한 목록")
	@JsonIgnoreProperties(value = { "companyInfo" }, allowSetters = true)
	//@JsonIgnoreProperties({ "companyInfo" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	private RoleInfo roleInfo;

	public void setCompanySeq(long companySeq) {
		this.companySeq = companySeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCornerName() {
		return cornerName;
	}

	public void setCornerName(String cornerName) {
		this.cornerName = cornerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCornerCode() {
		return cornerCode;
	}

	public void setCornerCode(String cornerCode) {
		this.cornerCode = cornerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Long getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Long regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public Long getUpdUserSeq() {
		return updUserSeq;
	}

	public void setUpdUserSeq(Long updUserSeq) {
		this.updUserSeq = updUserSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getCreateNo() {
		return createNo;
	}

	public void setCreateNo(long createNo) {
		this.createNo = createNo;
	}

	public String getErpYn() {
		return erpYn;
	}

	public void setErpYn(String erpYn) {
		this.erpYn = erpYn;
	}

	public String getErpStat() {
		return erpStat;
	}

	public void setErpStat(String erpStat) {
		this.erpStat = erpStat;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public long getCompanySeq() {
		return companySeq;
	}

	@Override
	public String toString() {
		return "CompanyInfo [companySeq=" + companySeq + ", name=" + name + ", code=" + code + ", cornerName="
				+ cornerName + ", customerCode=" + customerCode + ", cornerCode=" + cornerCode + ", type=" + type
				+ ", closeYn=" + closeYn + ", address1=" + address1 + ", address2=" + address2 + ", telNo=" + telNo
				+ ", regDate=" + regDate + ", updDate=" + updDate + ", useYn=" + useYn + ", regUserSeq=" + regUserSeq
				+ ", updUserSeq=" + updUserSeq + ", createDate=" + createDate + ", createNo=" + createNo + ", erpYn="
				+ erpYn + ", erpStat=" + erpStat + ", roleInfo=" + roleInfo + "]";
	}

}
