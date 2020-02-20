package com.systemk.spyder.Entity.External;

import com.systemk.spyder.Entity.External.Key.RfidLe10IfKey;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_le10_if")
public class RfidLe10If implements Serializable{

	private static final long serialVersionUID = -1244662902177363338L;

	@EmbeddedId
	private RfidLe10IfKey key;

	// 추가구분('': 기존 출고분, 'A': 매장추가분)
	@Column(name="le10_gubn")
	private String le10Gubn;

	// 출고수량
	@Column(name="le10_chqt")
	private BigDecimal le10Chqt;

	// 비고
	@Column(name="le10_note")
	private String le10Note;

	// 변경상태
	@Column(name="le10_stat")
	private String le10Stat;

	// 등록일시
	@Column(name="le10_endt")
	private Date le10Endt;

	// 전송여부
	@Column(name="le10_tryn")
	private String le10Tryn;

	// 전송일시
	@Column(name="le10_trdt")
	private Date le10Trdt;

	// 매장확인수량
	@Column(name="le10_mfqt")
	private BigDecimal le10Mfqt;

	// 매장확정여부
	@Column(name="le10_mfyn")
	private String le10Mfyn;

	// 매장확정일자
	@Column(name="le10_mfdt")
	private String le10Mfdt;

	// 수정일시
	@Column(name="le10_updt")
	private Date le10Updt;

	public String getLe10Gubn() {
		return le10Gubn;
	}

	public void setLe10Gubn(String le10Gubn) {
		this.le10Gubn = le10Gubn;
	}

	public BigDecimal getLe10Chqt() {
		return le10Chqt;
	}

	public void setLe10Chqt(BigDecimal le10Chqt) {
		this.le10Chqt = le10Chqt;
	}

	public String getLe10Note() {
		return le10Note;
	}

	public void setLe10Note(String le10Note) {
		this.le10Note = le10Note;
	}

	public String getLe10Stat() {
		return le10Stat;
	}

	public void setLe10Stat(String le10Stat) {
		this.le10Stat = le10Stat;
	}

	public Date getLe10Endt() {
		return le10Endt;
	}

	public void setLe10Endt(Date le10Endt) {
		this.le10Endt = le10Endt;
	}

	public String getLe10Tryn() {
		return le10Tryn;
	}

	public void setLe10Tryn(String le10Tryn) {
		this.le10Tryn = le10Tryn;
	}

	public Date getLe10Trdt() {
		return le10Trdt;
	}

	public void setLe10Trdt(Date le10Trdt) {
		this.le10Trdt = le10Trdt;
	}

	public BigDecimal getLe10Mfqt() {
		return le10Mfqt;
	}

	public void setLe10Mfqt(BigDecimal le10Mfqt) {
		this.le10Mfqt = le10Mfqt;
	}

	public String getLe10Mfyn() {
		return le10Mfyn;
	}

	public void setLe10Mfyn(String le10Mfyn) {
		this.le10Mfyn = le10Mfyn;
	}

	public String getLe10Mfdt() {
		return le10Mfdt;
	}

	public void setLe10Mfdt(String le10Mfdt) {
		this.le10Mfdt = le10Mfdt;
	}

	public Date getLe10Updt() {
		return le10Updt;
	}

	public void setLe10Updt(Date le10Updt) {
		this.le10Updt = le10Updt;
	}

	public RfidLe10IfKey getKey() {
		return key;
	}

	public void setKey(RfidLe10IfKey key) {
		this.key = key;
	}

	public RfidLe10If() {

	}

	public void update(ReleaseScheduleDetailLog detailLog) {
		this.le10Mfqt = new BigDecimal(detailLog.getReleaseAmount());
		this.le10Updt = new Date();

		// 차이 수량 계산
		Long calc = detailLog.getAmount() - detailLog.getReleaseAmount();
		this.le10Note = calc == 0 ? "" : calc.toString();
	}

	public void init() {
		this.le10Mfqt = new BigDecimal(0);
		this.le10Updt = null;
		this.le10Note = "";
	}

	public RfidLe10If(ReleaseScheduleLog scheduleLog, ReleaseScheduleDetailLog detailLog, Long erpReleaseSeq) {
		this.key = new RfidLe10IfKey(scheduleLog.getErpReleaseDate(),
									scheduleLog.getBoxInfo().getBarcode(),
									erpReleaseSeq,
								    scheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode(),
									scheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode(),
									detailLog.getStyle(), detailLog.getColor(), detailLog.getSize());
		this.le10Gubn = "A";
		this.le10Chqt = new BigDecimal(detailLog.getAmount());
		this.le10Stat = "C";
		this.le10Endt = new Date();
		this.le10Tryn = "Y";
		this.le10Trdt = new Date();
		this.le10Mfqt = new BigDecimal(detailLog.getReleaseAmount());
		this.le10Mfyn = "N";
		this.le10Mfdt = "";
		this.le10Updt = new Date();

		// 차이 수량 계산
		Long calc = detailLog.getAmount() - detailLog.getReleaseAmount();
		this.le10Note = calc == 0 ? "" : calc.toString();
	}
}
