package com.systemk.spyder.Entity.Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxHeader;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxStyle;
import com.systemk.spyder.Entity.External.RfidLe10If;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Util.CalendarUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@ApiModel(description = "물류출고예정 헤더 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReleaseScheduleLog implements Serializable{

	private static final long serialVersionUID = -6653913931827699481L;

	// 물류출고예정 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long releaseScheduleLogSeq;

	// 출고오더타입
	//@ApiModelProperty(notes = "출고 오더 타입(storeRelease: 매장 출고, onlineRelease: 온라인 출고)")
	@Column(nullable = false, length = 20)
	private String orderType;

	// 생성일
	//@ApiModelProperty(notes = "생성일")
	@Column(nullable = false, length = 8)
	private String createDate;

	// 작업라인
	//@ApiModelProperty(notes = "작업라인")
	private Long workLine;

	// 박스 일련번호
	//@ApiModelProperty(notes = "박스 일련번호")
	@JsonIgnoreProperties({"regUserInfo", "updUserInfo"})
	@OneToOne
	@JoinColumn(name="boxSeq")
	private BoxInfo boxInfo;

	// 등록일
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

	// 물류출고예정 상세 목록
	//@ApiModelProperty(notes = "물류출고예정 스타일 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "releaseScheduleLogSeq")
	private Set<ReleaseScheduleDetailLog> releaseScheduleDetailLog;

	// 출고작업 여부
	//@ApiModelProperty(notes = "물류 출고 작업 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String releaseYn;

	// 출고작업 일
	//@ApiModelProperty(notes = "물류 출고 작업일")
	private Date releaseDate;

	// 완료여부
	//@ApiModelProperty(notes = "물류 출고 작업 완료 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String completeYn;

	// 완료일
	//@ApiModelProperty(notes = "물류 출고 작업 완료일")
	private Date completeDate;

	// 배치 여부
	//@ApiModelProperty(notes = "물류 출고 작업 완료 배치여부(Y, N)")
	@Column(nullable = true, length = 1)
	private String completeBatchYn;

	// 배치 완료일
	//@ApiModelProperty(notes = "물류 출고 작업 완료 배치일")
	private Date completeBatchDate;

	// 폐기 여부(상품 수정시)
	//@ApiModelProperty(notes = "폐기 여부(Y, N)")
	@Column(nullable = true, length = 1)
	private String disuseYn;

	// 맵핑 여부(컨베이어 통과 안된 제품)
	@Column(nullable = true, length = 1)
	private String mappingYn;

	// ERP 입고 실적 전달 여부
	@Column(nullable = true, length = 1)
	@JsonView({View.Public.class})
	private String erpCompleteYn;

	// ERP 입고 실적 전달 날짜
	@JsonView({View.Public.class})
	private Date erpCompleteDate;

	// ERP 출고일자
	@Column(nullable = true, length = 8)
	private String erpReleaseDate;

	// erp 출고seq
	private Long erpReleaseSeq;

	public Long getReleaseScheduleLogSeq() {
		return releaseScheduleLogSeq;
	}

	public void setReleaseScheduleLogSeq(Long releaseScheduleLogSeq) {
		this.releaseScheduleLogSeq = releaseScheduleLogSeq;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public BoxInfo getBoxInfo() {
		return boxInfo;
	}

	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
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

	public Set<ReleaseScheduleDetailLog> getReleaseScheduleDetailLog() {
		return releaseScheduleDetailLog;
	}

	public void setReleaseScheduleDetailLog(Set<ReleaseScheduleDetailLog> releaseScheduleDetailLog) {
		this.releaseScheduleDetailLog = releaseScheduleDetailLog;
	}

	public String getReleaseYn() {
		return releaseYn;
	}

	public void setReleaseYn(String releaseYn) {
		this.releaseYn = releaseYn;
	}

	public String getCompleteYn() {
		return completeYn;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getDisuseYn() {
		return disuseYn;
	}

	public void setDisuseYn(String disuseYn) {
		this.disuseYn = disuseYn;
	}

	public String getCompleteBatchYn() {
		return completeBatchYn;
	}

	public void setCompleteBatchYn(String completeBatchYn) {
		this.completeBatchYn = completeBatchYn;
	}

	public Date getCompleteBatchDate() {
		return completeBatchDate;
	}

	public void setCompleteBatchDate(Date completeBatchDate) {
		this.completeBatchDate = completeBatchDate;
	}

	public String getMappingYn() {
		return mappingYn;
	}

	public void setMappingYn(String mappingYn) {
		this.mappingYn = mappingYn;
	}

	public String getErpReleaseDate() {
		return erpReleaseDate;
	}

	public void setErpReleaseDate(String erpReleaseDate) {
		this.erpReleaseDate = erpReleaseDate;
	}

	public String getErpCompleteYn() {
		return erpCompleteYn;
	}

	public void setErpCompleteYn(String erpCompleteYn) {
		this.erpCompleteYn = erpCompleteYn;
	}

	public Date getErpCompleteDate() {
		return erpCompleteDate;
	}

	public void setErpCompleteDate(Date erpCompleteDate) {
		this.erpCompleteDate = erpCompleteDate;
	}

	public Long getErpReleaseSeq() {
		return erpReleaseSeq;
	}

	public void setErpReleaseSeq(Long erpReleaseSeq) {
		this.erpReleaseSeq = erpReleaseSeq;
	}

	public ReleaseScheduleLog() {

	}

	public ReleaseScheduleLog(RfidLe10If storeStorage, BoxInfo boxInfo, UserInfo userInfo, ProductMaster productMaster) {
		this.erpReleaseDate = storeStorage.getKey().getLe10Chdt();
		this.erpReleaseSeq = storeStorage.getKey().getLe10Chsq().longValue();
		this.boxInfo = boxInfo;
		this.orderType = "storeRelease";
		this.createDate = CalendarUtil.convertFormat("yyyyMMdd");
		this.regDate = new Date();
		this.regUserInfo = userInfo;
		this.updDate = new Date();
		this.updUserInfo = userInfo;
		this.releaseYn = "Y";
		this.releaseDate = new Date();
		this.completeYn = "N";
		this.completeBatchYn = "N";
		this.disuseYn = "N";
		this.mappingYn = "N";
		this.erpCompleteYn = "N";
		if(this.releaseScheduleDetailLog == null) {
			this.releaseScheduleDetailLog = new HashSet<ReleaseScheduleDetailLog>();
		}

		this.releaseScheduleDetailLog.add(new ReleaseScheduleDetailLog(storeStorage, productMaster));
	}

	public ReleaseScheduleLog(TempForceReleaseBoxHeader header, BoxInfo boxInfo, UserInfo userInfo) {
		this.erpReleaseDate = CalendarUtil.convertFormat("yyyyMMdd");
		this.boxInfo = boxInfo;
		this.orderType = "storeRelease";
		this.createDate = CalendarUtil.convertFormat("yyyyMMdd");
		this.regDate = new Date();
		this.regUserInfo = userInfo;
		this.updDate = new Date();
		this.updUserInfo = userInfo;
		this.releaseYn = "Y";
		this.releaseDate = new Date();
		this.completeYn = "N";
		this.completeBatchYn = "N";
		this.disuseYn = "N";
		this.mappingYn = header.getFlag().equals("E") ? "N" : "Y";
		this.erpCompleteYn = "N";
		this.releaseScheduleDetailLog = new HashSet<ReleaseScheduleDetailLog>();

		for(TempForceReleaseBoxStyle style : header.getStyleList()) {

			if(style.getFlag().equals("S")) {
				continue;
			}
			this.releaseScheduleDetailLog.add(new ReleaseScheduleDetailLog(header, style));
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReleaseScheduleLog that = (ReleaseScheduleLog) o;
		return Objects.equals(boxInfo.getBarcode(), that.boxInfo.getBarcode());
	}
}
