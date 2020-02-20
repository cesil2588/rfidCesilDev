package com.systemk.spyder.Dto.Response;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;

/**
 * 재고조사 목록 DTO
 * @author escho
 *
 */
public class InventoryScheduleResult implements Serializable{

	private static final long serialVersionUID = -1726647866960759285L;

	// 일련번호
	private Long inventoryScheduleSeq;

	// 날짜
	private String createDate;

	// 업체 명
	private String companyName;

	// 확정 여부
	private String confirmYn;

	// 제목
    private String explanatory;

    // 작업 여부(Y, N)
    private String flag;

    // 전산재고
    private Long totalAmount;

    // 실사수량
    private Long totalCompleteAmount;

    // 망실수량
    private Long totalDisuseAmount;

    // 상세 스타일 정보
    @JsonInclude(Include.NON_DEFAULT)
    private Set<InventoryScheduleStyleResult> styleList = new HashSet<InventoryScheduleStyleResult>();

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

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public String getExplanatory() {
		return explanatory;
	}

	public void setExplanatory(String explanatory) {
		this.explanatory = explanatory;
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

	public Long getInventoryScheduleSeq() {
		return inventoryScheduleSeq;
	}

	public void setInventoryScheduleSeq(Long inventoryScheduleSeq) {
		this.inventoryScheduleSeq = inventoryScheduleSeq;
	}

	public Set<InventoryScheduleStyleResult> getStyleList() {
		return styleList;
	}

	public void setStyleList(Set<InventoryScheduleStyleResult> styleList) {
		this.styleList = styleList;
	}

	public Long getTotalDisuseAmount() {
		return totalDisuseAmount;
	}

	public void setTotalDisuseAmount(Long totalDisuseAmount) {
		this.totalDisuseAmount = totalDisuseAmount;
	}

	public InventoryScheduleResult() {

	}

	public InventoryScheduleResult(InventoryScheduleHeader header, boolean flag) {
		this.inventoryScheduleSeq = header.getInventoryScheduleHeaderSeq();
		this.createDate = header.getCreateDate();
		this.companyName = header.getCompanyInfo().getName();
		this.confirmYn = header.getConfirmYn();
		this.explanatory = header.getExplanatory();
		this.flag = header.getFlag();
		this.totalAmount = header.getTotalAmount();
		this.totalCompleteAmount = header.getTotalCompleteAmount();
		this.totalDisuseAmount = header.getTotalDisuseAmount();

		if(flag) {
			for(InventoryScheduleStyle style : header.getStyleList()) {
				this.styleList.add(new InventoryScheduleStyleResult(style));
			}
		}
	}

}
