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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "바택 발행 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BartagMaster implements Serializable {

	private static final long serialVersionUID = -4792403901190702002L;

	// 바택 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bartagSeq;

	// 발행일자
	//@ApiModelProperty(notes = "발행일자")
	@Column(nullable = false, length = 8)
	private String createDate;

	// 일련번호
	//@ApiModelProperty(notes = "발행 일련번호")
	private Long seq;

	// 라인번호
	//@ApiModelProperty(notes = "라인번호")
	private Long lineSeq;
	
	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	private String style;

	// 제품연도
	//@ApiModelProperty(notes = "제품연도")
	@Column(nullable = false, length = 4)
	private String productYy;

	// 제품시즌
	//@ApiModelProperty(notes = "제품시즌")
	@Column(nullable = false, length = 5)
	private String productSeason;
	
	// RFID 시즌 코드
	//@ApiModelProperty(notes = "RFID 시즌 코드")
	@Column(nullable = false, length = 3)
	private String productRfidYySeason;

	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
	private String color;

	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
	private String size;

	// 오더차수
	//@ApiModelProperty(notes = "오더차수")
	@Column(nullable = false, length = 3)
	private String orderDegree;

	// 나머지제품코드
	//@ApiModelProperty(notes = "스타일 + 컬러")
	@Column(nullable = false, length = 20)
	private String annotherStyle;

	// 발행수량
	//@ApiModelProperty(notes = "발행 수량")
	private Long amount;
	
	// 총수량
	//@ApiModelProperty(notes = "총 수량")
	private Long totalAmount;
	
	// RFID 발행 시작
	//@ApiModelProperty(notes = "RFID 발행 시작 일련번호")
	@Column(nullable = true, length = 5)
	private String startRfidSeq;
	
	// RFID 발행 종료
	//@ApiModelProperty(notes = "RFID 발행 종료 일련번호")
	@Column(nullable = true, length = 5)
	private String endRfidSeq;
	
	// 태그발행 업체
	//@ApiModelProperty(notes = "RFID 태그 발행업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="publishCompanySeq")
	private CompanyInfo publishCompanyInfo;
	
	// 생산업체
	//@ApiModelProperty(notes = "생산업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productionCompanySeq")
	private CompanyInfo productionCompanyInfo;
	
	// 세부생산업체
	//@ApiModelProperty(notes = "생산업체 상세")
	@Column(nullable = true, length = 20)
	private String detailProductionCompanyName;

	// ERP RFID용 바택
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;

	// 바택정보
	//@ApiModelProperty(notes = "바택정보")
	@Column(nullable = false, length = 100)
	private String data;

	// 변경상태(0: 생산요청 입력, 1: 초기생성, 2: 발행 준비, 3: 발행 시작, 4: 발행 중, 5: 발행 종료, 6: 종결, 7: 폐기)
	//@ApiModelProperty(notes = "변경상태 (0: 생산요청 입력, 1: 초기생성, 2: 발행 준비, 3: 발행 시작, 4: 발행 중, 5: 발행 종료, 6: 종결, 7: 폐기)")
	@Column(nullable = false, length = 1)
	private String stat;

	// 전송여부
	//@ApiModelProperty(notes = "전송 여부")
	@Column(nullable = false, length = 1)
	private String result;

	// 등록일시
	//@ApiModelProperty(notes = "등록일")
	private Date regDate;
	
	// 바택발행시작
	//@ApiModelProperty(notes = "바택발행 시작일")
	@Column(nullable = true)
	private Date bartagStartDate;
	
	// 바택발행종료
	//@ApiModelProperty(notes = "바택발행 종료일")
	@Column(nullable = true)
	private Date bartagEndDate;
	
	// 태그 시리얼 생성 여부
	//@ApiModelProperty(notes = "RFID 태그 시리얼 생성 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String generateSeqYn;
	
	// 재발행 요청 여부
	//@ApiModelProperty(notes = "재발행 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String reissueRequestYn;
	
	// 수정일시
	//@ApiModelProperty(notes = "수정일")
	private Date updDate;
	
	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="updUserSeq")
 	private UserInfo updUserInfo;

	// 생산재고
	//@ApiModelProperty(notes = "생산재고 일련번호")
	@JsonIgnoreProperties("bartagMaster")
	@OneToOne
	@JoinColumn(name="productionStorageSeq")
	private ProductionStorage productionStorage;
	
	// 추가 발주 차수
	//@ApiModelProperty(notes = "추가 요청 차수")
	@Column(nullable = true, length = 1)
	private String additionOrderDegree;
	
	// ERP or RFID 시작 여부
	//@ApiModelProperty(notes = "ERP & RFID 시작 여부(E: ERP, R: RFID)")
	@Column(nullable = true, length = 1)
	private String startFlag;
	
    //  미발행 수량
	//@ApiModelProperty(notes = "미발행 수량")
	@Column(name="stat1_amount")
    private Long stat1Amount;

    //  발행대기 수량
	//@ApiModelProperty(notes = "발행대기 수량")
	@Column(name="stat2_amount")
    private Long stat2Amount;

    //  발행완료 수량
	//@ApiModelProperty(notes = "발행완료 수량")
	@Column(name="stat3_amount")
    private Long stat3Amount;

    //  재발행대기 수량
	//@ApiModelProperty(notes = "재발행대기 수량")
	@Column(name="stat4_amount")
    private Long stat4Amount;

    //  재발행완료 수량
	//@ApiModelProperty(notes = "재발행완료 수량")
	@Column(name="stat5_amount")
    private Long stat5Amount;

    //  재발행요청 수량
	//@ApiModelProperty(notes = "재발행요청 수량")
	@Column(name="stat6_amount")
    private Long stat6Amount;

    //  폐기 수량
	//@ApiModelProperty(notes = "폐기 수량")
	@Column(name="stat7_amount")
    private Long stat7Amount;
	
	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Date getBartagStartDate() {
		return bartagStartDate;
	}

	public void setBartagStartDate(Date bartagStartDate) {
		this.bartagStartDate = bartagStartDate;
	}

	public Date getBartagEndDate() {
		return bartagEndDate;
	}

	public void setBartagEndDate(Date bartagEndDate) {
		this.bartagEndDate = bartagEndDate;
	}

	public Long getBartagSeq() {
		return bartagSeq;
	}

	public void setBartagSeq(Long bartagSeq) {
		this.bartagSeq = bartagSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Long getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Long lineSeq) {
		this.lineSeq = lineSeq;
	}

	public String getProductSeason() {
		return productSeason;
	}

	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
	}

	public String getProductYy() {
		return productYy;
	}

	public void setProductYy(String productYy) {
		this.productYy = productYy;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getAnnotherStyle() {
		return annotherStyle;
	}

	public void setAnnotherStyle(String annotherStyle) {
		this.annotherStyle = annotherStyle;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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

	public String getGenerateSeqYn() {
		return generateSeqYn;
	}

	public void setGenerateSeqYn(String generateSeqYn) {
		this.generateSeqYn = generateSeqYn;
	}

	public String getReissueRequestYn() {
		return reissueRequestYn;
	}

	public void setReissueRequestYn(String reissueRequestYn) {
		this.reissueRequestYn = reissueRequestYn;
	}
	

	public String getProductRfidYySeason() {
		return productRfidYySeason;
	}

	public void setProductRfidYySeason(String productRfidYySeason) {
		this.productRfidYySeason = productRfidYySeason;
	}
	
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStartRfidSeq() {
		return startRfidSeq;
	}

	public void setStartRfidSeq(String startRfidSeq) {
		this.startRfidSeq = startRfidSeq;
	}

	public String getEndRfidSeq() {
		return endRfidSeq;
	}

	public void setEndRfidSeq(String endRfidSeq) {
		this.endRfidSeq = endRfidSeq;
	}
	
	public ProductionStorage getProductionStorage() {
		return productionStorage;
	}

	public void setProductionStorage(ProductionStorage productionStorage) {
		this.productionStorage = productionStorage;
	}

	public CompanyInfo getPublishCompanyInfo() {
		return publishCompanyInfo;
	}

	public void setPublishCompanyInfo(CompanyInfo publishCompanyInfo) {
		this.publishCompanyInfo = publishCompanyInfo;
	}

	public CompanyInfo getProductionCompanyInfo() {
		return productionCompanyInfo;
	}

	public void setProductionCompanyInfo(CompanyInfo productionCompanyInfo) {
		this.productionCompanyInfo = productionCompanyInfo;
	}

	public String getAdditionOrderDegree() {
		return additionOrderDegree;
	}

	public void setAdditionOrderDegree(String additionOrderDegree) {
		this.additionOrderDegree = additionOrderDegree;
	}

	public Long getStat1Amount() {
		return stat1Amount;
	}

	public void setStat1Amount(Long stat1Amount) {
		this.stat1Amount = stat1Amount;
	}

	public Long getStat2Amount() {
		return stat2Amount;
	}

	public void setStat2Amount(Long stat2Amount) {
		this.stat2Amount = stat2Amount;
	}

	public Long getStat3Amount() {
		return stat3Amount;
	}

	public void setStat3Amount(Long stat3Amount) {
		this.stat3Amount = stat3Amount;
	}

	public Long getStat4Amount() {
		return stat4Amount;
	}

	public void setStat4Amount(Long stat4Amount) {
		this.stat4Amount = stat4Amount;
	}

	public Long getStat5Amount() {
		return stat5Amount;
	}

	public void setStat5Amount(Long stat5Amount) {
		this.stat5Amount = stat5Amount;
	}

	public Long getStat6Amount() {
		return stat6Amount;
	}

	public void setStat6Amount(Long stat6Amount) {
		this.stat6Amount = stat6Amount;
	}

	public Long getStat7Amount() {
		return stat7Amount;
	}

	public void setStat7Amount(Long stat7Amount) {
		this.stat7Amount = stat7Amount;
	}

	public String getDetailProductionCompanyName() {
		return detailProductionCompanyName;
	}

	public void setDetailProductionCompanyName(String detailProductionCompanyName) {
		this.detailProductionCompanyName = detailProductionCompanyName;
	}
	
	public String getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}

	@Override
	public String toString() {
		return "BartagMaster [bartagSeq=" + bartagSeq + ", createDate=" + createDate + ", seq=" + seq + ", lineSeq="
				+ lineSeq + ", style=" + style + ", productYy=" + productYy + ", productSeason=" + productSeason
				+ ", productRfidYySeason=" + productRfidYySeason + ", color=" + color + ", size=" + size
				+ ", orderDegree=" + orderDegree + ", annotherStyle=" + annotherStyle + ", amount=" + amount
				+ ", totalAmount=" + totalAmount + ", startRfidSeq=" + startRfidSeq + ", endRfidSeq=" + endRfidSeq
				+ ", publishCompanyInfo=" + publishCompanyInfo + ", productionCompanyInfo=" + productionCompanyInfo
				+ ", detailProductionCompanyName=" + detailProductionCompanyName + ", erpKey=" + erpKey + ", data="
				+ data + ", stat=" + stat + ", result=" + result + ", regDate=" + regDate + ", bartagStartDate="
				+ bartagStartDate + ", bartagEndDate=" + bartagEndDate + ", generateSeqYn=" + generateSeqYn
				+ ", reissueRequestYn=" + reissueRequestYn + ", updDate=" + updDate + ", updUserInfo=" + updUserInfo
				+ ", productionStorage=" + productionStorage + ", additionOrderDegree=" + additionOrderDegree
				+ ", stat1Amount=" + stat1Amount + ", stat2Amount=" + stat2Amount + ", stat3Amount=" + stat3Amount
				+ ", stat4Amount=" + stat4Amount + ", stat5Amount=" + stat5Amount + ", stat6Amount=" + stat6Amount
				+ ", stat7Amount=" + stat7Amount + "]";
	}
	
}
