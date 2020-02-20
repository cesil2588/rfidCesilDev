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

@SqlResultSetMapping(name = "storeLogCountAllResult", classes = @ConstructorResult(targetClass = CountModel.class, 
columns = {
	@ColumnResult(name = "stat1Amount", type = Long.class), 
	@ColumnResult(name = "stat2Amount", type = Long.class), 
	@ColumnResult(name = "stat3Amount", type = Long.class),
	@ColumnResult(name = "stat4Amount", type = Long.class),
	@ColumnResult(name = "stat5Amount", type = Long.class),
	@ColumnResult(name = "stat6Amount", type = Long.class),
	@ColumnResult(name = "stat7Amount", type = Long.class)}
))

@NamedNativeQuery(name = "storeLogCountAll", 
	query = "SELECT COUNT(CASE WHEN ssrt.stat = '1' THEN ssrt.stat END) stat1Amount, " +
				   "COUNT(CASE WHEN ssrt.stat = '2' THEN ssrt.stat END) stat2Amount, " + 
				   "COUNT(CASE WHEN ssrt.stat = '3' THEN ssrt.stat END) stat3Amount, " + 
				   "COUNT(CASE WHEN ssrt.stat = '4' THEN ssrt.stat END) stat4Amount, " + 
				   "COUNT(CASE WHEN ssrt.stat = '5' THEN ssrt.stat END) stat5Amount, " + 
				   "COUNT(CASE WHEN ssrt.stat = '6' THEN ssrt.stat END) stat6Amount, " + 
				   "COUNT(CASE WHEN ssrt.stat = '7' THEN ssrt.stat END) stat7Amount " + 
			  "FROM store_storage_rfid_tag ssrt " + 
			"WHERE ssrt.store_storage_seq = :seq", resultSetMapping = "storeLogCountAllResult")
