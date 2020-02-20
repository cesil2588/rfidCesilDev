package com.systemk.spyder.Entity.OpenDb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleHeaderKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="OP_OM01001")
public class OpenDbScheduleHeader implements Serializable{

	private static final long serialVersionUID = -8133919604215440726L;

	@EmbeddedId
	private OpenDbScheduleHeaderKey openDbScheduleHeaderKey;

	// 고객오더유형코드
	@Column(name="CUST_ORD_TYPE_CD", nullable = true, length = 10)
    private String custOrdTypeCd;

	// 고객이동유형코드
	@Column(name="CUST_MOVE_TYPE_CD", nullable = true, length = 10)
    private String custMoveTypeCd;

	// 작업구분코드
	@Column(name="WORK_SCTN_CD", nullable = true, length = 10)
    private String workSctnCd;

	// 반품여부 기본값 : N
	@Column(name="RETURN_GOODS_YN", nullable = true, length = 1)
    private String returnGoodsYn;

	// 거절사유코드
	@Column(name="RJCT_REASON_CD", nullable = true, length = 10)
    private String rjctReasonCd;

	// 출발지코드
	@Column(name="DPT_AR_CD", nullable = true, length = 15)
    private String dptArCd;

	// 출발지 유형 코드
	@Column(name="DPT_AR_TYPE_CD", nullable = true, length = 10)
    private String dptArTypeCd;

	// 출발지 우편 번호
	@Column(name="DPT_AR_ZIP_CD", nullable = true, length = 6)
    private String dptArZipCd;
	
	// 출발지 우편 주소
	@Column(name="DPT_AR_ZIP_ADDR", nullable = true, length = 200)
    private String dptArZipAddr;

	// 출발지 상세 주소
	@Column(name="DPT_AR_DTL_ADDR", nullable = true, length = 300)
    private String dptArDtlAddr;

	// 시작 요청 일자
	@Column(name="STRT_REQ_YMD", nullable = true, length = 8)
    private String strtReqYmd;

	// 출발지 담당자명
	@Column(name="DPT_AR_OFCR_NM", nullable = true, length = 100)
    private String dptArOfcrNm;

	// 출발지담당자전화번호
	@Column(name="DPT_AR_OFCR_TEL_NO", nullable = true, length = 40)
    private String dptArOfcrTelNo;
	
	// 주문자 코드
	@Column(name="ORD_AR_CD", nullable = true, length = 15)
	private String ordArCd;
    
	// 주문자 유형 코드
	@Column(name="ORD_AR_TYPE_CD", nullable = true, length = 10)
	private String ordArTypeCd;
	
	// 주문자 명
	@Column(name="ORD_AR_NAME", nullable = true, length = 30)
	private String ordArName;
	
	// 주문자 전화번호
	@Column(name="ORD_AR_TEL_NO", nullable = true, length = 30)
	private String ordArTelNo;
		
	// 주문자 우편번호
	@Column(name="ORD_AR_ZIP_CD", nullable = true, length = 6)
	private String ordArZipCd;
	
	// 주문자우편주소
	@Column(name="ORD_AR_ZIP_ADDR", nullable = true, length = 300)
	private String ordArZipAddr;
	
	// 주문자상세주소
	@Column(name="ORD_AR_DTL_ADDR", nullable = true, length = 300)
	private String ordArDtlAddr;
	
	// 도착지코드
	@Column(name="ARV_AR_CD", nullable = true, length = 15)
	private String arvArCd;
	
	// 도착지유형코드
	@Column(name="ARV_AR_TYPE_CD", nullable = true, length = 10)
	private String arvArTypeCd;
	
	// 도착지우편번호
	@Column(name="ARV_AR_ZIP_CD", nullable = true, length = 6)
	private String arvArZipCd;
	
	// 도착지우편주소
	@Column(name="ARV_AR_ZIP_ADDR", nullable = true, length = 200)
	private String arvArZipAddr;
	
	// 도착지상세주소
	@Column(name="ARV_AR_DTL_ADDR", nullable = true, length = 300)
	private String arvArDtlAddr;
	
	// 납기요청일자
	@Column(name="APTD_REQ_YMD", nullable = true, length = 8)
	private String aptdReqYmd;
	
	// 납기요청시분
	@Column(name="APTD_REQ_HM", nullable = true, length = 4)
	private String aptdReqHm;
	
	// 도착지담당자명
	@Column(name="ARV_AR_OFCR_NM", nullable = true, length = 100)
	private String arvArOfcrNm;
	
	// 도착지담당자전화번호(모바일)
	@Column(name="ARV_AR_OFCR_TEL_NO", nullable = true, length = 40)
	private String arvArOfcrTelNo;
	
	// 고객거래선코드
	@Column(name="CUST_OF_CUST_CD", nullable = true, length = 15)
	private String custOfCustCd;
	
