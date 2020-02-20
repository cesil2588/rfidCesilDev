package com.systemk.spyder.Entity.Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Response.InventoryScheduleResult;
import com.systemk.spyder.Dto.Response.InventoryScheduleStyleResult;
import com.systemk.spyder.Entity.External.RfidMd14If;
import com.systemk.spyder.Entity.Main.View.View;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@ApiModel(description = "재고조사 헤더 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryScheduleHeader {

	// 재고조사 헤더 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private Long inventoryScheduleHeaderSeq;

	// 생성일
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = false, length = 8)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String createDate;

	// 작업라인
	//@ApiModelProperty(notes = "작업 라인")
	@JsonView({View.Public.class})
	private Long workLine;

	// 업체
	//@ApiModelProperty(notes = "작업 업체 일련번호")
	@JsonIgnoreProperties({"roleInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companySeq")
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private CompanyInfo companyInfo;

	// 업체 타입
	//@ApiModelProperty(notes = "업체 타입(distribution: 물류, store: 매장)")
	@Column(nullable = false, length = 20)
	@JsonView({View.Public.class})
	private String companyType;

	// 작업 여부(Y, N)
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String flag;

	// 확정 여부
	//@ApiModelProperty(notes = "확정 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
	private String confirmYn;

	// 확정일
	//@ApiModelProperty(notes = "확정일")
	@JsonView({View.Public.class})
	private Date confirmDate;

	// 완료 여부
	//@ApiModelProperty(notes = "완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class})
	private String completeYn;

	// 완료일
	//@ApiModelProperty(notes = "완료일")
	@JsonView({View.Public.class})
	private Date completeDate;

	// 배치 여부
	//@ApiModelProperty(notes = "배치 여부(Y, N)")
	@Column(nullable = true, length = 1)
	@JsonView({ View.Public.class})
	private String batchYn;

	// 배치일
	//@ApiModelProperty(notes = "배치일")
	@JsonView({ View.Public.class})
	private Date batchDate;

	// 종결 여부
	//@ApiModelProperty(notes = "종결 여부(Y, N)")
	@Column(nullable = false, length = 1)
	@JsonView({View.Public.class})
	private String disuseYn;

	// 종결일
	//@ApiModelProperty(notes = "종결일")
	@JsonView({View.Public.class})
	private Date disuseDate;

	// 비고
	//@ApiModelProperty(notes = "비고")
    @Column(nullable = false, length = 100)
    @JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private String explanatory;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
    @JsonView({View.Public.class})
	private Date regDate;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regUserSeq")
	@JsonView({View.Public.class})
	private UserInfo regUserInfo;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@JsonView({View.Public.class})
	private Date updDate;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updUserSeq")
	@JsonView({View.Public.class})
	private UserInfo updUserInfo;

	// 전산재고
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Long totalAmount;

    // 실사수량
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Long totalCompleteAmount;

	// 총망실수량
	@JsonView({View.Public.class, View.MobileList.class, View.MobileDetail.class})
    private Long totalDisuseAmount;

	// 스타일 목록
	//@ApiModelProperty(notes = "스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "inventoryScheduleHeaderSeq")
	@JsonView({View.Public.class, View.MobileDetail.class})
	public Set<InventoryScheduleStyle> styleList;

	// 마지막 작업한 PDA 유니크 키
	@Column(nullable = true, length = 50)
	private String pdaKey;

	// ERP 등록 구분
	@Column(nullable = true, length = 1)
	private String erpRegType;

	public Long getInventoryScheduleHeaderSeq() {
		return inventoryScheduleHeaderSeq;
	}

	public void setInventoryScheduleHeaderSeq(Long inventoryScheduleHeaderSeq) {
		this.inventoryScheduleHeaderSeq = inventoryScheduleHeaderSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getWorkLine() {
		return workLine;
	}

	public void setWorkLine(Long workLine) {
		this.workLine = workLine;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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

	public Set<InventoryScheduleStyle> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<InventoryScheduleStyle> styleList) {
		this.styleList = styleList;
	}

	public String getDisuseYn() {
		return disuseYn;
	}

	public void setDisuseYn(String disuseYn) {
		this.disuseYn = disuseYn;
	}

	public Date getDisuseDate() {
		return disuseDate;
	}

	public void setDisuseDate(Date disuseDate) {
		this.disuseDate = disuseDate;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getBatchYn() {
		return batchYn;
	}

	public void setBatchYn(String batchYn) {
		this.batchYn = batchYn;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTotalCompleteAmount() {
		return totalCompleteAmount;
	}

	public void setTotalCompleteAmount(Long totalCompleteAmount) {
		this.totalCompleteAmount = totalCompleteAmount;
	}

	public Long getTotalDisuseAmount() {
		return totalDisuseAmount;
	}

	public void setTotalDisuseAmount(Long totalDisuseAmount) {
		this.totalDisuseAmount = totalDisuseAmount;
	}

	public String getPdaKey() {
		return pdaKey;
	}

	public void setPdaKey(String pdaKey) {
		this.pdaKey = pdaKey;
	}

	public String getErpRegType() {
		return erpRegType;
	}

	public void setErpRegType(String erpRegType) {
		this.erpRegType = erpRegType;
	}

	public InventoryScheduleHeader() {
	}

	public InventoryScheduleHeader(RfidMd14If obj, ProductMaster productMaster, CompanyInfo companyInfo, UserInfo userInfo) {
		this.createDate				= obj.getKey().getMd14Bsdt();
		this.workLine				= 0L;
		this.companyInfo			= companyInfo;
		this.companyType			= "store";
		this.erpRegType				= obj.getKey().getMd14Engb();
		this.flag					= "N";
		this.confirmYn				= "N";
		this.completeYn				= "N";
		this.batchYn				= "N";
		this.disuseYn				= "N";
		this.explanatory			= obj.getKey().getMd14Bsdt() + " 재고실사";
		this.regUserInfo			= userInfo;
		this.regDate				= new Date();
		this.totalCompleteAmount	= 0L;
		this.totalAmount			= obj.getMd14Mjlq().longValue();
		this.totalDisuseAmount		= obj.getMd14Mjlq().longValue();
		this.styleList				= new HashSet<InventoryScheduleStyle>();
		this.styleList.add(new InventoryScheduleStyle(obj, productMaster));
	}

	// 초기화
	public void init(UserInfo userInfo) {
		this.updUserInfo = userInfo;
		this.updDate = new Date();
		this.flag = "N";
		this.totalCompleteAmount = 0L;
		this.totalDisuseAmount = totalAmount;

		for(InventoryScheduleStyle style : this.styleList) {
			style.init();
		}
	}

	// DTO to Entity
	public void copyData(InventoryScheduleResult result) {
		this.confirmDate = null;
		this.confirmYn = "N";
		this.flag = result.getFlag();

		// 초기화 하여 서버 전송했을 경우
		if(result.getFlag().equals("N")){

			Long totalAmount = 0L;
			Set<InventoryScheduleStyle> tempStyleList = new HashSet<>();

			// 각 스타일별로 돌면서 신규 스타일이 아닌 스타일을 조회
			for(InventoryScheduleStyle style : this.getStyleList()) {
				if(style.getFlag().equals("S")){
					continue;
				}

				// 신규 스타일이 아닌경우 수량 초기화
				style.init();
				totalAmount += style.getAmount();
				tempStyleList.add(style);
			}

			this.getStyleList().clear();
			this.getStyleList().addAll(tempStyleList);

			this.totalAmount = totalAmount;
			this.totalCompleteAmount = 0L;
			this.totalDisuseAmount = result.getTotalAmount();

		// 작업 데이터가 있을 경우
		} else {

			this.totalAmount = result.getTotalAmount();
			this.totalCompleteAmount = result.getTotalCompleteAmount();
			this.totalDisuseAmount = result.getTotalDisuseAmount();

			for(InventoryScheduleStyleResult tempStyle : result.getStyleList()) {

				boolean pushFlag = true;

				loop:
				for(InventoryScheduleStyle style : this.getStyleList()) {

					// 기존 스타일 데이터 변경
					if(style.equals(tempStyle)) {

						style.copyData(tempStyle);
						pushFlag = false;

						break loop;
					}
				}

				// 신규 스타일은 추가
				if(pushFlag) {
					this.getStyleList().add(new InventoryScheduleStyle(tempStyle));
				}
			}
		}
	}

	// Entity, Temp 비교
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InventoryScheduleHeader header = (InventoryScheduleHeader) o;
		return Objects.equals(createDate, header.createDate) &&
				Objects.equals(companyInfo.getCustomerCode(), header.companyInfo.getCustomerCode()) &&
				Objects.equals(companyInfo.getCornerCode(), header.companyInfo.getCornerCode()) &&
				Objects.equals(erpRegType, header.erpRegType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createDate, companyInfo, erpRegType);
	}
}
