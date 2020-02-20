package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.systemk.spyder.Service.CustomBean.CountModel;

@SqlResultSetMapping(name = "productionLogCountAllResult", classes = @ConstructorResult(targetClass = CountModel.class, 
columns = {
	@ColumnResult(name = "stat1Amount", type = Long.class), 
	@ColumnResult(name = "stat2Amount", type = Long.class), 
	@ColumnResult(name = "stat3Amount", type = Long.class),
	@ColumnResult(name = "stat4Amount", type = Long.class),
	@ColumnResult(name = "stat5Amount", type = Long.class),
	@ColumnResult(name = "stat6Amount", type = Long.class),
	@ColumnResult(name = "stat7Amount", type = Long.class)}
))

@NamedNativeQuery(name = "productionLogCountAll", 
	query = "SELECT COUNT(CASE WHEN psrt.stat = '1' THEN psrt.stat END) stat1Amount, " +
				   "COUNT(CASE WHEN psrt.stat = '2' THEN psrt.stat END) stat2Amount, " + 
				   "COUNT(CASE WHEN psrt.stat = '3' THEN psrt.stat END) stat3Amount, " + 
				   "COUNT(CASE WHEN psrt.stat = '4' THEN psrt.stat END) stat4Amount, " + 
				   "COUNT(CASE WHEN psrt.stat = '5' THEN psrt.stat END) stat5Amount, " + 
				   "COUNT(CASE WHEN psrt.stat = '6' THEN psrt.stat END) stat6Amount, " + 
				   "COUNT(CASE WHEN psrt.stat = '7' THEN psrt.stat END) stat7Amount " + 
			  "FROM production_storage_rfid_tag psrt " + 
			"WHERE psrt.production_storage_seq = :seq", resultSetMapping = "productionLogCountAllResult")
