package com.systemk.spyder.Entity.External.Key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class RfidLe10IfKey implements Serializable{

	private static final long serialVersionUID = -6729476935743326624L;

	// 출고일자
	@Column(name="le10_chdt")
	private String le10Chdt;

	// 출고 시퀀스
	@Column(name="le10_chsq")
	private BigDecimal le10Chsq;

	// 매장 코드
	@Column(name="le10_gras")
	private String le10Gras;

	// 매장 코너코드
	@Column(name="le10_grco")
	private String le10Grco;

	// 박스번호
	@Column(name="le10_bxno")
	private String le10Bxno;

	// 스타일
	@Column(name="le10_styl")
	private String le10Styl;

	// 나머지 제품코드
	@Column(name="le10_stcd")
	private String le10Stcd;

	public String getLe10Chdt() {
		return le10Chdt;
	}

	public void setLe10Chdt(String le10Chdt) {
		this.le10Chdt = le10Chdt;
	}

	public String getLe10Bxno() {
		return le10Bxno;
	}

	public void setLe10Bxno(String le10Bxno) {
		this.le10Bxno = le10Bxno;
	}

	public BigDecimal getLe10Chsq() {
		return le10Chsq;
	}

	public void setLe10Chsq(BigDecimal le10Chsq) {
		this.le10Chsq = le10Chsq;
	}

	public String getLe10Gras() {
		return le10Gras;
	}

	public void setLe10Gras(String le10Gras) {
		this.le10Gras = le10Gras;
	}

	public String getLe10Grco() {
		return le10Grco;
	}

	public void setLe10Grco(String le10Grco) {
		this.le10Grco = le10Grco;
	}

	public String getLe10Styl() {
		return le10Styl;
	}

	public void setLe10Styl(String le10Styl) {
		this.le10Styl = le10Styl;
	}

	public String getLe10Stcd() {
		return le10Stcd;
	}

	public void setLe10Stcd(String le10Stcd) {
		this.le10Stcd = le10Stcd;
	}

	public RfidLe10IfKey() {

	}

	public RfidLe10IfKey(String le10Chdt,
						 String le10Bxno,
						 Long le10Chsq,
						 String le10Gras,
						 String le10Grco,
						 String le10Styl,
						 String le10Color,
						 String le10Size) {
		this.le10Chdt = le10Chdt;
		this.le10Bxno = le10Bxno;
		this.le10Chsq = new BigDecimal(le10Chsq);
		this.le10Gras = le10Gras;
		this.le10Grco = le10Grco;
		this.le10Styl = le10Styl;
		this.le10Stcd = le10Color + le10Size;
	}
}
