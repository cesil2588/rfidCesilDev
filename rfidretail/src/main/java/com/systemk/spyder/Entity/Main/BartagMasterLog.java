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

@SqlResultSetMapping(name = "bartagLogCountAllResult", classes = @ConstructorResult(targetClass = CountModel.class, 
columns = {
	@ColumnResult(name = "stat1Amount", type = Long.class), 
	@ColumnResult(name = "stat2Amount", type = Long.class), 
	@ColumnResult(name = "stat3Amount", type = Long.class),
	@ColumnResult(name = "stat4Amount", type = Long.class),
	@ColumnResult(name = "stat5Amount", type = Long.class),
	@ColumnResult(name = "stat6Amount", type = Long.class),
	@ColumnResult(name = "stat7Amount", type = Long.class)}
))

@NamedNativeQuery(name = "bartagLogCountAll", 
	query = "SELECT COUNT(CASE WHEN rtm.stat = '1' THEN rtm.stat END) stat1Amount, " +
				   "COUNT(CASE WHEN rtm.stat = '2' THEN rtm.stat END) stat2Amount, " + 
				   "COUNT(CASE WHEN rtm.stat = '3' THEN rtm.stat END) stat3Amount, " + 
				   "COUNT(CASE WHEN rtm.stat = '4' THEN rtm.stat END) stat4Amount, " + 
				   "COUNT(CASE WHEN rtm.stat = '5' THEN rtm.stat END) stat5Amount, " + 
				   "COUNT(CASE WHEN rtm.stat = '6' THEN rtm.stat END) stat6Amount, " + 
				   "COUNT(CASE WHEN rtm.stat = '7' THEN rtm.stat END) stat7Amount " + 
			  "FROM rfid_tag_master rtm " + 
			"WHERE rtm.bartag_seq = :seq", resultSetMapping = "bartagLogCountAllResult")
//@ApiModel(description = "바택 발행 로그 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BartagMasterLog implements Serializable {

	private static final long serialVersionUID = 4139144469461216706L;

	//  바택 로그 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long bartagMasterLogSeq;
	
	// 상태(1:미발행, 2:발행대기, 3:발행완료, 4:재발행대기, 5:재발행완료, 6:재발행요청, 7:폐기)
	//@ApiModelProperty(notes = "상태(1:미발행, 2:발행대기, 3:발행완료, 4:재발행대기, 5:재발행완료, 6:재발행요청, 7:폐기)")
	@Column(nullable = false, length = 1)
	private String stat;

    // 바택 일련번호
	//@ApiModelProperty(notes = "바택 발행 일련번호")
	@ManyToOne
	@JoinColumn(name="bartagSeq")
    private BartagMaster bartagMaster;
	
    //  이전 총 수량
	//@ApiModelProperty(notes = "이전 총 수량")
    private Long beforeTotalAmount;

    //  이전 미발행 수량
	//@ApiModelProperty(notes = "이전 미발행 수량")
    @Column(name="before_stat1_amount")
    private Long beforeStat1Amount;

    //  이전 발행대기 수량
	//@ApiModelProperty(notes = "이전 발행대기 수량")
    @Column(name="before_stat2_amount")
    private Long beforeStat2Amount;

    //  이전 발행완료 수량
	//@ApiModelProperty(notes = "이전 발행완료 수량")
    @Column(name="before_stat3_amount")
    private Long beforeStat3Amount;

    //  이전 재발행대기 수량
	//@ApiModelProperty(notes = "이전 재발행대기 수량")
    @Column(name="before_stat4_amount")
    private Long beforeStat4Amount;

    //  이전 재발행완료 수량
    //@ApiModelProperty(notes = "이전 재발행완료 수량")
    @Column(name="before_stat5_amount")
    private Long beforeStat5Amount;

    //  이전 재발행요청 수량
    //@ApiModelProperty(notes = "이전 재발행요청 수량")
    @Column(name="before_stat6_amount")
    private Long beforeStat6Amount;

    //  이전 폐기 수량
    //@ApiModelProperty(notes = "이전 폐기 수량")
    @Column(name="before_stat7_amount")
    private Long beforeStat7Amount;

    //  현재 총 수량
    //@ApiModelProperty(notes = "현재 총 수량")
	@ColumnDefault("0")
    private Long currentTotalAmount;

    //  현재 미발행 수량
    //@ApiModelProperty(notes = "현재 미발행 수량")
	@Column(name="current_stat1_amount")
    private Long currentStat1Amount;

    //  현재 발행대기 수량
    //@ApiModelProperty(notes = "현재 발행대기 수량")
	@Column(name="current_stat2_amount")
    private Long currentStat2Amount;

    //  현재 발행완료 수량
    //@ApiModelProperty(notes = "현재 발행완료 수량")
	@Column(name="current_stat3_amount")
    private Long currentStat3Amount;

    //  현재 재발행대기 수량
    //@ApiModelProperty(notes = "현재 재발행대기 수량")
	@Column(name="current_stat4_amount")
    private Long currentStat4Amount;

    //  현재 재발행완료 수량
    //@ApiModelProperty(notes = "현재 재발행완료 수량")
	@Column(name="current_stat5_amount")
    private Long currentStat5Amount;

    //  현재 재발행요청 수량
    //@ApiModelProperty(notes = "현재 재발행요청 수량")
	@Column(name="current_stat6_amount")
    private Long currentStat6Amount;

    //  현재 폐기 수량
    //@ApiModelProperty(notes = "현재 퍠기 수량")
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
    
    //  작업 시작일
    //@ApiModelProperty(notes = "작업시작일")
    private Date startDate;
    
    //  작업 종료일
    //@ApiModelProperty(notes = "작업종료일")
    private Date endDate;

	public Long getBartagMasterLogSeq() {
		return bartagMasterLogSeq;
	}

	public void setBartagMasterLogSeq(Long bartagMasterLogSeq) {
		this.bartagMasterLogSeq = bartagMasterLogSeq;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public BartagMaster getBartagMaster() {
		return bartagMaster;
	}

	public void setBartagMaster(BartagMaster bartagMaster) {
		this.bartagMaster = bartagMaster;
	}

	public Long getBeforeTotalAmount() {
		return beforeTotalAmount;
	}

	public void setBeforeTotalAmount(Long beforeTotalAmount) {
		this.beforeTotalAmount = beforeTotalAmount;
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

	public Long getBeforeStat7Amount() {
		return beforeStat7Amount;
	}

	public void setBeforeStat7Amount(Long beforeStat7Amount) {
		this.beforeStat7Amount = beforeStat7Amount;
	}

	public Long getCurrentTotalAmount() {
		return currentTotalAmount;
	}

	public void setCurrentTotalAmount(Long currentTotalAmount) {
		this.currentTotalAmount = currentTotalAmount;
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

	public Long getCurrentStat7Amount() {
		return currentStat7Amount;
	}

	public void setCurrentStat7Amount(Long currentStat7Amount) {
		this.currentStat7Amount = currentStat7Amount;
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
    
	public void CopyBeforeData(BartagMaster param)
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
    
    public void CopyCurrentData(BartagMaster param)
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
