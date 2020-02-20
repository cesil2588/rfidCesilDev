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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//물류 재고 테이블
//@ApiModel(description = "물류 재고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DistributionStorage implements Serializable{

	private static final long serialVersionUID = -256788773597967072L;

	// 물류재고 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long distributionStorageSeq;

	// 물류업체 일련번호
	//@ApiModelProperty(notes = "물류 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companySeq")
	private CompanyInfo distributionCompanyInfo;

	// 생산 재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	@OneToOne
	@JoinColumn(name="productionStorageSeq")
	private ProductionStorage productionStorage;

	// 물류업체 로케이션(더미컬럼)
	//@ApiModelProperty(notes = "로케이션 정보(현재 미사용)")
	@Column(nullable = true, length = 10)
	private String location;

	// 총 수량
	//@ApiModelProperty(notes = "총 수량")
	@ColumnDefault("0")
	private Long totalAmount;

	// 입고예정 수량
	//@ApiModelProperty(notes = "입고예정 수량")
	@Column(name="stat1_amount")
    private Long stat1Amount;

    // 재고 수량
	//@ApiModelProperty(notes = "재고 수량")
	@Column(name="stat2_amount")
    private Long stat2Amount;

    // 출고 수량
	//@ApiModelProperty(notes = "출고 수량")
	@Column(name="stat3_amount")
    private Long stat3Amount;

    // 재발행대기 수량
	//@ApiModelProperty(notes = "재발행 대기 수량")
	@Column(name="stat4_amount")
    private Long stat4Amount;

    // 재발행요청 수량
	//@ApiModelProperty(notes = "재발행 요청 수량")
	@Column(name="stat5_amount")
    private Long stat5Amount;

	// 반품
	//@ApiModelProperty(notes = "반품 수량")
	@Column(name="stat6_amount")
	private Long stat6Amount;

	// 폐기
	//@ApiModelProperty(notes = "폐기 수량")
	@Column(name="stat7_amount")
    private Long stat7Amount;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
    private UserInfo regUserInfo;

	// 등록일
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

	// 스타일
	// @ApiModelProperty(notes = "스타일")
	@Column(nullable = true, length = 20)
	private String style;

	// 제품연도
	// @ApiModelProperty(notes = "제품연도")
	@Column(nullable = true, length = 4)
	private String productYy;

	// 제품시즌
	// @ApiModelProperty(notes = "제품시즌")
	@Column(nullable = true, length = 5)
	private String productSeason;

	// 컬러
	// @ApiModelProperty(notes = "컬러")
	@Column(nullable = true, length = 10)
	private String color;

	// 사이즈
	// @ApiModelProperty(notes = "사이즈")
	@Column(nullable = true, length = 10)
	private String size;

	// 오더차수
	// @ApiModelProperty(notes = "오더차수")
	@Column(nullable = true, length = 3)
	private String orderDegree;

	// ERP RFID용 바택
	// @ApiModelProperty(notes = "ERP 키")
	@Column(nullable = true, length = 10)
	private String erpKey;

	// 추가 발주 차수
	// @ApiModelProperty(notes = "추가 요청 차수")
	@Column(nullable = true, length = 1)
	private String additionOrderDegree;

	public Long getDistributionStorageSeq() {
		return distributionStorageSeq;
	}

	public void setDistributionStorageSeq(Long distributionStorageSeq) {
		this.distributionStorageSeq = distributionStorageSeq;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
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

	public CompanyInfo getDistributionCompanyInfo() {
		return distributionCompanyInfo;
	}

	public void setDistributionCompanyInfo(CompanyInfo distributionCompanyInfo) {
		this.distributionCompanyInfo = distributionCompanyInfo;
	}

	public ProductionStorage getProductionStorage() {
		return productionStorage;
	}

	public void setProductionStorage(ProductionStorage productionStorage) {
		this.productionStorage = productionStorage;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getStat7Amount() {
		return stat7Amount;
	}

	public void setStat7Amount(Long stat7Amount) {
		this.stat7Amount = stat7Amount;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getProductYy() {
		return productYy;
	}

	public void setProductYy(String productYy) {
		this.productYy = productYy;
	}

	public String getProductSeason() {
		return productSeason;
	}

	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
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

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getAdditionOrderDegree() {
		return additionOrderDegree;
	}

	public void setAdditionOrderDegree(String additionOrderDegree) {
		this.additionOrderDegree = additionOrderDegree;
	}

	public DistributionStorage() {

	}

	public DistributionStorage(ProductionStorage productionStorage, CompanyInfo companyInfo, UserInfo userInfo) {
		this.productionStorage = productionStorage;
		this.distributionCompanyInfo = companyInfo;
		this.totalAmount = 0L;
		this.stat1Amount = 0L;
		this.stat2Amount = 0L;
		this.stat3Amount = 0L;
		this.stat4Amount = 0L;
		this.stat5Amount = 0L;
		this.stat6Amount = 0L;
		this.regDate = new Date();
		this.regUserInfo = userInfo;
		this.location = "0000";
		this.productYy = productionStorage.getProductYy();
		this.productSeason = productionStorage.getProductSeason();
		this.style = productionStorage.getStyle();
		this.color = productionStorage.getColor();
		this.size = productionStorage.getSize();
		this.orderDegree = productionStorage.getOrderDegree();
		this.additionOrderDegree = productionStorage.getAdditionOrderDegree();
	}

	public DistributionStorage(DistributionStorage distributionStorage) {
		this.totalAmount = distributionStorage.getTotalAmount();
		this.stat1Amount = distributionStorage.getStat1Amount();
		this.stat2Amount = distributionStorage.getStat2Amount();
		this.stat3Amount = distributionStorage.getStat3Amount();
		this.stat4Amount = distributionStorage.getStat4Amount();
		this.stat5Amount = distributionStorage.getStat5Amount();
		this.stat6Amount = distributionStorage.getStat6Amount();
		this.stat7Amount = distributionStorage.getStat7Amount();
	}


}