package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//@ApiModel(description = "물류입고예정 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StorageScheduleLog implements Serializable{

	private static final long serialVersionUID = -6653913931827699481L;

	// 물류입고예정 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long storageScheduleLogSeq;
	
	// WMS 입고 코드
	//@ApiModelProperty(notes = "WMS 입고 코드(default: R2455)")
	@Column(nullable = true, length = 7)
	@JsonView({View.Public.class})
	private String openDbCode;
	
	// WMS 입고 타입
	//@ApiModelProperty(notes = "WMS 입고 타입(OR-R: 생산업체 입고, OR-R2: 해외업체 입고, 10-R: 매장 반품)")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class})
	private String orderType;
	
	// 도착예정코드
	//@ApiModelProperty(notes = "도착예정 코드(2930: 입고, 2935: 반품)")
	@Column(nullable = false, length = 15)
	@JsonView({View.Public.class})
	private String arrivalCode;
	
	// 생성일
	//@ApiModelProperty(notes = "생성일자")
	@Column(nullable = false, length = 8)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String createDate;
	
	// 작업 라인
	//@ApiModelProperty(notes = "작업라인")
	@JsonView({View.Public.class})
	private Long workLine;
	
	// 박스 일련번호
	//@ApiModelProperty(notes = "박스 일련번호")
	@JsonIgnoreProperties({"regUserInfo", "updUserInfo"})
	@OneToOne
	@JoinColumn(name="boxSeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private BoxInfo boxInfo;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@JsonView({View.Public.class})
	private Date regDate;
	
	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	@JsonView({View.Public.class})
	private UserInfo regUserInfo;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@JsonView({View.Public.class})
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	@JsonView({View.Public.class})
	private UserInfo updUserInfo;
	
	// 세부 스타일 정보
	//@ApiModelProperty(notes = "물류입고예정 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "storageScheduleLogSeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Set<StorageScheduleDetailLog> storageScheduleDetailLog;
	
	// 확정 여부
	//@ApiModelProperty(notes = "확정 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String confirmYn;
	
	// 입고 완료 여부
	//@ApiModelProperty(notes = "입고 완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String completeYn;
	
	// 확정일
	//@ApiModelProperty(notes = "확정일")
	@JsonView({View.Public.class})
	private Date confirmDate;
	
	// 입고 완료일
	//@ApiModelProperty(notes = "입고 완료일")
	@JsonView({View.Public.class})
	private Date completeDate;
	
	// 인보이스 번호
	//@ApiModelProperty(notes = "인보이스 번호")
	@Column(nullable = true, length = 50)
	@JsonView({View.Public.class})
	private String invoiceNum;
	
	// 폐기 여부(상품 수정시)
	//@ApiModelProperty(notes = "폐기 여부")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String disuseYn;
	
	// 반품 구분
	//@ApiModelProperty(notes = "반품 구분(10:일반반품, 15:이동반품, 20:비용반품, 40:불량반품, 90:계절반품, 80:일반반품대기)")
	@Column(nullable = true, length = 2)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String returnType;
	
	// 입고예정 배치 작업 여부
	//@ApiModelProperty(notes = "입고예정 배치 작업 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String batchYn;
	
	// 입고예정 배치 작업 날짜
	//@ApiModelProperty(notes = "입고예정 배치 작업일")
	@JsonView({View.Public.class})
	private Date batchDate;
	
	// 입고 확정 배치 작업 여부
	//@ApiModelProperty(notes = "입고확정 배치 작업 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String completeBatchYn;
	
	// 입고 확정 배치 작업 날짜
	//@ApiModelProperty(notes = "입고확정 배치 작업일")
	@JsonView({View.Public.class})
	private Date completeBatchDate;
	
	// ERP 입고 예정 전달 여부
	//@ApiModelProperty(notes = "ERP 입고예정 전달 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String erpScheduleYn;
	
	// ERP 입고 예정 전달 날짜
	//@ApiModelProperty(notes = "ERP 입고예정 전달일")
	@JsonView({View.Public.class})
	private Date erpScheduleDate;
	
	// ERP 입고 실적 전달 여부
	//@ApiModelProperty(notes = "ERP 입고실적 전달 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String erpCompleteYn;
	
	// ERP 입고 실적 전달 날짜
	//@ApiModelProperty(notes = "ERP 입고실적 전달일")
	@JsonView({View.Public.class})
	private Date erpCompleteDate;
	
	// OPEN DB 입고예정정보 전달 여부
	//@ApiModelProperty(notes = "WMS 입고예정 전달 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String openDbScheduleYn;
		
	// OPEN DB 입고예정정보 전달 날짜
	//@ApiModelProperty(notes = "WMS 입고예정 전달일")
	@JsonView({View.Public.class})
	private Date openDbScheduleDate;
	
	// WMS 입고실적 전달 여부
	//@ApiModelProperty(notes = "WMS 입고실적 전달 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String wmsCompleteYn;
			
	// WMS 입고실적 전달 날짜
	//@ApiModelProperty(notes = "WMS 입고실적 전달일")
	@JsonView({View.Public.class})
	private Date wmsCompleteDate;
	
	// 입고 예정일
	//@ApiModelProperty(notes = "입고 예정일")
	@Column(nullable = true, length = 8)
	@JsonView({View.Public.class})
    private String arrivalDate;
	
	// 입고 반송 여부
	//@ApiModelProperty(notes = "입고 반송 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String returnYn;
	
	// 입고 반송일
	//@ApiModelProperty(notes = "입고 반송일")
	private Date returnDate;
	
	// 임시 입고 예정일
	//@ApiModelProperty(notes = "임시 입고 예정일")
	@Transient
	private String tempArrivalDate;


	public Long getStorageScheduleLogSeq() {
		return storageScheduleLogSeq;
	}

	public void setStorageScheduleLogSeq(Long storageScheduleLogSeq) {
		this.storageScheduleLogSeq = storageScheduleLogSeq;
	}

	public String getOpenDbCode() {
		return openDbCode;
	}

	public void setOpenDbCode(String openDbCode) {
		this.openDbCode = openDbCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getArrivalCode() {
		return arrivalCode;
	}

	public void setArrivalCode(String arrivalCode) {
		this.arrivalCode = arrivalCode;
	}

	public BoxInfo getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
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

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Set<StorageScheduleDetailLog> getStorageScheduleDetailLog() {
		return storageScheduleDetailLog;
	}

	public void setStorageScheduleDetailLog(Set<StorageScheduleDetailLog> storageScheduleDetailLog) {
		this.storageScheduleDetailLog = storageScheduleDetailLog;
	}

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public String getCompleteYn() {
		return completeYn;
	} 

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getWorkLine() {
		return workLine;
	}

	public void setWorkLine(Long workLine) {
		this.workLine = workLine;
	}

	public String getDisuseYn() {
		return disuseYn;
	}

	public void setDisuseYn(String disuseYn) {
		this.disuseYn = disuseYn;
	}
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
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

	@Override
	public String toString() {
		return "StorageScheduleLog [storageScheduleLogSeq=" + storageScheduleLogSeq + ", openDbCode=" + openDbCode
				+ ", orderType=" + orderType + ", arrivalCode=" + arrivalCode + ", createDate=" + createDate
				+ ", workLine=" + workLine + ", boxInfo=" + boxInfo + ", regDate=" + regDate + ", regUserInfo="
				+ regUserInfo + ", updDate=" + updDate + ", updUserInfo=" + updUserInfo + ", storageScheduleDetailLog="
				+ storageScheduleDetailLog + ", confirmYn=" + confirmYn + ", completeYn=" + completeYn
				+ ", confirmDate=" + confirmDate + ", completeDate=" + completeDate + ", invoiceNum=" + invoiceNum
				+ "]";
	}

	public String getCompleteBatchYn() {
		return completeBatchYn;
	}

	public void setCompleteBatchYn(String completeBatchYn) {
		this.completeBatchYn = completeBatchYn;
	}

	public Date getCompleteBatchDate() {
		return completeBatchDate;
	}

	public void setCompleteBatchDate(Date completeBatchDate) {
		this.completeBatchDate = completeBatchDate;
	}

	public String getErpScheduleYn() {
		return erpScheduleYn;
	}

	public void setErpScheduleYn(String erpScheduleYn) {
		this.erpScheduleYn = erpScheduleYn;
	}

	public Date getErpScheduleDate() {
		return erpScheduleDate;
	}

	public void setErpScheduleDate(Date erpScheduleDate) {
		this.erpScheduleDate = erpScheduleDate;
	}

	public String getErpCompleteYn() {
		return erpCompleteYn;
	}

	public void setErpCompleteYn(String erpCompleteYn) {
		this.erpCompleteYn = erpCompleteYn;
	}

	public String getOpenDbScheduleYn() {
		return openDbScheduleYn;
	}

	public void setOpenDbScheduleYn(String openDbScheduleYn) {
		this.openDbScheduleYn = openDbScheduleYn;
	}

	public Date getErpCompleteDate() {
		return erpCompleteDate;
	}

	public void setErpCompleteDate(Date erpCompleteDate) {
		this.erpCompleteDate = erpCompleteDate;
	}

	public Date getOpenDbScheduleDate() {
		return openDbScheduleDate;
	}

	public void setOpenDbScheduleDate(Date openDbScheduleDate) {
		this.openDbScheduleDate = openDbScheduleDate;
	}

	public String getWmsCompleteYn() {
		return wmsCompleteYn;
	}

	public void setWmsCompleteYn(String wmsCompleteYn) {
		this.wmsCompleteYn = wmsCompleteYn;
	}

	public Date getWmsCompleteDate() {
		return wmsCompleteDate;
	}

	public void setWmsCompleteDate(Date wmsCompleteDate) {
		this.wmsCompleteDate = wmsCompleteDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getTempArrivalDate() {
		return tempArrivalDate;
	}

	public void setTempArrivalDate(String tempArrivalDate) {
		this.tempArrivalDate = tempArrivalDate;
	}

	public String getReturnYn() {
		return returnYn;
	}

	public void setReturnYn(String returnYn) {
		this.returnYn = returnYn;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	
}
