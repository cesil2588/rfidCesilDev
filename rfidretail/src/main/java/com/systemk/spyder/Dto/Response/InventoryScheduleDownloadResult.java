package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;

/**
 * 재고조사 다운로드 DTO
 * @author escho
 *
 */
public class InventoryScheduleDownloadResult implements Serializable{

	private static final long serialVersionUID = -1726647866960759285L;

	// 일련번호
	private Long inventoryScheduleSeq;

	// 날짜
	private String createDate;

	// 업체 명
	private String companyName;

	// 제목
    private String explanatory;

    // 스타일
    private String style;

    // 컬러
    private String color;

    // 사이즈
    private String size;

    // 전산 수량
    private Long amount;

    // 실사 수량
    private Long completeAmount;

    // 망실 수량
    private Long disuseAmount;

    // 신규 스타일 여부(null: 작업 안함, N: 변경 없음, T: 추가 태그, S: 신규 스타일)
    private String flag;

	public Long getInventoryScheduleSeq() {
		return inventoryScheduleSeq;
	}

	public void setInventoryScheduleSeq(Long inventoryScheduleSeq) {
		this.inventoryScheduleSeq = inventoryScheduleSeq;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
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

	public InventoryScheduleDownloadResult() {

	}

	public InventoryScheduleDownloadResult(InventoryScheduleHeader header, InventoryScheduleStyle style) {
		this.inventoryScheduleSeq = header.getInventoryScheduleHeaderSeq();
		this.createDate = header.getCreateDate();
		this.companyName = header.getCompanyInfo().getName();
		this.explanatory = header.getExplanatory();
		this.flag = header.getFlag();
		this.style = style.getStyle();
		this.color = style.getColor();
		this.size = style.getSize();
		this.amount = style.getAmount();
		this.completeAmount = style.getCompleteAmount();
		this.disuseAmount = style.getDisuseAmount();
		this.flag = style.getFlag();
	}
}
