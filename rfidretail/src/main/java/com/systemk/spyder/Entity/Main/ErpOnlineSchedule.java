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

//@ApiModel(description = "물류 온라인 출고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpOnlineSchedule implements Serializable{

	private static final long serialVersionUID = -7870646806176932814L;
	
	// ERP 온라인 출고 예정 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long erpOnlineScheduleSeq;

	// 주문일자
	//@ApiModelProperty(notes = "주문일자")
	@Column(nullable = false, length = 8)
	private String orderDate;

	// 온라인 주문번호
	//@ApiModelProperty(notes = "온라인 주문번호")
	@Column(nullable = false, length = 30)
	private String orderNo;

	// 주문 시리얼
	//@ApiModelProperty(notes = "주문 시리얼")
	private Long orderSeq;
	
	// 주문 구분
	//@ApiModelProperty(notes = "주문 구분('': 일반주문, C: 교환주문, P: 단체주문)")
	@Column(nullable = true, length = 1)
	private String orderType;
	
	// 교환 순번
	//@ApiModelProperty(notes = "교환 순번")
	private Long exchangeSeq;
	
	// 매장 업체 정보
	//@ApiModelProperty(notes = "매장 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeCompanySeq")
	private CompanyInfo storeCompanyInfo;
	
	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	private String style;
	
	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
	private String color;
	
	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
	private String size;
	
	// 나머지 제품코드
	//@ApiModelProperty(notes = "컬러 + 사이즈")
	@Column(nullable = false, length = 20)
	private String annotherStyle;
	
	// 주문 수량
	//@ApiModelProperty(notes = "주문 수량")
	private Long orderAmount;
	
	// 판매 단가
	//@ApiModelProperty(notes = "판매 단가")
	private Long salePrice;
	
	// 온라인 사용 마일리지
	//@ApiModelProperty(notes = "온라인 사용 마일리지")
	private Long useMileage;
	
	// 온라인 쿠폰 사용금액
	//@ApiModelProperty(notes = "온라인 쿠폰 사용금액")
	private Long useCoupon;
	
	// 결제 금액
	//@ApiModelProperty(notes = "결제 금액")
	private Long totalPrice;
	
	// 확정 수량
	//@ApiModelProperty(notes = "확정 수량")
	private Long confirmAmount;
	
	// 마진율
	//@ApiModelProperty(notes = "마진율")
	private Long marginPercent;
	
	// 진행상태
	//@ApiModelProperty(notes = "진행상태(0: 미처리, 1: 재고요청, 2: 재고보충, 3: 반품, 4: 교환, 5: 주문취소)")
	@Column(nullable = false, length = 1)
	private String stat;
	
	// 재고보충형태
	//@ApiModelProperty(notes = "재고보충형태(1: 출고, 2: 매장간이동)")
	@Column(nullable = true, length = 1)
	private String stockAddType;
	
	// 고객명
	//@ApiModelProperty(notes = "고객명")
	@Column(nullable = false, length = 30)
	private String customerName;
	
	// 전화번호
	//@ApiModelProperty(notes = "전화번호")
	@Column(nullable = false, length = 20)
	private String phoneNo;
	
	// 핸드폰 전화번호
	//@ApiModelProperty(notes = "핸드폰 전화번호")
	@Column(nullable = false, length = 20)
	private String cellPhoneNo;
	
	// 우편번호
	//@ApiModelProperty(notes = "우편번호")
	@Column(nullable = false, length = 6)
	private String zipCode;
	
	// 주소1
	//@ApiModelProperty(notes = "주소1")
	@Column(nullable = false, length = 150)
	private String address1;
	
	// 주소2
	//@ApiModelProperty(notes = "주소2")
	@Column(nullable = false, length = 150)
	private String address2;
	
	// 배송업체
	//@ApiModelProperty(notes = "배송업체")
	@Column(nullable = true, length = 30)
	private String deliveryCompanyName;
	
	// 송장번호
	//@ApiModelProperty(notes = "송장번호")
	@Column(nullable = true, length = 30)
	private String deliveryNo;
	
	// 비고
	//@ApiModelProperty(notes = "비고")
	@Column(nullable = true, length = 150)
	private String explanatory;
	
	// 창고 업체 정보
	//@ApiModelProperty(notes = "창고업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "distributionCompanySeq")
	private CompanyInfo distributionCompanyInfo;
	
	// 출고 일자
	//@ApiModelProperty(notes = "출고 일자")
	@Column(nullable = true, length = 8)
	private String releaseDate;
	
	// 출고 번호
	//@ApiModelProperty(notes = "출고 번호")
	private Long releaseNo;
	
	// 출고 시리얼
	//@ApiModelProperty(notes = "출고 시리얼")
	private Long releaseSeq;
	
	// 미결발생일
	//@ApiModelProperty(notes = "미결 발생일")
	@Column(nullable = true, length = 8)
	private String arrearageDate;
	
	// from 매장
	//@ApiModelProperty(notes = "출발 매장 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startCompanySeq")
	private CompanyInfo startCompanyInfo;
	
	// 미결번호
	//@ApiModelProperty(notes = "미결번호")
	private Long arrearageNo;
	
	// 미결시리얼
	//@ApiModelProperty(notes = "시리얼")
	private Long arrearageSeq;
	
	// 판매일자
	//@ApiModelProperty(notes = "판매 일자")
	@Column(nullable = true, length = 8)
	private String saleDate;
	
	// 영수번호
	//@ApiModelProperty(notes = "영수 번호")
	private Long receiptNo;
	
	// 주문취소사유
	//@ApiModelProperty(notes = "주문 취소 사유")
	@Column(nullable = true, length = 50)
	private String cancelReason;
	
	// 재고보충상태(출고)
	//@ApiModelProperty(notes = "재고 보충 상태(1: 보충, 2: 재고부족) - 출고")
	@Column(nullable = true, length = 1)
	private String releaseStockAddType;
	
	// 재고보충처리일(출고)
	//@ApiModelProperty(notes = "재고 보충 처리일 - 출고")
	@Column(nullable = true, length = 8)
	private String releaseStockAddDate;
	
	// 재고보충처리시간(출고)
	//@ApiModelProperty(notes = "재고 보충 처리시간 - 출고")
	@Column(nullable = true, length = 6)
	private String releaseStockAddTime;
	
	// 재고보충처리자(출고)
	//@ApiModelProperty(notes = "재고 보충 처리자 - 출고")
	@Column(nullable = true, length = 10)
	private String releaseStockAddUserId;
	
	// 재고보충상태(이동)
	//@ApiModelProperty(notes = "재고 보충 상태(1: 보충, 2: 재고부족) - 이동")
	@Column(nullable = true, length = 1)
	private String moveStockAddType;

	// 재고보충처리일(이동)
	//@ApiModelProperty(notes = "재고 보충 처리일 - 이동")
	@Column(nullable = true, length = 8)
	private String moveStockAddDate;

	// 재고보충처리시간(이동)
	//@ApiModelProperty(notes = "재고 보충 처리시간 - 이동")
	@Column(nullable = true, length = 6)
	private String moveStockAddTime;

	// 재고보충처리자(이동)
	//@ApiModelProperty(notes = "재고 보충 처리자 - 이동")
	@Column(nullable = true, length = 10)
	private String moveStockAddUserId;
	
	// 요청횟수
	//@ApiModelProperty(notes = "요청 횟수")
	private Long requestCount;
	
	// 요청일자
	//@ApiModelProperty(notes = "요청 일자")
	@Column(nullable = true, length = 8)
	private String requestDate;
	
	// 요청시퀀스
	//@ApiModelProperty(notes = "요청 시퀀스")
	private Long requestNo;
	
	// 요청시리얼
	//@ApiModelProperty(notes = "요청 시리얼")
	private Long requestSeq;
	
	// WMS 요청여부
	//@ApiModelProperty(notes = "WMS 요청 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String requestWmsYn;
	
	// 사유 구분
	//@ApiModelProperty(notes = "사유 구분('': 해당없음, 1: A/S, 2: 단순변심 (교환등록시 신규주문번호에)")
	@Column(nullable = true, length = 1)
	private String reasonType;
	
	// 교환 사유
	//@ApiModelProperty(notes = "교환 사유")
	@Column(nullable = true, length = 50)
	private String exchangeReason;
	
	// 등록구분
	//@ApiModelProperty(notes = "등록 구분")
	@Column(nullable = false, length = 1)
	private String regType;
	
	// 원주문일자
	//@ApiModelProperty(notes = "원 주문일자")
	@Column(nullable = true, length = 8)
	private String oriOrderDate;
	
	// 원주물번호
	//@ApiModelProperty(notes = "원 주문번호")
	@Column(nullable = true, length = 30)
	private String oriOrderNo;
	
	// 원주문시리얼
	//@ApiModelProperty(notes = "원 주문 시리얼")
	private Long oriOrderSeq;
	
	// 등록일
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@Column(nullable = true, length = 10)
	private String regUserId;
	
	// 수정일
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@Column(nullable = true, length = 10)
	private String updUserId;

	public Long getErpOnlineScheduleSeq() {
		return erpOnlineScheduleSeq;
	}

	public void setErpOnlineScheduleSeq(Long erpOnlineScheduleSeq) {
		this.erpOnlineScheduleSeq = erpOnlineScheduleSeq;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getExchangeSeq() {
		return exchangeSeq;
	}

	public void setExchangeSeq(Long exchangeSeq) {
		this.exchangeSeq = exchangeSeq;
	}

	public CompanyInfo getStoreCompanyInfo() {
		return storeCompanyInfo;
	}

	public void setStoreCompanyInfo(CompanyInfo storeCompanyInfo) {
		this.storeCompanyInfo = storeCompanyInfo;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public String getAnnotherStyle() {
		return annotherStyle;
	}

	public void setAnnotherStyle(String annotherStyle) {
		this.annotherStyle = annotherStyle;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public Long getUseMileage() {
		return useMileage;
	}

	public void setUseMileage(Long useMileage) {
		this.useMileage = useMileage;
	}

	public Long getUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(Long useCoupon) {
		this.useCoupon = useCoupon;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getConfirmAmount() {
		return confirmAmount;
	}

	public void setConfirmAmount(Long confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	public Long getMarginPercent() {
		return marginPercent;
	}

	public void setMarginPercent(Long marginPercent) {
		this.marginPercent = marginPercent;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getStockAddType() {
		return stockAddType;
	}

	public void setStockAddType(String stockAddType) {
		this.stockAddType = stockAddType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCellPhoneNo() {
		return cellPhoneNo;
	}

	public void setCellPhoneNo(String cellPhoneNo) {
		this.cellPhoneNo = cellPhoneNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public CompanyInfo getDistributionCompanyInfo() {
		return distributionCompanyInfo;
	}

	public void setDistributionCompanyInfo(CompanyInfo distributionCompanyInfo) {
		this.distributionCompanyInfo = distributionCompanyInfo;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getReleaseNo() {
		return releaseNo;
	}

	public void setReleaseNo(Long releaseNo) {
		this.releaseNo = releaseNo;
	}

	public Long getReleaseSeq() {
		return releaseSeq;
	}

	public void setReleaseSeq(Long releaseSeq) {
		this.releaseSeq = releaseSeq;
	}

	public String getArrearageDate() {
		return arrearageDate;
	}

	public void setArrearageDate(String arrearageDate) {
		this.arrearageDate = arrearageDate;
	}

	public CompanyInfo getStartCompanyInfo() {
		return startCompanyInfo;
	}

	public void setStartCompanyInfo(CompanyInfo startCompanyInfo) {
		this.startCompanyInfo = startCompanyInfo;
	}

	public Long getArrearageNo() {
		return arrearageNo;
	}

	public void setArrearageNo(Long arrearageNo) {
		this.arrearageNo = arrearageNo;
	}

	public Long getArrearageSeq() {
		return arrearageSeq;
	}

	public void setArrearageSeq(Long arrearageSeq) {
		this.arrearageSeq = arrearageSeq;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public Long getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getReleaseStockAddType() {
		return releaseStockAddType;
	}

	public void setReleaseStockAddType(String releaseStockAddType) {
		this.releaseStockAddType = releaseStockAddType;
	}

	public String getReleaseStockAddDate() {
		return releaseStockAddDate;
	}

	public void setReleaseStockAddDate(String releaseStockAddDate) {
		this.releaseStockAddDate = releaseStockAddDate;
	}

	public String getReleaseStockAddTime() {
		return releaseStockAddTime;
	}

	public void setReleaseStockAddTime(String releaseStockAddTime) {
		this.releaseStockAddTime = releaseStockAddTime;
	}

	public String getReleaseStockAddUserId() {
		return releaseStockAddUserId;
	}

	public void setReleaseStockAddUserId(String releaseStockAddUserId) {
		this.releaseStockAddUserId = releaseStockAddUserId;
	}

	public String getMoveStockAddType() {
		return moveStockAddType;
	}

	public void setMoveStockAddType(String moveStockAddType) {
		this.moveStockAddType = moveStockAddType;
	}

	public String getMoveStockAddDate() {
		return moveStockAddDate;
	}

	public void setMoveStockAddDate(String moveStockAddDate) {
		this.moveStockAddDate = moveStockAddDate;
	}

	public String getMoveStockAddTime() {
		return moveStockAddTime;
	}

	public void setMoveStockAddTime(String moveStockAddTime) {
		this.moveStockAddTime = moveStockAddTime;
	}

	public String getMoveStockAddUserId() {
		return moveStockAddUserId;
	}

	public void setMoveStockAddUserId(String moveStockAddUserId) {
		this.moveStockAddUserId = moveStockAddUserId;
	}

	public Long getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public Long getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(Long requestNo) {
		this.requestNo = requestNo;
	}

	public Long getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(Long requestSeq) {
		this.requestSeq = requestSeq;
	}

	public String getRequestWmsYn() {
		return requestWmsYn;
	}

	public void setRequestWmsYn(String requestWmsYn) {
		this.requestWmsYn = requestWmsYn;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getOriOrderDate() {
		return oriOrderDate;
	}

	public void setOriOrderDate(String oriOrderDate) {
		this.oriOrderDate = oriOrderDate;
	}

	public String getOriOrderNo() {
		return oriOrderNo;
	}

	public void setOriOrderNo(String oriOrderNo) {
		this.oriOrderNo = oriOrderNo;
	}

	public Long getOriOrderSeq() {
		return oriOrderSeq;
	}

	public void setOriOrderSeq(Long oriOrderSeq) {
		this.oriOrderSeq = oriOrderSeq;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
}
