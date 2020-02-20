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

//@ApiModel(description = "물류 매장 출고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpStoreSchedule implements Serializable{

	private static final long serialVersionUID = 8747797875075532681L;
	
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long erpStoreScheduleSeq;
	
	// 물류마감일자
	//@ApiModelProperty(notes = "물류마감일자")
	@Column(nullable = false, length = 8)
	private String completeDate;

	// 물류마감구분
	//@ApiModelProperty(notes = "물류마감구분")
	@Column(nullable = false, length = 1)
	private String completeType;

	// 물류마감번호
	//@ApiModelProperty(notes = "물류마감번호")
	@Column(nullable = false, length = 2)
	private String completeSeq;

	// 출발지 정보
	//@ApiModelProperty(notes = "출발업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startCompanySeq")
	private CompanyInfo startCompanyInfo;

	// 도착지 정보
	//@ApiModelProperty(notes = "도착업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endCompanySeq")
	private CompanyInfo endCompanyInfo;

	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	private String style;

	// 제품코드
	//@ApiModelProperty(notes = "컬러 + 사이즈")
	@Column(nullable = false, length = 20)
	private String anotherStyle;

	// 번들코드
	//@ApiModelProperty(notes = "번들코드")
	@Column(nullable = false, length = 5)
	private String bundleCd;
	
	// 물류마감인터페이스 순번
	//@ApiModelProperty(notes = "물류마감 인터페이스 순번")
    @Column(nullable = false, length = 5)
    private String completeIfSeq;

    // 컬러
	//@ApiModelProperty(notes = "컬러")
    @Column(nullable = false, length = 5)
    private String color;

    // 사이즈
	//@ApiModelProperty(notes = "사이즈")
    @Column(nullable = false, length = 5)
    private String size;

    // 주문수량
	//@ApiModelProperty(notes = "주문 수량")
    private Long orderAmount;

    // sorting 수량
	//@ApiModelProperty(notes = "Sorting 수량")
    private Long sortingAmount;

    // 출고 수량
	//@ApiModelProperty(notes = "출고 수량")
    private Long releaseAmount;
    
    // 매장 입고(완료) 수량
	//@ApiModelProperty(notes = "매장 입고(완료) 수량")
    private Long completeAmount;

    // 출고 일자
	//@ApiModelProperty(notes = "출고 일자")
    @Column(nullable = false, length = 8)
    private String releaseDate;

    // 출고 일련번호
	//@ApiModelProperty(notes = "출고 일련번호")
    private Long releaseSeq;

    // 출고 시리얼
	//@ApiModelProperty(notes = "출고 시리얼")
    private Long releaseSerial;

    // 출고 등록ID
	//@ApiModelProperty(notes = "출고 등록 ID")
    @Column(nullable = false, length = 10)
    private String releaseUserId;

    // 상태
	//@ApiModelProperty(notes = "출고 상태(I: 신규, U: 수정, D: 삭제)")
    @Column(nullable = false, length = 1)
    private String stat;

    // ERP 등록일시
	//@ApiModelProperty(notes = "ERP 등록일시")
    @Column(nullable = false, length = 8)
    private String erpRegDate;

    // 등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
    // 등록시간
	//@ApiModelProperty(notes = "등록시간")
    @Column(nullable = true, length = 4)
    private String regTime;
    
    // 수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;
    
    // 등록자
	//@ApiModelProperty(notes = "등록자")
    @Column(nullable = false, length = 10)
    private String regUserId;
    
	//@ApiModelProperty(notes = "임시 출발 업체 코드")
    @Transient
    private String tempStartCustomerCode;
    
    // 배치 작업 여부
	//@ApiModelProperty(notes = "베치 작업 여부(Y, N)")
    @Column(nullable = true, length = 1)
    private String batchYn;
    
	//@ApiModelProperty(notes = "베치 작업일")
    private Date batchDate;
    
    // ERP 출고 실적 전송 여부
	//@ApiModelProperty(notes = "ERP 출고 실적 전송 여부(Y, N)")
    @Column(nullable = true, length = 1)
    private String erpCompleteYn;
    
	//@ApiModelProperty(notes = "ERP 출고 실적 전송일")
    private Date erpCompleteDate;
    
    // referenceNo
	//@ApiModelProperty(notes = "ReferenceNo")
    @Column(nullable = true, length = 30)
    private String referenceNo;
    
    // 완료 여부
	//@ApiModelProperty(notes = "완료 여부(Y, N)")
    @Column(nullable = true, length = 1)
 	private String completeCheckYn;
    
    // 완료 일자
	//@ApiModelProperty(notes = "완료 일자")
    private Date completeCheckDate;
    
    // RFID 발행 여부
	//@ApiModelProperty(notes = "RFID 발행 여부(Y, N)")
    @Column(nullable = true, length = 1)
    private String rfidYn;
    
    @Transient
    private String tempErpkey;
    
	public String getCompleteIfSeq() {
		return completeIfSeq;
	}

	public void setCompleteIfSeq(String completeIfSeq) {
		this.completeIfSeq = completeIfSeq;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getSortingAmount() {
		return sortingAmount;
	}

	public void setSortingAmount(Long sortingAmount) {
		this.sortingAmount = sortingAmount;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getReleaseSeq() {
		return releaseSeq;
	}

	public void setReleaseSeq(Long releaseSeq) {
		this.releaseSeq = releaseSeq;
	}

	public Long getReleaseSerial() {
		return releaseSerial;
	}

	public void setReleaseSerial(Long releaseSerial) {
		this.releaseSerial = releaseSerial;
	}

	public String getReleaseUserId() {
		return releaseUserId;
	}

	public void setReleaseUserId(String releaseUserId) {
		this.releaseUserId = releaseUserId;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getErpRegDate() {
		return erpRegDate;
	}

	public void setErpRegDate(String erpRegDate) {
		this.erpRegDate = erpRegDate;
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

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompleteType() {
		return completeType;
	}

	public void setCompleteType(String completeType) {
		this.completeType = completeType;
	}

	public String getCompleteSeq() {
		return completeSeq;
	}

	public void setCompleteSeq(String completeSeq) {
		this.completeSeq = completeSeq;
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAnotherStyle() {
		return anotherStyle;
	}

	public void setAnotherStyle(String anotherStyle) {
		this.anotherStyle = anotherStyle;
	}

	public String getBundleCd() {
		return bundleCd;
	}

	public void setBundleCd(String bundleCd) {
		this.bundleCd = bundleCd;
	}
	
	public Long getErpStoreScheduleSeq() {
		return erpStoreScheduleSeq;
	}

	public void setErpStoreScheduleSeq(Long erpStoreScheduleSeq) {
		this.erpStoreScheduleSeq = erpStoreScheduleSeq;
	}
	
	// ErpStoreSchedule 모델 복사
    public void CopyData(ErpStoreSchedule param)
    {
        this.color = param.getColor();
        this.completeIfSeq = param.getCompleteIfSeq();
        this.erpRegDate = param.getErpRegDate();
        this.orderAmount = param.getOrderAmount();
        this.regDate = param.getRegDate();
        this.regUserId = param.getRegUserId();
        this.releaseAmount = param.getReleaseAmount();
        this.releaseDate = param.getReleaseDate();
        this.releaseSeq = param.getReleaseSeq();
        this.releaseSerial = param.getReleaseSerial();
        this.releaseUserId = param.getReleaseUserId();
        this.size = param.getSize();
        this.sortingAmount = param.getSortingAmount();
        this.stat = param.getStat();
        this.updDate = param.getUpdDate();
    }

	public String getTempStartCustomerCode() {
		return tempStartCustomerCode;
	}

	public void setTempStartCustomerCode(String tempStartCustomerCode) {
		this.tempStartCustomerCode = tempStartCustomerCode;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getBatchYn() {
		return batchYn;
	}

	public void setBatchYn(String batchYn) {
		this.batchYn = batchYn;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	public String getErpCompleteYn() {
		return erpCompleteYn;
	}

	public void setErpCompleteYn(String erpCompleteYn) {
		this.erpCompleteYn = erpCompleteYn;
	}

	public Date getErpCompleteDate() {
		return erpCompleteDate;
	}

	public void setErpCompleteDate(Date erpCompleteDate) {
		this.erpCompleteDate = erpCompleteDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getCompleteCheckYn() {
		return completeCheckYn;
	}

	public void setCompleteCheckYn(String completeCheckYn) {
		this.completeCheckYn = completeCheckYn;
	}

	public Date getCompleteCheckDate() {
		return completeCheckDate;
	}

	public void setCompleteCheckDate(Date completeCheckDate) {
		this.completeCheckDate = completeCheckDate;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public String getTempErpkey() {
		return tempErpkey;
	}

	public void setTempErpkey(String tempErpkey) {
		this.tempErpkey = tempErpkey;
	}
}