	// 공급처코드
	@Column(name="SUP_PL_CD", nullable = true, length = 15)
	private String supPlCd;
	
	// 오더수량
	@Column(name="ORD_QTY")
	private Long ordQty;
	
	// 오더중량
	@Column(name="ORD_WGT")
	private Long ordWgt;
	
	// 비고
	@Column(name="RMK", nullable = true, length = 500)
	private String rmk;
	
	// 고객참조번호
	@Column(name="CUST_REF_NO", nullable = true, length = 50)
	private String custRefNo;
	
	// 삭제여부
	@Column(name="DEL_YN", nullable = false, length = 1)
	private String delYn;
	
	// 도착지담당자전화번호(자택)
	@Column(name="COL01", nullable = true, length = 100)
	private String col01;
	
	// 운송장 번호
	@Column(name="SHIPMENT_NO", nullable = true, length = 20)
	private String shipmentNo;
	
	// 진행상태
	@Column(name="PROG_STAT_CD", nullable = true, length = 10)
	private String progStatCd;
	
	// 고객사 인터페이스여부
	@Column(name="CUST_IF_YN", nullable = false, length = 1)
	private String custIfYn;
	
	// 인터페이스여부
	@Column(name="IF_YN", nullable = false, length = 1)
	private String ifYn;
	
	// 인터페이스메세지
	@Column(name="IF_MSG", nullable = true, length = 100)
	private String ifMsg;
	
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
		
	// 예비6
	@Column(name="COL06", nullable = true, length = 100)
	private String col06;
		
	// 예비7
	@Column(name="COL07", nullable = true, length = 100)
	private String col07;
	
	// 작업장
	@Column(name="WORK_CNTR_CD", nullable = true, length = 5)
	private String workCntrCd;
	
	@Column(name="TR_IF_YN", nullable = true, length = 1)
	private String trIfYn;
	
	@Column(name="TR_DATE", nullable = true, length = 8)
	private String trDate;
	
	@Column(name="BRAND_REAL_NO", nullable = true, length = 30)
	private String brandRealNo;
	
	// 택배 취급 정보
	@Column(name="TML_CD", nullable = true, length = 20)
	private String tmlCd;
	
	// 택배 취급 정보
	@Column(name="FILT_CD", nullable = true, length = 20)
	private String filtCd;
	
	// 택배 취급 정보
	@Column(name="BRNSHP_NM", nullable = true, length = 50)
	private String brnshpNm;
	
	// 택배 취급 정보
	@Column(name="CITY_GUN_GU", nullable = true, length = 50)
	private String cityGunGu;
	
	// 택배 취급 정보
	@Column(name="DONG", nullable = true, length = 20)
	private String dong;

	
	public String getCustOrdTypeCd() {
		return custOrdTypeCd;
	}

	public void setCustOrdTypeCd(String custOrdTypeCd) {
		this.custOrdTypeCd = custOrdTypeCd;
	}

	public String getCustMoveTypeCd() {
		return custMoveTypeCd;
	}

	public void setCustMoveTypeCd(String custMoveTypeCd) {
		this.custMoveTypeCd = custMoveTypeCd;
	}

	public String getWorkSctnCd() {
		return workSctnCd;
	}

	public void setWorkSctnCd(String workSctnCd) {
		this.workSctnCd = workSctnCd;
	}

	public String getReturnGoodsYn() {
		return returnGoodsYn;
	}

	public void setReturnGoodsYn(String returnGoodsYn) {
		this.returnGoodsYn = returnGoodsYn;
	}

	public String getRjctReasonCd() {
		return rjctReasonCd;
	}

	public void setRjctReasonCd(String rjctReasonCd) {
		this.rjctReasonCd = rjctReasonCd;
	}

	public String getDptArCd() {
		return dptArCd;
	}

	public void setDptArCd(String dptArCd) {
		this.dptArCd = dptArCd;
	}

	public String getDptArTypeCd() {
		return dptArTypeCd;
	}

	public void setDptArTypeCd(String dptArTypeCd) {
		this.dptArTypeCd = dptArTypeCd;
	}

	public String getDptArZipCd() {
		return dptArZipCd;
	}

	public void setDptArZipCd(String dptArZipCd) {
		this.dptArZipCd = dptArZipCd;
	}

	public String getDptArZipAddr() {
		return dptArZipAddr;
	}

	public void setDptArZipAddr(String dptArZipAddr) {
		this.dptArZipAddr = dptArZipAddr;
	}

	public String getDptArDtlAddr() {
		return dptArDtlAddr;
	}

	public void setDptArDtlAddr(String dptArDtlAddr) {
		this.dptArDtlAddr = dptArDtlAddr;
	}

	public String getStrtReqYmd() {
		return strtReqYmd;
	}

