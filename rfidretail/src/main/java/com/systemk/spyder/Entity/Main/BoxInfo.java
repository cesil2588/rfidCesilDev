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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Util.CalendarUtil;
import org.apache.catalina.User;

//@ApiModel(description = "박스 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoxInfo implements Serializable{

	private static final long serialVersionUID = -7346830984201637770L;

	//  박스 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Long boxSeq;

    //  바코드
	//@ApiModelProperty(notes = "박스바코드")
	@Column(nullable = false, length = 18)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String barcode;

	// 날짜
	//@ApiModelProperty(notes = "날짜")
	@Column(nullable = true, length = 8)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String createDate;

    //  박스번호
	//@ApiModelProperty(notes = "박스번호")
	@Column(nullable = true, length = 4)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String boxNum;

    //  생산,물류,매장
	//@ApiModelProperty(notes = "타입(1: 생산, 2:물류, 3: 매장)")
	@Column(nullable = false, length = 2)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String type;

    //  반품여부
	//@ApiModelProperty(notes = "반품 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String returnYn;

	// 출발업체
	//@ApiModelProperty(notes = "출발업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="startCompanySeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private CompanyInfo startCompanyInfo;

	// 도착업체
	//@ApiModelProperty(notes = "도착업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="endCompanySeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private CompanyInfo endCompanyInfo;

    //  변경상태(1: 초기생성, 2:출고(태그 맵핑 완료), 3:출고완료(컨베이어, PDA 확인 완료), 4:폐기)
	//@ApiModelProperty(notes = "변경상태(1: 초기생성, 2:출고(태그 맵핑 완료), 3:출고완료(컨베이어, PDA 확인 완료), 4:폐기")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String stat;

    //  등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	@JsonView({View.Public.class})
    private UserInfo regUserInfo;

    //  등록일시
	//@ApiModelProperty(notes = "등록일시")
	@JsonView({View.Public.class})
    private Date regDate;

    //  수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
    @JsonView({View.Public.class})
    private UserInfo updUserInfo;

    // 수정일시
	//@ApiModelProperty(notes = "수정일시")
    @JsonView({View.Public.class})
    private Date updDate;

    // 도착예정일시
	//@ApiModelProperty(notes = "도착예정일시")
    @JsonInclude(Include.NON_NULL)
    @JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Date arrivalDate;

    // 물류도착완료 일시
	//@ApiModelProperty(notes = "도착완료일시")
    @JsonInclude(Include.NON_NULL)
    @JsonView({View.Public.class})
    private Date completeDate;

    // 인보이스 번호
	//@ApiModelProperty(notes = "인보이스번호")
    @Column(nullable = true, length = 50)
    @JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    @JsonInclude(Include.NON_NULL)
    private String invoiceNum;

	//@ApiModelProperty(notes = "박스 그룹 일련번호")
    @JsonView({View.Public.class})
    private Long boxWorkGroupSeq;

	//@ApiModelProperty(notes = "임시 인보이스번호")
    @Transient
    private String tempInvoiceNum;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Long boxSeq) {
		this.boxSeq = boxSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReturnYn() {
		return returnYn;
	}

	public void setReturnYn(String returnYn) {
		this.returnYn = returnYn;
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

	public CompanyInfo getStartCompanyInfo() {
		return startCompanyInfo;
	}

	public void setStartCompanyInfo(CompanyInfo startCompanyInfo) {
		this.startCompanyInfo = startCompanyInfo;
	}

	public CompanyInfo getEndCompanyInfo() {
		return endCompanyInfo;
	}

	public void setEndCompanyInfo(CompanyInfo endCompanyInfo) {
		this.endCompanyInfo = endCompanyInfo;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Long getBoxWorkGroupSeq() {
		return boxWorkGroupSeq;
	}

	public void setBoxWorkGroupSeq(Long boxWorkGroupSeq) {
		this.boxWorkGroupSeq = boxWorkGroupSeq;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getTempInvoiceNum() {
		return tempInvoiceNum;
	}

	public void setTempInvoiceNum(String tempInvoiceNum) {
		this.tempInvoiceNum = tempInvoiceNum;
	}

	public BoxInfo() {

	}

	public BoxInfo(String barcode, CompanyInfo startCompanyInfo, CompanyInfo endCompanyInfo, UserInfo userInfo) {
		this.barcode = barcode;
		this.startCompanyInfo = startCompanyInfo;
		this.endCompanyInfo = endCompanyInfo;
		this.returnYn = "N";
		this.regDate = new Date();
		this.regUserInfo = userInfo;
		this.type = "02";
		this.stat = "2";
		this.createDate = CalendarUtil.convertFormat("yyyyMMdd");
	}

	public void setStat(String stat, UserInfo userInfo){
		this.stat				= stat;
		this.updDate			= new Date();
		this.updUserInfo		= userInfo;
		this.completeDate		= new Date();
	}
}
