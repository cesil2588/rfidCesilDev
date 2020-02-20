package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidIb01IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_lb01_if")
public class RfidIb01If implements Serializable{

	private static final long serialVersionUID = -2961479231550810314L;
	
	@EmbeddedId
	private RfidIb01IfKey key;
	
	// 업무 구분
	@Column(name="lb01_emgb")
	private String lb01Emgb;
	
	// 입고 창고
	@Column(name="lb01_cgcd")
	private String lb01Cgcd;
	
	// 입고 창고 코너
	@Column(name="lb01_cgco")
	private String lb01Cgco;
	
	// 생산 업체
	@Column(name="lb01_prod")
	private String lb01Prod;
	
	// 생산 업체 코너
	@Column(name="lb01_prco")
	private String lb01Prco;
	
	// 스타일
	@Column(name="lb01_styl")
	private String lb01Styl;
	
	// 나머지 제품 코드
	@Column(name="lb01_stcd")
	private String lb01Stcd;
	
	// 컬러
	@Column(name="lb01_it06")
	private String lb01It06;

	// 사이즈
	@Column(name="lb01_it07")
	private String lb01It07;
	
	// 제지차수
	@Column(name="lb01_jjch")
	private String lb01Jjch;
	
	// 입고수량
	@Column(name="lb01_ipqt")
	private BigDecimal lb01Ipqt;
	
	// 전송데이터 변환 여부
	@Column(name="lb01_tryn")
	private String lb01Tryn;
	
	// 진행상태
	@Column(name="lb01_stat")
	private String lb01Stat;
	
	// 전송된 시분
	@Column(name="lb01_time")
	private String lb01Time;
	
	// 입고 일련번호
	@Column(name="lb01_ipsq")
	private BigDecimal lb01Ipsq;
	
	// 입고 시리얼
	@Column(name="lb01_ipsr")
	private BigDecimal lb01Ipsr;
	
	// 비고
	@Column(name="lb01_bigo")
	private String lb01Bigo;
	
	// invoice 번호
	@Column(name="lb01_blno")
	private String lb01Blno;
	
	// 등록 일시
	@Column(name="lb01_endt")
	private Date lb01Endt;

	public String getLb01Emgb() {
		return lb01Emgb;
	}

	public void setLb01Emgb(String lb01Emgb) {
		this.lb01Emgb = lb01Emgb;
	}

	public String getLb01Cgcd() {
		return lb01Cgcd;
	}

	public void setLb01Cgcd(String lb01Cgcd) {
		this.lb01Cgcd = lb01Cgcd;
	}

	public String getLb01Cgco() {
		return lb01Cgco;
	}

	public void setLb01Cgco(String lb01Cgco) {
		this.lb01Cgco = lb01Cgco;
	}

	public String getLb01Prod() {
		return lb01Prod;
	}

	public void setLb01Prod(String lb01Prod) {
		this.lb01Prod = lb01Prod;
	}

	public String getLb01Prco() {
		return lb01Prco;
	}

	public void setLb01Prco(String lb01Prco) {
		this.lb01Prco = lb01Prco;
	}

	public String getLb01Styl() {
		return lb01Styl;
	}

	public void setLb01Styl(String lb01Styl) {
		this.lb01Styl = lb01Styl;
	}

	public String getLb01Stcd() {
		return lb01Stcd;
	}

	public void setLb01Stcd(String lb01Stcd) {
		this.lb01Stcd = lb01Stcd;
	}

	public String getLb01It06() {
		return lb01It06;
	}

	public void setLb01It06(String lb01It06) {
		this.lb01It06 = lb01It06;
	}

	public String getLb01It07() {
		return lb01It07;
	}

	public void setLb01It07(String lb01It07) {
		this.lb01It07 = lb01It07;
	}

	public String getLb01Jjch() {
		return lb01Jjch;
	}

	public void setLb01Jjch(String lb01Jjch) {
		this.lb01Jjch = lb01Jjch;
	}

	public BigDecimal getLb01Ipqt() {
		return lb01Ipqt;
	}

	public void setLb01Ipqt(BigDecimal lb01Ipqt) {
		this.lb01Ipqt = lb01Ipqt;
	}

	public String getLb01Tryn() {
		return lb01Tryn;
	}

	public void setLb01Tryn(String lb01Tryn) {
		this.lb01Tryn = lb01Tryn;
	}

	public String getLb01Stat() {
		return lb01Stat;
	}

	public void setLb01Stat(String lb01Stat) {
		this.lb01Stat = lb01Stat;
	}

	public String getLb01Time() {
		return lb01Time;
	}

	public void setLb01Time(String lb01Time) {
		this.lb01Time = lb01Time;
	}

	public BigDecimal getLb01Ipsq() {
		return lb01Ipsq;
	}

	public void setLb01Ipsq(BigDecimal lb01Ipsq) {
		this.lb01Ipsq = lb01Ipsq;
	}

	public BigDecimal getLb01Ipsr() {
		return lb01Ipsr;
	}

	public void setLb01Ipsr(BigDecimal lb01Ipsr) {
		this.lb01Ipsr = lb01Ipsr;
	}

	public String getLb01Bigo() {
		return lb01Bigo;
	}

	public void setLb01Bigo(String lb01Bigo) {
		this.lb01Bigo = lb01Bigo;
	}

	public String getLb01Blno() {
		return lb01Blno;
	}

	public void setLb01Blno(String lb01Blno) {
		this.lb01Blno = lb01Blno;
	}

	public Date getLb01Endt() {
		return lb01Endt;
	}

	public void setLb01Endt(Date lb01Endt) {
		this.lb01Endt = lb01Endt;
	}

	public RfidIb01IfKey getKey() {
		return key;
	}

	public void setKey(RfidIb01IfKey key) {
		this.key = key;
	}
}
