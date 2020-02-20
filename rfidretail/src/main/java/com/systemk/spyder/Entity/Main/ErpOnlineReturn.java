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

//@ApiModel(description = "물류 온라인 반품 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErpOnlineReturn implements Serializable{

	private static final long serialVersionUID = -7870646806176932814L;
	
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long erpOnlineReturnSeq;
	
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
	
	// 처리 구분
	//@ApiModelProperty(notes = "처리 구분(1: 창고반품, 2: 창고교환, 3: 매장반품, 4: 매장교환 (1,2만사용))")
	@Column(nullable = false, length = 1)
	private String returnType;
	
	// 상태
	//@ApiModelProperty(notes = "상태(0: 미처리, 1: 반품요청, 2: 반품확정)")
	@Column(nullable = false, length = 1)
	private String stat;
	
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
	
	// 반품 수량
	//@ApiModelProperty(notes = "반품 수량")
	private Long returnAmount;
	
	// 판매 단가
	//@ApiModelProperty(notes = "판매 단가")
	private Long salePrice;
	
	// 온라인 사용 마일리지
	//@ApiModelProperty(notes = "온라인 사용 마일리지")
	private Long useMileage;

	// 온라인 쿠폰 사용금액
	//@ApiModelProperty(notes = "온라인 쿠폰 사용 금액")
	private Long useCoupon;

	// 결제 금액
	//@ApiModelProperty(notes = "결제 금액")
	private Long totalPrice;

	// 확정 수량
	//@ApiModelProperty(notes = "확정 수량")
	private Long confirmAmount;
	
	// 고객명
	//@ApiModelProperty(notes = "고객 명")
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
	
	// 반품일자
	//@ApiModelProperty(notes = "반품일자")
	@Column(nullable = false, length = 8)
	private String returnDate;
	
	// 반품번호
	//@ApiModelProperty(notes = "반품번호")
	private Long returnNo;
	
	// 반품사유
	//@ApiModelProperty(notes = "반품사유")
	@Column(nullable = true, length = 150)
	private String returnReason;
	
	// 배송업체
	//@ApiModelProperty(notes = "배송업체")
	@Column(nullable = true, length = 30)
	private String deliveryCompanyName;

	// 송장번호
	//@ApiModelProperty(notes = "송장번호")
	@Column(nullable = true, length = 30)
	private String deliveryNo;
	
	// 환입일
	//@ApiModelProperty(notes = "환입일")
	@Column(nullable = false, length = 8)
	private String purchaseReturnDate;
	
	// 환입영수번호
	//@ApiModelProperty(notes = "환입영수번호")
	private Long purchaseReturnReceiptNo;
	
	// 출고일
	//@ApiModelProperty(notes = "출고일")
	@Column(nullable = true, length = 8)
	private String releaseDate;
	
	// 출고송장번호
	//@ApiModelProperty(notes = "출고송장번호")
	@Column(nullable = true, length = 30)
	private String releaseDeliveryNo;
	
	// 출고배송업체
	//@ApiModelProperty(notes = "출고배송업체")
	@Column(nullable = true, length = 30)
	private String releaseDeliveryCompanyName;
	
	// 비고
	//@ApiModelProperty(notes = "비고")
	@Column(nullable = true, length = 100)
	private String explanatory;
	
	// 반품 창고업체
	//@ApiModelProperty(notes = "반품 창고 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "distributionCompanySeq")
	private CompanyInfo distributionCompanyInfo;
	
	// 반품 매장업체
	//@ApiModelProperty(notes = "반품 매장 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeCompanySeq")
	private CompanyInfo storeCompanyInfo;
	
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
	
	// 등록 구분
	//@ApiModelProperty(notes = "등록 구분(A: 자동, M: 수동)")
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
	//@ApiModelProperty(notes = "원 주문시리얼")
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

	public Long getErpOnlineReturnSeq() {
		return erpOnlineReturnSeq;
	}

	public void setErpOnlineReturnSeq(Long erpOnlineReturnSeq) {
		this.erpOnlineReturnSeq = erpOnlineReturnSeq;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Long getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(Long returnNo) {
		this.returnNo = returnNo;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
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

	public String getPurchaseReturnDate() {
		return purchaseReturnDate;
	}

	public void setPurchaseReturnDate(String purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseDeliveryNo() {
		return releaseDeliveryNo;
	}

	public void setReleaseDeliveryNo(String releaseDeliveryNo) {
		this.releaseDeliveryNo = releaseDeliveryNo;
	}

	public String getReleaseDeliveryCompanyName() {
		return releaseDeliveryCompanyName;
	}

	public void setReleaseDeliveryCompanyName(String releaseDeliveryCompanyName) {
		this.releaseDeliveryCompanyName = releaseDeliveryCompanyName;
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

	public CompanyInfo getStoreCompanyInfo() {
		return storeCompanyInfo;
	}

	public void setStoreCompanyInfo(CompanyInfo storeCompanyInfo) {
		this.storeCompanyInfo = storeCompanyInfo;
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

	public Long getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Long returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Long getPurchaseReturnReceiptNo() {
		return purchaseReturnReceiptNo;
	}

	public void setPurchaseReturnReceiptNo(Long purchaseReturnReceiptNo) {
		this.purchaseReturnReceiptNo = purchaseReturnReceiptNo;
	}
	
	
}
