package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidIb10IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_lb10_if")
public class RfidIb10If implements Serializable{

	private static final long serialVersionUID = -3941517570249397485L;

	@EmbeddedId
	private RfidIb10IfKey key;
	// 스타일
	@Column(name="lb10_styl")
	private String lb10Styl;

	// 나머지 제품코드
	@Column(name="lb10_stcd")
	private String lb10Stcd;

	// 제지차수
	@Column(name="lb10_jjch")
	private String lb10Jjch;

	// 출고작업 일자
	@Column(name="lb10_jbdt")
	private String lb10Jbdt;

	// 생산업체
	@Column(name="lb10_prod")
	private String lb10Prod;

	// 생산업체-코너코드
	@Column(name="lb10_prco")
	private String lb10Prco;

	// 입고예정수량
	@Column(name="lb10_cfqt")
	private BigDecimal lb10Cfqt;

	// 비고
	@Column(name="lb10_bigo")
	private String lb10Bigo;

	// 처리 여부
	@Column(name="lb10_tryn")
	private String lb10Tryn;

	// 처리 일자
	@Column(name="lb10_trdt")
	private String lb10Trdt;

	// 처리 시분
	@Column(name="lb10_trmh")
	private String lb10Trmh;

	// 오류
	@Column(name="lb10_errt")
	private String lb10Errt;

	// 입력일시
	@Column(name="lb10_endt")
	private String lb10Endt;

	// 입력ID
	@Column(name="lb10_enid")
	private String lb10Enid;

	// BL 번호
	@Column(name="lb10_blno")
	private String lb10Blno;

	// 상태
	@Column(name="lb10_stat")
	private String lb10Stat;

	// 수정ID
	@Column(name="lb10_upid")
	private String lb10Upid;

	// 수정일시
	@Column(name="lb10_updt")
	private String lb10Updt;

	public String getLb10Styl() {
		return lb10Styl;
	}

	public void setLb10Styl(String lb10Styl) {
		this.lb10Styl = lb10Styl;
	}

	public String getLb10Stcd() {
		return lb10Stcd;
	}

	public void setLb10Stcd(String lb10Stcd) {
		this.lb10Stcd = lb10Stcd;
	}

	public String getLb10Jjch() {
		return lb10Jjch;
	}

	public void setLb10Jjch(String lb10Jjch) {
		this.lb10Jjch = lb10Jjch;
	}

	public String getLb10Prod() {
		return lb10Prod;
	}

	public void setLb10Prod(String lb10Prod) {
		this.lb10Prod = lb10Prod;
	}

	public String getLb10Prco() {
		return lb10Prco;
	}

	public void setLb10Prco(String lb10Prco) {
		this.lb10Prco = lb10Prco;
	}

	public BigDecimal getLb10Cfqt() {
		return lb10Cfqt;
	}

	public void setLb10Cfqt(BigDecimal lb10Cfqt) {
		this.lb10Cfqt = lb10Cfqt;
	}

	public String getLb10Bigo() {
		return lb10Bigo;
	}

	public void setLb10Bigo(String lb10Bigo) {
		this.lb10Bigo = lb10Bigo;
	}

	public String getLb10Tryn() {
		return lb10Tryn;
	}

	public void setLb10Tryn(String lb10Tryn) {
		this.lb10Tryn = lb10Tryn;
	}

	public String getLb10Trdt() {
		return lb10Trdt;
	}

	public void setLb10Trdt(String lb10Trdt) {
		this.lb10Trdt = lb10Trdt;
	}

	public String getLb10Trmh() {
		return lb10Trmh;
	}

	public void setLb10Trmh(String lb10Trmh) {
		this.lb10Trmh = lb10Trmh;
	}

	public String getLb10Errt() {
		return lb10Errt;
	}

	public void setLb10Errt(String lb10Errt) {
		this.lb10Errt = lb10Errt;
	}

	public String getLb10Endt() {
		return lb10Endt;
	}

	public void setLb10Endt(String lb10Endt) {
		this.lb10Endt = lb10Endt;
	}

	public String getLb10Enid() {
		return lb10Enid;
	}

	public void setLb10Enid(String lb10Enid) {
		this.lb10Enid = lb10Enid;
	}

	public String getLb10Jbdt() {
		return lb10Jbdt;
	}

	public void setLb10Jbdt(String lb10Jbdt) {
		this.lb10Jbdt = lb10Jbdt;
	}

	public String getLb10Blno() {
		return lb10Blno;
	}

	public void setLb10Blno(String lb10Blno) {
		this.lb10Blno = lb10Blno;
	}

	public RfidIb10IfKey getKey() {
		return key;
	}

	public void setKey(RfidIb10IfKey key) {
		this.key = key;
	}

	public String getLb10Stat() {
		return lb10Stat;
	}

	public void setLb10Stat(String lb10Stat) {
		this.lb10Stat = lb10Stat;
	}

	public String getLb10Upid() {
		return lb10Upid;
	}

	public void setLb10Upid(String lb10Upid) {
		this.lb10Upid = lb10Upid;
	}

	public String getLb10Updt() {
		return lb10Updt;
	}

	public void setLb10Updt(String lb10Updt) {
		this.lb10Updt = lb10Updt;
	}
}