	public void setStrtReqYmd(String strtReqYmd) {
		this.strtReqYmd = strtReqYmd;
	}

	public String getDptArOfcrNm() {
		return dptArOfcrNm;
	}

	public void setDptArOfcrNm(String dptArOfcrNm) {
		this.dptArOfcrNm = dptArOfcrNm;
	}

	public String getDptArOfcrTelNo() {
		return dptArOfcrTelNo;
	}

	public void setDptArOfcrTelNo(String dptArOfcrTelNo) {
		this.dptArOfcrTelNo = dptArOfcrTelNo;
	}

	public String getOrdArCd() {
		return ordArCd;
	}

	public void setOrdArCd(String ordArCd) {
		this.ordArCd = ordArCd;
	}

	public String getOrdArTypeCd() {
		return ordArTypeCd;
	}

	public void setOrdArTypeCd(String ordArTypeCd) {
		this.ordArTypeCd = ordArTypeCd;
	}

	public String getOrdArName() {
		return ordArName;
	}

	public void setOrdArName(String ordArName) {
		this.ordArName = ordArName;
	}

	public String getOrdArTelNo() {
		return ordArTelNo;
	}

	public void setOrdArTelNo(String ordArTelNo) {
		this.ordArTelNo = ordArTelNo;
	}

	public String getOrdArZipCd() {
		return ordArZipCd;
	}

	public void setOrdArZipCd(String ordArZipCd) {
		this.ordArZipCd = ordArZipCd;
	}

	public String getOrdArZipAddr() {
		return ordArZipAddr;
	}

	public void setOrdArZipAddr(String ordArZipAddr) {
		this.ordArZipAddr = ordArZipAddr;
	}

	public String getOrdArDtlAddr() {
		return ordArDtlAddr;
	}

	public void setOrdArDtlAddr(String ordArDtlAddr) {
		this.ordArDtlAddr = ordArDtlAddr;
	}

	public String getArvArCd() {
		return arvArCd;
	}

	public void setArvArCd(String arvArCd) {
		this.arvArCd = arvArCd;
	}

	public String getArvArTypeCd() {
		return arvArTypeCd;
	}

	public void setArvArTypeCd(String arvArTypeCd) {
		this.arvArTypeCd = arvArTypeCd;
	}

	public String getArvArZipCd() {
		return arvArZipCd;
	}

	public void setArvArZipCd(String arvArZipCd) {
		this.arvArZipCd = arvArZipCd;
	}

	public String getArvArZipAddr() {
		return arvArZipAddr;
	}

	public void setArvArZipAddr(String arvArZipAddr) {
		this.arvArZipAddr = arvArZipAddr;
	}

	public String getArvArDtlAddr() {
		return arvArDtlAddr;
	}