//생산 재고 이력 테이블
//@ApiModel(description = "생산 재고 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionStorageLog implements Serializable{

	private static final long serialVersionUID = -67966988105090319L;

	//  생산재고 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long productionStorageLogSeq;
	
	// 작업타입(1:web, 2:pda)
	//@ApiModelProperty(notes = "작업 타입(1:Web, 2: PDA)")
	@Column(nullable = false, length = 1)
	private String type;
	
	// 상태(1:입고예정, 2:재고, 3:출고, 4:재발행요청, 5:폐기, 6:반품검수, 7:반품)
	//@ApiModelProperty(notes = "상태(1: 입고예정, 2: 재고, 3: 출고, 4: 재발행요청, 5: 폐기, 6: 반품검수, 7: 반품)")
	@Column(nullable = false, length = 3)
	private String stat;

    //  생산재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	@ManyToOne
	@JoinColumn(name="productionStorageSeq")
    private ProductionStorage productionStorage;
	
    //  이전 총 수량
	//@ApiModelProperty(notes = "이전 총 수량")
	@ColumnDefault("0")
    private Long beforeTotalAmount;

    //  이전 출고 수량
	//@ApiModelProperty(notes = "이전 출고 수량")
	@ColumnDefault("0")
    private Long beforeReleaseAmount;

    //  이전 재고 수량
	//@ApiModelProperty(notes = "이전 재고 수량")
	@ColumnDefault("0")
    private Long beforeStockAmount;

    //  이전 미검수 수량
	//@ApiModelProperty(notes = "이전 입고예정 수량")
	@ColumnDefault("0")
    private Long beforeNonCheckAmount;

    //  이전 재발행 요청 수량
	//@ApiModelProperty(notes = "이전 재발행 요청 수량")
	@ColumnDefault("0")
    private Long beforeReissueAmount;

    //  이전 폐기 수량
	//@ApiModelProperty(notes = "이전 폐기 수량")
	@ColumnDefault("0")
    private Long beforeDisuseAmount;

    //  이전 반품 미검수 수량
	//@ApiModelProperty(notes = "이전 반품 미검수 수량")
	@ColumnDefault("0")
    private Long beforeReturnNonCheckAmount;

    //  이전 반품 수량
	//@ApiModelProperty(notes = "이전 반품 수량")
	@ColumnDefault("0")
    private Long beforeReturnAmount;

    //  현재 총 수량
	//@ApiModelProperty(notes = "현재 총 수량")
	@ColumnDefault("0")
    private Long currentTotalAmount;

    //  현재 출고 수량
	//@ApiModelProperty(notes = "현재 출고 수량")
	@ColumnDefault("0")
    private Long currentReleaseAmount;

    //  현재 재고 수량
	//@ApiModelProperty(notes = "현재 재고 수량")
	@ColumnDefault("0")
    private Long currentStockAmount;

    //  현재 입고예정 수량
	//@ApiModelProperty(notes = "현재 입고예정 수량")
	@ColumnDefault("0")
    private Long currentNonCheckAmount;

    //  현재 재발행 요청 수량
	//@ApiModelProperty(notes = "현재 재발행 요청 수량")
	@ColumnDefault("0")
    private Long currentReissueAmount;

    //  현재 폐기 수량
	//@ApiModelProperty(notes = "현재 폐기 수량")
	@ColumnDefault("0")
    private Long currentDisuseAmount;

    //  현재 반품 미검수 수량
	//@ApiModelProperty(notes = "현재 반품 미검수 수량")
	@ColumnDefault("0")
    private Long currentReturnNonCheckAmount;

    //  현재 반품 수량
	//@ApiModelProperty(notes = "현재 반품 수량")
	@ColumnDefault("0")
    private Long currentReturnAmount;

    //  등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
	//  작업 시작일
	//@ApiModelProperty(notes = "작업 시작일")
    private Date startDate;
    
    //  작업 종료일
	//@ApiModelProperty(notes = "작업 종료일")
    private Date endDate;

    public Long getProductionStorageLogSeq() {
        return productionStorageLogSeq;
    }

    public void setProductionStorageLogSeq(Long productionStorageLogSeq) {
        this.productionStorageLogSeq = productionStorageLogSeq;
    }

	public Long getBeforeTotalAmount() {
        return beforeTotalAmount;
    }

    public void setBeforeTotalAmount(Long beforeTotalAmount) {
        this.beforeTotalAmount = beforeTotalAmount;
    }

    public Long getBeforeReleaseAmount() {
        return beforeReleaseAmount;
    }

    public void setBeforeReleaseAmount(Long beforeReleaseAmount) {
        this.beforeReleaseAmount = beforeReleaseAmount;
    }

    public Long getBeforeStockAmount() {
        return beforeStockAmount;
    }

    public void setBeforeStockAmount(Long beforeStockAmount) {
        this.beforeStockAmount = beforeStockAmount;
    }

    public Long getBeforeNonCheckAmount() {
        return beforeNonCheckAmount;
    }

    public void setBeforeNonCheckAmount(Long beforeNonCheckAmount) {
        this.beforeNonCheckAmount = beforeNonCheckAmount;
    }

    public Long getBeforeReissueAmount() {
        return beforeReissueAmount;
    }

    public void setBeforeReissueAmount(Long beforeReissueAmount) {
        this.beforeReissueAmount = beforeReissueAmount;
    }

    public Long getBeforeDisuseAmount() {
        return beforeDisuseAmount;
    }

    public void setBeforeDisuseAmount(Long beforeDisuseAmount) {
        this.beforeDisuseAmount = beforeDisuseAmount;
    }

    public Long getBeforeReturnNonCheckAmount() {
        return beforeReturnNonCheckAmount;
    }

    public void setBeforeReturnNonCheckAmount(Long beforeReturnNonCheckAmount) {
        this.beforeReturnNonCheckAmount = beforeReturnNonCheckAmount;
    }

    public Long getBeforeReturnAmount() {
        return beforeReturnAmount;
    }

    public void setBeforeReturnAmount(Long beforeReturnAmount) {
        this.beforeReturnAmount = beforeReturnAmount;
    }

    public Long getCurrentTotalAmount() {
        return currentTotalAmount;
    }

    public void setCurrentTotalAmount(Long currentTotalAmount) {
        this.currentTotalAmount = currentTotalAmount;
    }

    public Long getCurrentReleaseAmount() {
        return currentReleaseAmount;
    }

    public void setCurrentReleaseAmount(Long currentReleaseAmount) {
        this.currentReleaseAmount = currentReleaseAmount;
    }

    public Long getCurrentStockAmount() {
        return currentStockAmount;
    }

    public void setCurrentStockAmount(Long currentStockAmount) {
        this.currentStockAmount = currentStockAmount;
    }

    public Long getCurrentNonCheckAmount() {
        return currentNonCheckAmount;
    }

    public void setCurrentNonCheckAmount(Long currentNonCheckAmount) {
        this.currentNonCheckAmount = currentNonCheckAmount;
    }

    public Long getCurrentReissueAmount() {
        return currentReissueAmount;
    }

    public void setCurrentReissueAmount(Long currentReissueAmount) {
        this.currentReissueAmount = currentReissueAmount;
    }

    public Long getCurrentDisuseAmount() {
        return currentDisuseAmount;
    }

    public void setCurrentDisuseAmount(Long currentDisuseAmount) {
        this.currentDisuseAmount = currentDisuseAmount;
    }

    public Long getCurrentReturnNonCheckAmount() {
        return currentReturnNonCheckAmount;
    }

    public void setCurrentReturnNonCheckAmount(Long currentReturnNonCheckAmount) {
        this.currentReturnNonCheckAmount = currentReturnNonCheckAmount;
    }

    public Long getCurrentReturnAmount() {
        return currentReturnAmount;
    }

    public void setCurrentReturnAmount(Long currentReturnAmount) {
        this.currentReturnAmount = currentReturnAmount;
    }

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public ProductionStorage getProductionStorage() {
		return productionStorage;
	}

	public void setProductionStorage(ProductionStorage productionStorage) {
		this.productionStorage = productionStorage;
	}

    public void CopyBeforeData(ProductionStorage param)
    {
        this.beforeTotalAmount = param.getTotalAmount();
        this.beforeReleaseAmount = param.getReleaseAmount();
        this.beforeStockAmount = param.getStockAmount();
        this.beforeNonCheckAmount = param.getNonCheckAmount();
        this.beforeReissueAmount = param.getReissueAmount();
        this.beforeDisuseAmount = param.getDisuseAmount();
        this.beforeReturnNonCheckAmount = param.getReturnNonCheckAmount();
        this.beforeReturnAmount = param.getReturnAmount();
    }
    
    public void CopyCurrentData(ProductionStorage param)
    {
        this.currentTotalAmount = param.getTotalAmount();
        this.currentReleaseAmount = param.getReleaseAmount();
        this.currentStockAmount = param.getStockAmount();
        this.currentNonCheckAmount = param.getNonCheckAmount();
        this.currentReissueAmount = param.getReissueAmount();
        this.currentDisuseAmount = param.getDisuseAmount();
        this.currentReturnNonCheckAmount = param.getReturnNonCheckAmount();
        this.currentReturnAmount = param.getReturnAmount();
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}