//매장 재고 이력 테이블
//@ApiModel(description = "매장 재고 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreStorageLog implements Serializable{

	private static final long serialVersionUID = -3744553680277940603L;

	// 매장 재고 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long storeStorageLogSeq;
	
	// 매장 재고 일련번호
	//@ApiModelProperty(notes = "매장 재고 일련번호")
	@ManyToOne
	@JoinColumn(name="storeStorageSeq")
	private StoreStorage storeStorage;
	
	// 작업타입(1:web, 2:pda, 3:컨베이어)
	//@ApiModelProperty(notes = "작업타입(1:web, 2:pda, 3:컨베이어)")
	@Column(nullable = false, length = 1)
	private String type;

	// 상태(1:입고예정, 2:입고, 3:판매, 4:반품, 5:재발행요청, 6:매장간이동, 7:폐기)
	//@ApiModelProperty(notes = "상태(1:입고예정, 2:입고, 3:판매, 4:반품, 5:재발행요청, 6:매장간이동, 7:폐기)")
	@Column(nullable = false, length = 3)
	private String stat;

	// 이전 총 수량
	//@ApiModelProperty(notes = "이전 총 수량")
	@ColumnDefault("0")
	private Long beforeTotalAmount;

	// 이전 입고예정 수량
	//@ApiModelProperty(notes = "이전 입고 예정 수량")
	@Column(name="before_stat1_amount")
	private Long beforeStat1Amount;

	// 이전 재고 수량
	//@ApiModelProperty(notes = "이전 재고 수량")
	@Column(name="before_stat2_amount")
	private Long beforeStat2Amount;

	// 이전 판매 수량
	//@ApiModelProperty(notes = "이전 판매 수량")
	@Column(name="before_stat3_amount")
	private Long beforeStat3Amount;

	// 이전 반품 수량
	//@ApiModelProperty(notes = "이전 반품 수량")
	@Column(name="before_stat4_amount")
	private Long beforeStat4Amount;

	// 이전 재발행 요청 수량
	//@ApiModelProperty(notes = "이전 재발행 요청 수량")
	@Column(name="before_stat5_amount")
	private Long beforeStat5Amount;

	// 이전 매장간이동 요청 수량
	//@ApiModelProperty(notes = "이전 매장간 이동 요청 수량")
	@Column(name="before_stat6_amount")
	private Long beforeStat6Amount;
	
	// 이전 폐기 수량
	//@ApiModelProperty(notes = "이전 폐기 수량")
	@Column(name="before_stat7_amount")
	private Long beforeStat7Amount;

	// 현재 총 수량
	//@ApiModelProperty(notes = "현재 총 수량")
	@ColumnDefault("0")
	private Long currentTotalAmount;

	// 현재 입고 예정 수량
	//@ApiModelProperty(notes = "현재 입고 예정 수량")
	@Column(name="current_stat1_amount")
	private Long currentStat1Amount;

	// 현재 재고 수량
	//@ApiModelProperty(notes = "현재 재고 수량")
	@Column(name="current_stat2_amount")
	private Long currentStat2Amount;

	// 현재 판매 수량
	//@ApiModelProperty(notes = "현재 판매 수량")
	@Column(name="current_stat3_amount")
	private Long currentStat3Amount;

	// 현재 반품 수량
	//@ApiModelProperty(notes = "현재 반품 수량")
	@Column(name="current_stat4_amount")
	private Long currentStat4Amount;

	// 현재 재발행 요청 수량
	//@ApiModelProperty(notes = "현재 재발행 요청 수량")
	@Column(name="current_stat5_amount")
	private Long currentStat5Amount;

	// 현재 매장간 이동 요청 수량
	//@ApiModelProperty(notes = "현재 매장간 이동 요청 수량")
	@Column(name="current_stat6_amount")
	private Long currentStat6Amount;
	
	// 폐기
	//@ApiModelProperty(notes = "현재 폐기 수량")
	@Column(name="current_stat7_amount")
	private Long currentStat7Amount;

	//  등록자
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
	// 작업 시작일
	//@ApiModelProperty(notes = "작업 시작일")
    private Date startDate;
    
	// 작업 종료일
	//@ApiModelProperty(notes = "작업 종료일")
    private Date endDate;

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

	public Long getBeforeTotalAmount() {
		return beforeTotalAmount;
	}

	public void setBeforeTotalAmount(Long beforeTotalAmount) {
		this.beforeTotalAmount = beforeTotalAmount;
	}


	public Long getCurrentTotalAmount() {
		return currentTotalAmount;
	}

	public void setCurrentTotalAmount(Long currentTotalAmount) {
		this.currentTotalAmount = currentTotalAmount;
	}

	public Long getBeforeStat1Amount() {
		return beforeStat1Amount;
	}

	public void setBeforeStat1Amount(Long beforeStat1Amount) {
		this.beforeStat1Amount = beforeStat1Amount;
	}

	public Long getBeforeStat2Amount() {
		return beforeStat2Amount;
	}

	public void setBeforeStat2Amount(Long beforeStat2Amount) {
		this.beforeStat2Amount = beforeStat2Amount;
	}

	public Long getBeforeStat3Amount() {
		return beforeStat3Amount;
	}

	public void setBeforeStat3Amount(Long beforeStat3Amount) {
		this.beforeStat3Amount = beforeStat3Amount;
	}

	public Long getBeforeStat4Amount() {
		return beforeStat4Amount;
	}

	public void setBeforeStat4Amount(Long beforeStat4Amount) {
		this.beforeStat4Amount = beforeStat4Amount;
	}

	public Long getBeforeStat5Amount() {
		return beforeStat5Amount;
	}

	public void setBeforeStat5Amount(Long beforeStat5Amount) {
		this.beforeStat5Amount = beforeStat5Amount;
	}

	public Long getBeforeStat6Amount() {
		return beforeStat6Amount;
	}

	public void setBeforeStat6Amount(Long beforeStat6Amount) {
		this.beforeStat6Amount = beforeStat6Amount;
	}

	public Long getCurrentStat1Amount() {
		return currentStat1Amount;
	}

	public void setCurrentStat1Amount(Long currentStat1Amount) {
		this.currentStat1Amount = currentStat1Amount;
	}

	public Long getCurrentStat2Amount() {
		return currentStat2Amount;
	}

	public void setCurrentStat2Amount(Long currentStat2Amount) {
		this.currentStat2Amount = currentStat2Amount;
	}

	public Long getCurrentStat3Amount() {
		return currentStat3Amount;
	}

	public void setCurrentStat3Amount(Long currentStat3Amount) {
		this.currentStat3Amount = currentStat3Amount;
	}

	public Long getCurrentStat4Amount() {
		return currentStat4Amount;
	}

	public void setCurrentStat4Amount(Long currentStat4Amount) {
		this.currentStat4Amount = currentStat4Amount;
	}

	public Long getCurrentStat5Amount() {
		return currentStat5Amount;
	}

	public void setCurrentStat5Amount(Long currentStat5Amount) {
		this.currentStat5Amount = currentStat5Amount;
	}

	public Long getCurrentStat6Amount() {
		return currentStat6Amount;
	}

	public void setCurrentStat6Amount(Long currentStat6Amount) {
		this.currentStat6Amount = currentStat6Amount;
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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getStoreStorageLogSeq() {
		return storeStorageLogSeq;
	}

	public void setStoreStorageLogSeq(Long storeStorageLogSeq) {
		this.storeStorageLogSeq = storeStorageLogSeq;
	}

	public void CopyBeforeData(StoreStorage param)
    {
        this.beforeTotalAmount = param.getTotalAmount();
        this.beforeStat1Amount = param.getStat1Amount();
        this.beforeStat2Amount = param.getStat2Amount();
        this.beforeStat3Amount = param.getStat3Amount();
        this.beforeStat4Amount = param.getStat4Amount();
        this.beforeStat5Amount = param.getStat5Amount();
        this.beforeStat6Amount = param.getStat6Amount();
        this.beforeStat7Amount = param.getStat7Amount();
    }
    
    public void CopyCurrentData(StoreStorage param)
    {
        this.currentTotalAmount = param.getTotalAmount();
        this.currentStat1Amount = param.getStat1Amount();
        this.currentStat2Amount = param.getStat2Amount();
        this.currentStat3Amount = param.getStat3Amount();
        this.currentStat4Amount = param.getStat4Amount();
        this.currentStat5Amount = param.getStat5Amount();
        this.currentStat6Amount = param.getStat6Amount();
        this.currentStat7Amount = param.getStat7Amount();
    }

	public StoreStorage getStoreStorage() {
		return storeStorage;
	}

	public void setStoreStorage(StoreStorage storeStorage) {
		this.storeStorage = storeStorage;
	}

	public Long getBeforeStat7Amount() {
		return beforeStat7Amount;
	}

	public void setBeforeStat7Amount(Long beforeStat7Amount) {
		this.beforeStat7Amount = beforeStat7Amount;
	}

	public Long getCurrentStat7Amount() {
		return currentStat7Amount;
	}

	public void setCurrentStat7Amount(Long currentStat7Amount) {
		this.currentStat7Amount = currentStat7Amount;
	}
    
}