	public void setArvArDtlAddr(String arvArDtlAddr) {
		this.arvArDtlAddr = arvArDtlAddr;
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

	public String getArvArOfcrNm() {
		return arvArOfcrNm;
	}

	public void setArvArOfcrNm(String arvArOfcrNm) {
		this.arvArOfcrNm = arvArOfcrNm;
	}

	public String getArvArOfcrTelNo() {
		return arvArOfcrTelNo;
	}

	public void setArvArOfcrTelNo(String arvArOfcrTelNo) {
		this.arvArOfcrTelNo = arvArOfcrTelNo;
	}

	public String getCustOfCustCd() {
		return custOfCustCd;
	}

	public void setCustOfCustCd(String custOfCustCd) {
		this.custOfCustCd = custOfCustCd;
	}

	public String getSupPlCd() {
		return supPlCd;
	}

	public void setSupPlCd(String supPlCd) {
		this.supPlCd = supPlCd;
	}

	public Long getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(Long ordQty) {
		this.ordQty = ordQty;
	}

	public Long getOrdWgt() {
		return ordWgt;
	}

	public void setOrdWgt(Long ordWgt) {
		this.ordWgt = ordWgt;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getCol01() {
		return col01;
	}

	public void setCol01(String col01) {
		this.col01 = col01;
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getProgStatCd() {
		return progStatCd;
	}

	public void setProgStatCd(String progStatCd) {
		this.progStatCd = progStatCd;
	}

	public String getCustIfYn() {
		return custIfYn;
	}

	public void setCustIfYn(String custIfYn) {
		this.custIfYn = custIfYn;
	}

	public String getIfYn() {
		return ifYn;
	}

	public void setIfYn(String ifYn) {
		this.ifYn = ifYn;
	}

	public String getIfMsg() {
		return ifMsg;
	}

	public void setIfMsg(String ifMsg) {
		this.ifMsg = ifMsg;
	}

	
	public String getRegrId() {
		return regrId;
	}

	public void setRegrId(String regrId) {
		this.regrId = regrId;
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

	public String getCol06() {
		return col06;
	}

	public void setCol06(String col06) {
		this.col06 = col06;
	}

	public String getCol07() {
		return col07;
	}

	public void setCol07(String col07) {
		this.col07 = col07;
	}

	public String getWorkCntrCd() {
		return workCntrCd;
	}

	public void setWorkCntrCd(String workCntrCd) {
		this.workCntrCd = workCntrCd;
	}

	public String getTrIfYn() {
		return trIfYn;
	}

	public void setTrIfYn(String trIfYn) {
		this.trIfYn = trIfYn;
	}

	public String getTrDate() {
		return trDate;
	}

	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}

	public String getBrandRealNo() {
		return brandRealNo;
	}

	public void setBrandRealNo(String brandRealNo) {
		this.brandRealNo = brandRealNo;
	}

	public String getTmlCd() {
		return tmlCd;
	}

	public void setTmlCd(String tmlCd) {
		this.tmlCd = tmlCd;
	}

	public String getFiltCd() {
		return filtCd;
	}

	public void setFiltCd(String filtCd) {
		this.filtCd = filtCd;
	}

	public String getBrnshpNm() {
		return brnshpNm;
	}

	public void setBrnshpNm(String brnshpNm) {
		this.brnshpNm = brnshpNm;
	}

	public String getCityGunGu() {
		return cityGunGu;
	}

	public void setCityGunGu(String cityGunGu) {
		this.cityGunGu = cityGunGu;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public OpenDbScheduleHeaderKey getOpenDbScheduleHeaderKey() {
		return openDbScheduleHeaderKey;
	}

	public void setOpenDbScheduleHeaderKey(OpenDbScheduleHeaderKey openDbScheduleHeaderKey) {
		this.openDbScheduleHeaderKey = openDbScheduleHeaderKey;
	}

	@Override
	public String toString() {
		return "OpenDbScheduleHeader [openDbScheduleHeaderKey=" + openDbScheduleHeaderKey + ", custOrdTypeCd="
				+ custOrdTypeCd + ", custMoveTypeCd=" + custMoveTypeCd + ", workSctnCd=" + workSctnCd
				+ ", returnGoodsYn=" + returnGoodsYn + ", rjctReasonCd=" + rjctReasonCd + ", dptArCd=" + dptArCd
				+ ", dptArTypeCd=" + dptArTypeCd + ", dptArZipCd=" + dptArZipCd + ", dptArZipAddr=" + dptArZipAddr
				+ ", dptArDtlAddr=" + dptArDtlAddr + ", strtReqYmd=" + strtReqYmd + ", dptArOfcrNm=" + dptArOfcrNm
				+ ", dptArOfcrTelNo=" + dptArOfcrTelNo + ", ordArCd=" + ordArCd + ", ordArTypeCd=" + ordArTypeCd
				+ ", ordArName=" + ordArName + ", ordArTelNo=" + ordArTelNo + ", ordArZipCd=" + ordArZipCd
				+ ", ordArZipAddr=" + ordArZipAddr + ", ordArDtlAddr=" + ordArDtlAddr + ", arvArCd=" + arvArCd
				+ ", arvArTypeCd=" + arvArTypeCd + ", arvArZipCd=" + arvArZipCd + ", arvArZipAddr=" + arvArZipAddr
				+ ", arvArDtlAddr=" + arvArDtlAddr + ", aptdReqYmd=" + aptdReqYmd + ", aptdReqHm=" + aptdReqHm
				+ ", arvArOfcrNm=" + arvArOfcrNm + ", arvArOfcrTelNo=" + arvArOfcrTelNo + ", custOfCustCd="
				+ custOfCustCd + ", supPlCd=" + supPlCd + ", ordQty=" + ordQty + ", ordWgt=" + ordWgt + ", rmk=" + rmk
				+ ", custRefNo=" + custRefNo + ", delYn=" + delYn + ", col01=" + col01 + ", shipmentNo=" + shipmentNo
				+ ", progStatCd=" + progStatCd + ", custIfYn=" + custIfYn + ", ifYn=" + ifYn + ", ifMsg=" + ifMsg
				+ ", regrId=" + regrId + ", regDate=" + regDate + ", mdfrId=" + mdfrId + ", mdfDate=" + mdfDate
				+ ", col02=" + col02 + ", col03=" + col03 + ", col04=" + col04 + ", col05=" + col05 + ", col06=" + col06
				+ ", col07=" + col07 + ", workCntrCd=" + workCntrCd + ", trIfYn=" + trIfYn + ", trDate=" + trDate
				+ ", brandRealNo=" + brandRealNo + ", tmlCd=" + tmlCd + ", filtCd=" + filtCd + ", brnshpNm=" + brnshpNm
				+ ", cityGunGu=" + cityGunGu + ", dong=" + dong + "]";
	}
}
