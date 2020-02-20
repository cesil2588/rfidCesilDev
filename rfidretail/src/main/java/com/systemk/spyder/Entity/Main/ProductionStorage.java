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

//생산 재고 테이블
//@ApiModel(description = "생산 재고 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionStorage implements Serializable{

	private static final long serialVersionUID = -47314590773160340L;

	//  생산재고 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long productionStorageSeq;

	// 바택 정보
	//@ApiModelProperty(notes = "바택 발행 일련번호")
	@JsonIgnoreProperties(value = {"productionStorage", "publishCompanyInfo"}, allowSetters = true)
	@OneToOne
	@JoinColumn(name="bartagSeq")
	private BartagMaster bartagMaster;

	// 생산업체 일련번호
	//@ApiModelProperty(notes = "생산 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companySeq")
	private CompanyInfo companyInfo;

    //  총 수량
	//@ApiModelProperty(notes = "총 수량")
	@ColumnDefault("0")
    private Long totalAmount;

	//  미검수 수량
	//@ApiModelProperty(notes = "입고예정 수량")
	@ColumnDefault("0")
    private Long nonCheckAmount;

	//  재고 수량
	//@ApiModelProperty(notes = "재고 수량")
	@ColumnDefault("0")
    private Long stockAmount;

    //  출고 수량
	//@ApiModelProperty(notes = "출고 수량")
	@ColumnDefault("0")
    private Long releaseAmount;

    //  재발행 요청 수량
	//@ApiModelProperty(notes = "재발행 요청 수량")
	@ColumnDefault("0")
    private Long reissueAmount;

    //  폐기 수량
	//@ApiModelProperty(notes = "폐기 수량")
	@ColumnDefault("0")
    private Long disuseAmount;

    //  반품 미검수 수량
	//@ApiModelProperty(notes = "반품 미검수 수량")
	@ColumnDefault("0")
    private Long returnNonCheckAmount;

    //  반품 수량
	//@ApiModelProperty(notes = "반품 수량")
	@ColumnDefault("0")
    private Long returnAmount;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;

    // 등록자
	//@ApiModelProperty(notes = "등록자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
    private UserInfo regUserInfo;

    // 수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;

    // 수정자
	//@ApiModelProperty(notes = "수정자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
    private UserInfo updUserInfo;

    // 스타일
 	//@ApiModelProperty(notes = "스타일")
 	@Column(nullable = true, length = 20)
 	private String style;

 	// 제품연도
 	//@ApiModelProperty(notes = "제품연도")
 	@Column(nullable = true, length = 4)
 	private String productYy;

 	// 제품시즌
 	//@ApiModelProperty(notes = "제품시즌")
 	@Column(nullable = true, length = 5)
 	private String productSeason;

 	// 컬러
 	//@ApiModelProperty(notes = "컬러")
 	@Column(nullable = true, length = 10)
 	private String color;

 	// 사이즈
 	//@ApiModelProperty(notes = "사이즈")
 	@Column(nullable = true, length = 10)
 	private String size;

 	// 오더차수
 	//@ApiModelProperty(notes = "오더차수")
 	@Column(nullable = true, length = 3)
 	private String orderDegree;

 	// ERP RFID용 바택
 	//@ApiModelProperty(notes = "ERP 키")
 	@Column(nullable = true, length = 10)
 	private String erpKey;

 	// 추가 발주 차수
 	//@ApiModelProperty(notes = "추가 요청 차수")
 	@Column(nullable = true, length = 1)
 	private String additionOrderDegree;

    public Long getProductionStorageSeq() {
        return productionStorageSeq;
    }

    public void setProductionStorageSeq(Long productionStorageSeq) {
        this.productionStorageSeq = productionStorageSeq;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getReleaseAmount() {
        return releaseAmount;
    }

    public void setReleaseAmount(Long releaseAmount) {
        this.releaseAmount = releaseAmount;
    }

    public Long getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Long stockAmount) {
        this.stockAmount = stockAmount;
    }

    public Long getNonCheckAmount() {
        return nonCheckAmount;
    }

    public void setNonCheckAmount(Long nonCheckAmount) {
        this.nonCheckAmount = nonCheckAmount;
    }

    public Long getReissueAmount() {
        return reissueAmount;
    }

    public void setReissueAmount(Long reissueAmount) {
        this.reissueAmount = reissueAmount;
    }

    public Long getDisuseAmount() {
        return disuseAmount;
    }

    public void setDisuseAmount(Long disuseAmount) {
        this.disuseAmount = disuseAmount;
    }

    public Long getReturnNonCheckAmount() {
        return returnNonCheckAmount;
    }

    public void setReturnNonCheckAmount(Long returnNonCheckAmount) {
        this.returnNonCheckAmount = returnNonCheckAmount;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
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

	public BartagMaster getBartagMaster() {
		return bartagMaster;
	}

	public void setBartagMaster(BartagMaster bartagMaster) {
		this.bartagMaster = bartagMaster;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
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

	public ProductionStorage() {

	}

	public ProductionStorage(BartagMaster bartag, Long userSeq) {
		this.bartagMaster = bartag;
		this.totalAmount = bartag.getAmount();
		this.nonCheckAmount = bartag.getAmount();
		this.stockAmount = 0L;
		this.releaseAmount = 0L;
		this.reissueAmount = 0L;
		this.disuseAmount = 0L;
		this.returnAmount = 0L;
		this.returnNonCheckAmount = 0L;
		this.regDate = new Date();
		this.companyInfo = bartag.getProductionCompanyInfo();
		this.productYy = bartag.getProductYy();
		this.productSeason = bartag.getProductSeason();
		this.style = bartag.getStyle();
		this.color = bartag.getColor();
		this.size = bartag.getSize();
		this.orderDegree = bartag.getOrderDegree();
		this.additionOrderDegree = bartag.getAdditionOrderDegree();

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		this.regUserInfo = regUserInfo;
	}

	public ProductionStorage(ProductionStorage productionStorage) {
		this.totalAmount = productionStorage.getTotalAmount();
		this.releaseAmount = productionStorage.getReleaseAmount();
		this.stockAmount = productionStorage.getStockAmount();
		this.nonCheckAmount = productionStorage.getNonCheckAmount();
		this.reissueAmount = productionStorage.getReissueAmount();
		this.disuseAmount = productionStorage.getDisuseAmount();
		this.returnNonCheckAmount = productionStorage.getReturnNonCheckAmount();
		this.returnAmount = productionStorage.getReturnAmount();
	}
}
