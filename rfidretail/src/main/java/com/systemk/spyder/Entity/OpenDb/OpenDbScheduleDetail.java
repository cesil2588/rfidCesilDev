package com.systemk.spyder.Entity.OpenDb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleDetailKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="OP_OM01002")
public class OpenDbScheduleDetail implements Serializable{

	private static final long serialVersionUID = -6056787546998890220L;

	@EmbeddedId
	private OpenDbScheduleDetailKey openDbScheduleDetailKey;

	// 아이템코드
	@Column(name="CSN_ITEM_CD", nullable = true, length = 40)
    private String csnItemCd;

	// 배치번호
	@Column(name="CSN_BAT_NO", nullable = true, length = 10)
    private String csnBatNo;

	// LOT번호
	@Column(name="LOT_NO", nullable = true, length = 40)
    private String lotNo;

	// 아이템등급코드
	@Column(name="ITEM_GRADE_CD", nullable = true, length = 10)
    private String itemGradeCd;

	// 구분코드
	@Column(name="SCTN_CD", nullable = true, length = 10)
    private String sctnCd;

	// 거절사유코드
	@Column(name="RJCT_REASON_CD", nullable = true, length = 10)
    private String rjctReasonCd;

	// 판매단가
	@Column(name="SALES_UPRICE")
    private Long salesUprice;

	// 오더수량
	@Column(name="ORD_QTY")
    private Long ordQty;
	
	// 수량단위코드
	@Column(name="QTY_UNIT_CD", nullable = true, length = 10)
    private String qtyUnitCd;

	// 오더중량
	@Column(name="ORD_WGT")
    private String ordWgt;

	// 중량단위코드
	@Column(name="WGT_UNIT_CD", nullable = true, length = 10)
    private String wgtUnitCd;

	// 납기요청일자
	@Column(name="APTD_REQ_YMD", nullable = true, length = 8)
    private String aptdReqYmd;

	// 납기요청시분
	@Column(name="APTD_REQ_HM", nullable = true, length = 4)
    private String aptdReqHm;
	
	// CBM
	@Column(name="CBM")
	private String cbm;
    
	// 고객참조번호
	@Column(name="CUST_REF_NO", nullable = true, length = 50)
	private String custRefNo;
	
	// 비고
	@Column(name="RMK", nullable = true, length = 500)
	private String rmk;
	
	// 삭제여부
	@Column(name="DEL_YN", nullable = false, length = 1)
	private String delYn;
	
	// 인터페이스여부
	@Column(name="IF_YN", nullable = false, length = 1)
	private String ifYn;
	
	// 등록자 ID
	@Column(name="REGR_ID", nullable = true, length = 10)
	private String regrId;
	
	// 등록일
	@Column(name="REG_DATE", nullable = true, length = 20)
	private String regDate;
	
	// 수정자ID
	@Column(name="MDFR_ID", nullable = true, length = 10)
	private String mdfrId;
	
	// 수정일시
	@Column(name="MDF_DATE", nullable = true, length = 20)
	private String mdfDate;
	
	// 예비1
	@Column(name="COL01", nullable = true, length = 100)
	private String col01;
	
	// 예비2
	@Column(name="COL02", nullable = true, length = 100)
	private String col02;
		
	// 예비3
	@Column(name="COL03", nullable = true, length = 100)
	private String col03;
		
	// 예비4
	@Column(name="COL04", nullable = true, length = 100)
	private String col04;
		
	// 예비5
	@Column(name="COL05", nullable = true, length = 100)
	private String col05;

	public String getCsnItemCd() {
		return csnItemCd;
	}

	public void setCsnItemCd(String csnItemCd) {
		this.csnItemCd = csnItemCd;
	}

	public String getCsnBatNo() {
		return csnBatNo;
	}

