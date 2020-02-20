package com.systemk.spyder.Entity.Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Response.InventoryScheduleStyleResult;
import com.systemk.spyder.Dto.Response.InventoryScheduleTagResult;
import com.systemk.spyder.Entity.External.RfidMd14If;
import com.systemk.spyder.Entity.Main.View.View;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@ApiModel(description = "재고조사 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryScheduleStyle {

	// 스타일 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({View.Public.class})
    private Long inventoryScheduleStyleSeq;

	// 스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String style;

	// 컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String color;

	// 사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String size;

	// 수량
	//@ApiModelProperty(notes = "입력 수량")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long amount;

	// 실사 수량
	//@ApiModelProperty(notes = "확정 수량")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long completeAmount;

	// 망실 수량
	//@ApiModelProperty(notes = "망실 수량")
	@JsonView({View.Public.class, View.MobileDetail.class})
	private Long disuseAmount;

	// erp 키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	@JsonView({View.Public.class, View.MobileDetail.class})
	private String erpKey;

	// RFID 여부
	@Column(nullable = true, length = 1)
	private String rfidYn;

	// 신규 여부(S: 신규 스타일)
	@Column(nullable = true, length = 1)
	private String flag;

	// RFID 태그 목록
	//@ApiModelProperty(notes = "RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "inventoryScheduleStyleSeq")
	@JsonView({View.Public.class, View.MobileDetail.class})
	public Set<InventoryScheduleTag> tagList;

	// PDA 유니크 키
	@Column(nullable = true, length = 50)
	private String pdaKey;

	// PDA 작업 수정일
	private Date updDate;

	// 헤더 일련번호
	//@ApiModelProperty(notes = "헤더 일련번호")
	private Long inventoryScheduleHeaderSeq;

	public Long getInventoryScheduleStyleSeq() {
		return inventoryScheduleStyleSeq;
	}

	public void setInventoryScheduleStyleSeq(Long inventoryScheduleStyleSeq) {
		this.inventoryScheduleStyleSeq = inventoryScheduleStyleSeq;
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

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public Set<InventoryScheduleTag> getRfidTagList() {
		return tagList;
	}

	public void setRfidTagList(Set<InventoryScheduleTag> rfidTagList) {
		this.tagList = rfidTagList;
	}

	public Long getInventoryScheduleHeaderSeq() {
		return inventoryScheduleHeaderSeq;
	}

	public void setInventoryScheduleHeaderSeq(Long inventoryScheduleHeaderSeq) {
		this.inventoryScheduleHeaderSeq = inventoryScheduleHeaderSeq;
	}

	public Long getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(Long completeAmount) {
		this.completeAmount = completeAmount;
	}

	public Long getDisuseAmount() {
		return disuseAmount;
	}

	public void setDisuseAmount(Long disuseAmount) {
		this.disuseAmount = disuseAmount;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPdaKey() {
		return pdaKey;
	}

	public void setPdaKey(String pdaKey) {
		this.pdaKey = pdaKey;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getRfidYn() {
		return rfidYn;
	}

	public void setRfidYn(String rfidYn) {
		this.rfidYn = rfidYn;
	}

	public InventoryScheduleStyle() {

	}

	public InventoryScheduleStyle(InventoryScheduleStyleResult result) {
		this.style = result.getStyle();
		this.color = result.getColor();
		this.size = result.getSize();
		this.erpKey = result.getErpKey();
		this.rfidYn = result.getRfidYn();
		this.flag = result.getFlag();
		this.amount = result.getAmount();
		this.completeAmount = result.getCompleteAmount();
		this.disuseAmount = result.getDisuseAmount();
		this.tagList = new HashSet<InventoryScheduleTag>();

		for(InventoryScheduleTagResult tag : result.getTagList()) {
			this.tagList.add(new InventoryScheduleTag(tag));
		}
	}

	public InventoryScheduleStyle(RfidMd14If obj, ProductMaster productMaster){
		this.style 				= obj.getKey().getMd14Styl();
		this.color 				= obj.getKey().getMd14Stcd().substring(0, 3);
		this.size 				= obj.getKey().getMd14Stcd().substring(3, 6);
		this.erpKey 			= productMaster == null ? "-" : productMaster.getErpKey();
		this.rfidYn				= productMaster == null ? "N" : "Y";
		this.amount 			= obj.getMd14Mjlq().longValue();
		this.completeAmount		= 0L;
		this.disuseAmount 		= obj.getMd14Mjlq().longValue();
	}

	public void copyData(InventoryScheduleStyleResult result) {
		this.flag = result.getFlag();
		this.amount = result.getAmount();
		this.completeAmount = result.getCompleteAmount();
		this.disuseAmount = result.getDisuseAmount();

		this.tagList.clear();

		for(InventoryScheduleTagResult tag : result.getTagList()) {
			this.tagList.add(new InventoryScheduleTag(tag));
		}
	}

	public void init(){
		this.flag = null;
		this.completeAmount = 0L;
		this.disuseAmount = this.amount;

		this.tagList.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InventoryScheduleStyle style1 = (InventoryScheduleStyle) o;
		return Objects.equals(style, style1.style) &&
				Objects.equals(color, style1.color) &&
				Objects.equals(size, style1.size);
	}

	@Override
	public int hashCode() {
		return Objects.hash(style, color, size);
	}
}
