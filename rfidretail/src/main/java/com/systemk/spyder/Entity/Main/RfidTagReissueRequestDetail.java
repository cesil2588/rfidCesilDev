package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Entity.Main.View.View;

//태그 재발행 요청 상세 테이블
//@ApiModel(description = "RFID 태그 재발행 요청 상세 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RfidTagReissueRequestDetail implements Serializable{

	private static final long serialVersionUID = -9171784472666423040L;

	// 태그 재발행 요청 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long rfidTagReissueRequestDetailSeq;

	//  RFID 태그
	//@ApiModelProperty(notes = "RFID 태그 인코딩")
    @Column(nullable = false, length = 32)
    @JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String rfidTag;

    // RFID 재발행 태그
	//@ApiModelProperty(notes = "재발행 RFID 태그 인코딩")
    @Column(nullable = true, length = 32)
    @JsonView({View.Public.class, View.MobileDetail.class})
    private String reissueRfidTag;

    // 바택 생성일자
	//@ApiModelProperty(notes = "바택 생성일자")
    @Column(nullable = true, length = 8)
    @JsonView({View.Public.class})
    private String createDate;

    // 바택 라인번호
	//@ApiModelProperty(notes = "바택 라인번호")
    @JsonView({View.Public.class})
    private Long lineSeq;

    // 바택 시리얼
	//@ApiModelProperty(notes = "바택 시리얼")
    @JsonView({View.Public.class})
    private Long seq;

    // 태그 시즌
	//@ApiModelProperty(notes = "RFID 태그 시즌")
    @Column(nullable = true, length = 3)
    @JsonView({View.Public.class})
    private String productRfidSeason;

    // 태그 오더차수
	//@ApiModelProperty(notes = "RFID 태그 오더차수")
    @Column(nullable = true, length = 3)
    @JsonView({View.Public.class})
    private String orderDegree;

    // 태그 스타일
	//@ApiModelProperty(notes = "스타일")
    @Column(nullable = true, length = 20)
    @JsonView({View.Public.class, View.MobileDetail.class})
    private String style;

    // 태그 컬러
	//@ApiModelProperty(notes = "컬러")
    @Column(nullable = true, length = 10)
    @JsonView({View.Public.class, View.MobileDetail.class})
    private String color;

    // 태그 사이즈
	//@ApiModelProperty(notes = "사이즈")
    @Column(nullable = true, length = 10)
    @JsonView({View.Public.class, View.MobileDetail.class})
    private String size;

    // 태그 일련번호
	//@ApiModelProperty(notes = "RFID 태그 시리얼")
    @Column(nullable = true, length = 5)
    @JsonView({View.Public.class, View.MobileDetail.class})
    private String rfidSeq;

	// 상태
	//@ApiModelProperty(notes = "상태(1: 재발행 요청, 2: 재발행 완료)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String stat;

	// 생산재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	@JsonIgnoreProperties({"publishCompanyInfo"})
	@ManyToOne
	@JoinColumn(name="productionStorageSeq")
	@JsonView({View.Public.class})
	private ProductionStorage productionStorage;

	// 재발행 요청 헤더
	//@ApiModelProperty(notes = "RFID 재발행 요청 헤더 일련번호")
	@JsonIgnoreProperties({"rfidTagReissueRequestDetail"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rfidTagReissueRequestSeq")
	@JsonView({View.Public.class})
	private RfidTagReissueRequest rfidTagReissueRequest;

	public Long getRfidTagReissueRequestDetailSeq() {
		return rfidTagReissueRequestDetailSeq;
	}

	public void setRfidTagReissueRequestDetailSeq(Long rfidTagReissueRequestDetailSeq) {
		this.rfidTagReissueRequestDetailSeq = rfidTagReissueRequestDetailSeq;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Long lineSeq) {
		this.lineSeq = lineSeq;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getProductRfidSeason() {
		return productRfidSeason;
	}

	public void setProductRfidSeason(String productRfidSeason) {
		this.productRfidSeason = productRfidSeason;
	}

	public String getOrderDegree() {
		return orderDegree;
	}

	public void setOrderDegree(String orderDegree) {
		this.orderDegree = orderDegree;
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

	public String getRfidSeq() {
		return rfidSeq;
	}

	public void setRfidSeq(String rfidSeq) {
		this.rfidSeq = rfidSeq;
	}

	public ProductionStorage getProductionStorage() {
		return productionStorage;
	}

	public void setProductionStorage(ProductionStorage productionStorage) {
		this.productionStorage = productionStorage;
	}

	public RfidTagReissueRequest getRfidTagReissueRequest() {
		return rfidTagReissueRequest;
	}

	public void setRfidTagReissueRequest(RfidTagReissueRequest rfidTagReissueRequest) {
		this.rfidTagReissueRequest = rfidTagReissueRequest;
	}

	public String getReissueRfidTag() {
		return reissueRfidTag;
	}

	public void setReissueRfidTag(String reissueRfidTag) {
		this.reissueRfidTag = reissueRfidTag;
	}

	public RfidTagReissueRequestDetail() {
	}

	public RfidTagReissueRequestDetail(RfidTagMaster rfidTag, BartagMaster bartag) {
		this.rfidTag			= rfidTag.getRfidTag();
		this.stat				= "1";
		this.rfidSeq			= rfidTag.getRfidSeq();
		this.createDate			= rfidTag.getCreateDate();
		this.lineSeq			= rfidTag.getLineSeq();
		this.seq				= rfidTag.getSeq();
		this.style				= bartag.getStyle();
		this.color				= bartag.getColor();
		this.size				= bartag.getSize();
		this.orderDegree		= bartag.getOrderDegree();
		this.productRfidSeason	= bartag.getProductRfidYySeason();
		this.productionStorage	= bartag.getProductionStorage();
	}
}