	public void setCsnBatNo(String csnBatNo) {
		this.csnBatNo = csnBatNo;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getItemGradeCd() {
		return itemGradeCd;
	}

	public void setItemGradeCd(String itemGradeCd) {
		this.itemGradeCd = itemGradeCd;
	}

	public String getSctnCd() {
		return sctnCd;
	}

	public void setSctnCd(String sctnCd) {
		this.sctnCd = sctnCd;
	}

	public String getRjctReasonCd() {
		return rjctReasonCd;
	}

	public void setRjctReasonCd(String rjctReasonCd) {
		this.rjctReasonCd = rjctReasonCd;
	}

	public Long getSalesUprice() {
		return salesUprice;
	}

	public void setSalesUprice(Long salesUprice) {
		this.salesUprice = salesUprice;
	}

	public Long getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(Long ordQty) {
		this.ordQty = ordQty;
	}

	public String getQtyUnitCd() {
		return qtyUnitCd;
	}

	public void setQtyUnitCd(String qtyUnitCd) {
		this.qtyUnitCd = qtyUnitCd;
	}

	public String getOrdWgt() {
		return ordWgt;
	}

	public void setOrdWgt(String ordWgt) {
		this.ordWgt = ordWgt;
	}

	public String getWgtUnitCd() {
		return wgtUnitCd;
	}

	public void setWgtUnitCd(String wgtUnitCd) {
		this.wgtUnitCd = wgtUnitCd;
	}

	public String getAptdReqYmd() {
		return aptdReqYmd;
	}

	public void setAptdReqYmd(String aptdReqYmd) {
		this.aptdReqYmd = aptdReqYmd;
	}

	public String getAptdReqHm() {
		return aptdReqHm;
	}

	public void setAptdReqHm(String aptdReqHm) {
		this.aptdReqHm = aptdReqHm;
	}

	public String getCbm() {
		return cbm;
	}

	public void setCbm(String cbm) {
		this.cbm = cbm;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getIfYn() {
		return ifYn;
	}

	public void setIfYn(String ifYn) {
		this.ifYn = ifYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getMdfrId() {
		return mdfrId;
	}

	public void setMdfrId(String mdfrId) {
		this.mdfrId = mdfrId;
	}

	public String getMdfDate() {
		return mdfDate;
	}

	public void setMdfDate(String mdfDate) {
		this.mdfDate = mdfDate;
	}

	public String getCol01() {
		return col01;
	}

	public void setCol01(String col01) {
		this.col01 = col01;
	}

	public String getCol02() {
		return col02;
	}

	public void setCol02(String col02) {
		this.col02 = col02;
	}

	public String getCol03() {
		return col03;
	}

	public void setCol03(String col03) {
		this.col03 = col03;
	}

	public String getCol04() {
		return col04;
	}

	public void setCol04(String col04) {
		this.col04 = col04;
	}

	public String getCol05() {
		return col05;
	}

	public void setCol05(String col05) {
		this.col05 = col05;
	}

	public OpenDbScheduleDetailKey getOpenDbScheduleDetailKey() {
		return openDbScheduleDetailKey;
	}

	public void setOpenDbScheduleDetailKey(OpenDbScheduleDetailKey openDbScheduleDetailKey) {
		this.openDbScheduleDetailKey = openDbScheduleDetailKey;
	}

	public String getRegrId() {
		return regrId;
	}

	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}

	@Override
	public String toString() {
		return "OpenDbScheduleDetail [openDbScheduleDetailKey=" + openDbScheduleDetailKey + ", csnItemCd=" + csnItemCd
				+ ", csnBatNo=" + csnBatNo + ", lotNo=" + lotNo + ", itemGradeCd=" + itemGradeCd + ", sctnCd=" + sctnCd
				+ ", rjctReasonCd=" + rjctReasonCd + ", salesUprice=" + salesUprice + ", ordQty=" + ordQty
				+ ", qtyUnitCd=" + qtyUnitCd + ", ordWgt=" + ordWgt + ", wgtUnitCd=" + wgtUnitCd + ", aptdReqYmd="
				+ aptdReqYmd + ", aptdReqHm=" + aptdReqHm + ", cbm=" + cbm + ", custRefNo=" + custRefNo + ", rmk=" + rmk
				+ ", delYn=" + delYn + ", ifYn=" + ifYn + ", regrId=" + regrId + ", regDate=" + regDate + ", mdfrId="
				+ mdfrId + ", mdfDate=" + mdfDate + ", col01=" + col01 + ", col02=" + col02 + ", col03=" + col03
				+ ", col04=" + col04 + ", col05=" + col05 + "]";
	}

}
