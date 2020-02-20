package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.systemk.spyder.Dto.Response.StoreScheduleStyleResult;
import com.systemk.spyder.Dto.Response.StoreScheduleTagResult;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxHeader;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxStyle;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxTag;
import com.systemk.spyder.Entity.External.RfidLe10If;

//@ApiModel(description = "물류출고예정 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReleaseScheduleDetailLog implements Serializable{

	private static final long serialVersionUID = -1856220004225585696L;

	// 물류출고예정 상세 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long releaseScheduleDetailLogSeq;

	// reference번호
	//@ApiModelProperty(notes = "ReferenceNo")
	@Column(nullable = true, length = 17)
	private String referenceNo;

	// 바코드
	//@ApiModelProperty(notes = "박스 바코드")
	@Column(nullable = false, length = 18)
	private String barcode;

	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	private String style;

	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 20)
	private String color;

	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 20)
	private String size;

	// 수량
	//@ApiModelProperty(notes = "수량")
	private Long amount;

	// RFID 부착 여부
	//@ApiModelProperty(notes = "RFID 부착 여부(Y, N)")
	@Column(nullable = false, length = 1)
	public String rfidYn;

	// 물류출고예정 RFID 목록
	//@ApiModelProperty(notes = "물류출고예정 RFID 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "releaseScheduleDetailLogSeq")
	private Set<ReleaseScheduleSubDetailLog> releaseScheduleSubDetailLog;

	// ERP 키
	@Column(nullable = true, length = 10)
	public String erpKey;

	// ERP 물류출고예정정보
	//@ApiModelProperty(notes = "ERP 물류출고예정 정보")
	private Long erpStoreScheduleSeq;

	// 플래그(S: 신규 스타일, T: 신규 태그 추가, N: 변경 없음)
	private String flag;

	// 출고 수량
	private Long releaseAmount;

	public Long getReleaseScheduleDetailLogSeq() {
		return releaseScheduleDetailLogSeq;
	}

	public void setReleaseScheduleDetailLogSeq(Long releaseScheduleDetailLogSeq) {
		this.releaseScheduleDetailLogSeq = releaseScheduleDetailLogSeq;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Set<ReleaseScheduleSubDetailLog> getReleaseScheduleSubDetailLog() {
		return releaseScheduleSubDetailLog;
	}

	public void setReleaseScheduleSubDetailLog(Set<ReleaseScheduleSubDetailLog> releaseScheduleSubDetailLog) {
		this.releaseScheduleSubDetailLog = releaseScheduleSubDetailLog;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Long getErpStoreScheduleSeq() {
		return erpStoreScheduleSeq;
	}

	public void setErpStoreScheduleSeq(Long erpStoreScheduleSeq) {
		this.erpStoreScheduleSeq = erpStoreScheduleSeq;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Long releaseAmount) {
		this.releaseAmount = releaseAmount;
	}

	public ReleaseScheduleDetailLog() {

	}

	public ReleaseScheduleDetailLog(String barcode, StoreScheduleStyleResult style) {
		this.barcode = barcode;
		this.style = style.getStyle();
		this.color = style.getColor();
		this.size = style.getSize();
		this.amount = style.getAmount();
		this.releaseAmount = style.getAmount();
		this.rfidYn = style.getRfidYn();
		this.erpKey = style.getErpKey();
		this.flag  = style.getFlag();

		this.releaseScheduleSubDetailLog = new HashSet<ReleaseScheduleSubDetailLog>();

		for(StoreScheduleTagResult tag : style.getTagList()) {
			this.releaseScheduleSubDetailLog.add(new ReleaseScheduleSubDetailLog(tag));
		}
	}

	public ReleaseScheduleDetailLog(RfidLe10If storeStorage, ProductMaster productMaster) {
		this.barcode = storeStorage.getKey().getLe10Bxno();
		this.style = storeStorage.getKey().getLe10Styl();
		this.color = storeStorage.getKey().getLe10Stcd().substring(0, 3);
		this.size = storeStorage.getKey().getLe10Stcd().substring(3, 6);
		this.amount = storeStorage.getLe10Chqt().longValue();
		this.rfidYn = productMaster != null ? "Y" : "N";
		this.erpKey = productMaster != null ? productMaster.getErpKey() : "-";
	}

	public ReleaseScheduleDetailLog(TempForceReleaseBoxHeader header, TempForceReleaseBoxStyle style) {
		this.barcode = header.getBoxBarcode();
		this.style = style.getStyle();
		this.color = style.getColor();
		this.size = style.getSize();
		this.amount = style.getAmount();
		this.rfidYn = style.getRfidYn();
		this.erpKey = style.getErpKey();

		this.releaseScheduleSubDetailLog = new HashSet<ReleaseScheduleSubDetailLog>();

		for(TempForceReleaseBoxTag tag : style.getTagList()) {
			if(header.getFlag().equals("E")) {
				continue;
			}
			this.releaseScheduleSubDetailLog.add(new ReleaseScheduleSubDetailLog(tag));
		}
	}
}
