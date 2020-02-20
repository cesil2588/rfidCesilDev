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

//생산 재고 RFID 테이블
//@ApiModel(description = "생산 재고 RFID 태그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionStorageRfidTag implements Serializable{

	private static final long serialVersionUID = 2046867578755154702L;

	//  생산 재고 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long productionStorageDetailSeq;

	// 생산 재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	private Long productionStorageSeq;
	
    //  ERP 키 + 태그 일련번호
	//@ApiModelProperty(notes = "RFID 바코드")
	@Column(nullable = false, length = 15)
    private String barcode;

    //  ERP 키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
    private String erpKey;

    //  RFID 태그 일련번호
	//@ApiModelProperty(notes = "RFID 태그 시리얼")
    @Column(nullable = true, length = 5)
    private String rfidSeq;

    //  RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩 정보")
    @Column(nullable = false, length = 32)
    private String rfidTag;

    //  상태
	//@ApiModelProperty(notes = "상태(1: 입고예정, 2: 재고, 3: 출고, 4: 재발행요청, 5: 폐기, 6: 반품검수, 7: 반품)")
    @Column(nullable = false, length = 1)
    private String stat;
    
    //  박스 일련번호
	//@ApiModelProperty(notes = "맵핑 박스 일련번호")
    @Column(nullable = true)
    private Long boxSeq;

    //  박스 바코드
	//@ApiModelProperty(notes = "맵핑 박스 바코드")
    @Column(nullable = true, length = 18)
    private String boxBarcode;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
    //  등록자
	//@ApiModelProperty(notes = "등록자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;

    //  수정자
	//@ApiModelProperty(notes = "수정자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	private UserInfo updUserInfo;
    
    // 수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;
    
    // 생산업체코드
	//@ApiModelProperty(notes = "생산업체코드")
    @Column(nullable = true, length = 3)
    private String customerCd;
    
	//@ApiModelProperty(notes = "임시 맵핑 박스 정보")
    @Transient
    private BoxInfo tempBoxInfo;
    
	public Long getProductionStorageDetailSeq() {
		return productionStorageDetailSeq;
	}

	public void setProductionStorageDetailSeq(Long productionStorageDetailSeq) {
		this.productionStorageDetailSeq = productionStorageDetailSeq;
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

	public Long getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Long boxSeq) {
		this.boxSeq = boxSeq;
	}

	public String getBoxBarcode() {
		return boxBarcode;
	}

	public void setBoxBarcode(String boxBarcode) {
		this.boxBarcode = boxBarcode;
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

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Long getProductionStorageSeq() {
		return productionStorageSeq;
	}

	public void setProductionStorageSeq(Long productionStorageSeq) {
		this.productionStorageSeq = productionStorageSeq;
	}

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public String getCustomerCd() {
		return customerCd;
	}

	public void setCustomerCd(String customerCd) {
		this.customerCd = customerCd;
	}

	public BoxInfo getTempBoxInfo() {
		return tempBoxInfo;
	}

	public void setTempBoxInfo(BoxInfo tempBoxInfo) {
		this.tempBoxInfo = tempBoxInfo;
	}
}
