package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLa21IfKey;

// 온라인 출고 실적
@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_la21_if")
public class RfidLa21If implements Serializable{

	private static final long serialVersionUID = 3113370450852223258L;

	@EmbeddedId
	private RfidLa21IfKey key;

	@Column(name="la21_mjcd")
    private String la21Mjcd;

	@Column(name="la21_corn")
    private String la21Corn;

	@Column(name="la21_styl")
    private String la21Styl;

	@Column(name="la21_stcd")
    private String la21Stcd;

	@Column(name="la21_chqt")
    private BigDecimal la21Chqt;

	@Column(name="la21_chdt")
    private String la21Chdt;

	@Column(name="la21_bigo")
    private String la21Bigo;

	@Column(name="la21_comp")
    private String la21Comp;

	@Column(name="la21_sjno")
    private String la21Sjno;

	@Column(name="la21_bst1")
    private String la21Bst1;

	@Column(name="la21_tryn")
    private String la21Tryn;

	@Column(name="la21_trdt")
    private String la21Trdt;

	@Column(name="la21_trmh")
    private String la21Trmh;

	@Column(name="la21_errt")
    private String la21Errt;

	@Column(name="la21_endt")
    private String la21Endt;

	@Column(name="la21_enid")
	private String la21Enid;

	@Column(name="la21_updt")
    private String la21Updt;

	@Column(name="la21_upid")
    private String la21Upid;


    public String getLa21Mjcd() {
        return la21Mjcd;
    }

    public void setLa21Mjcd(String la21Mjcd) {
        this.la21Mjcd = la21Mjcd;
    }

    public String getLa21Corn() {
        return la21Corn;
    }

    public void setLa21Corn(String la21Corn) {
        this.la21Corn = la21Corn;
    }

    public String getLa21Styl() {
        return la21Styl;
    }

    public void setLa21Styl(String la21Styl) {
        this.la21Styl = la21Styl;
    }

    public String getLa21Stcd() {
        return la21Stcd;
    }

    public void setLa21Stcd(String la21Stcd) {
        this.la21Stcd = la21Stcd;
    }

    public BigDecimal getLa21Chqt() {
        return la21Chqt;
    }

    public void setLa21Chqt(BigDecimal la21Chqt) {
        this.la21Chqt = la21Chqt;
    }

    public String getLa21Chdt() {
        return la21Chdt;
    }

    public void setLa21Chdt(String la21Chdt) {
        this.la21Chdt = la21Chdt;
    }

    public String getLa21Bigo() {
        return la21Bigo;
    }

    public void setLa21Bigo(String la21Bigo) {
        this.la21Bigo = la21Bigo;
    }

    public String getLa21Comp() {
        return la21Comp;
    }

    public void setLa21Comp(String la21Comp) {
        this.la21Comp = la21Comp;
    }

    public String getLa21Sjno() {
        return la21Sjno;
    }

    public void setLa21Sjno(String la21Sjno) {
        this.la21Sjno = la21Sjno;
    }

    public String getLa21Bst1() {
        return la21Bst1;
    }

    public void setLa21Bst1(String la21Bst1) {
        this.la21Bst1 = la21Bst1;
    }

    public String getLa21Tryn() {
        return la21Tryn;
    }

    public void setLa21Tryn(String la21Tryn) {
        this.la21Tryn = la21Tryn;
    }

    public String getLa21Trdt() {
        return la21Trdt;
    }

    public void setLa21Trdt(String la21Trdt) {
        this.la21Trdt = la21Trdt;
    }

    public String getLa21Trmh() {
        return la21Trmh;
    }

    public void setLa21Trmh(String la21Trmh) {
        this.la21Trmh = la21Trmh;
    }

    public String getLa21Errt() {
        return la21Errt;
    }

    public void setLa21Errt(String la21Errt) {
        this.la21Errt = la21Errt;
    }

    public String getLa21Endt() {
        return la21Endt;
    }

    public void setLa21Endt(String la21Endt) {
        this.la21Endt = la21Endt;
    }

    public String getLa21Enid() {
        return la21Enid;
    }

    public void setLa21Enid(String la21Enid) {
        this.la21Enid = la21Enid;
    }

    public String getLa21Updt() {
        return la21Updt;
    }

    public void setLa21Updt(String la21Updt) {
        this.la21Updt = la21Updt;
    }

    public String getLa21Upid() {
        return la21Upid;
    }

    public void setLa21Upid(String la21Upid) {
        this.la21Upid = la21Upid;
    }

    // RfidLa21If 모델 복사
    public void CopyData(RfidLa21If param)
    {
        this.la21Mjcd = param.getLa21Mjcd();
        this.la21Corn = param.getLa21Corn();
        this.la21Styl = param.getLa21Styl();
        this.la21Stcd = param.getLa21Stcd();
        this.la21Chqt = param.getLa21Chqt();
        this.la21Chdt = param.getLa21Chdt();
        this.la21Bigo = param.getLa21Bigo();
        this.la21Comp = param.getLa21Comp();
        this.la21Sjno = param.getLa21Sjno();
        this.la21Bst1 = param.getLa21Bst1();
        this.la21Tryn = param.getLa21Tryn();
        this.la21Trdt = param.getLa21Trdt();
        this.la21Trmh = param.getLa21Trmh();
        this.la21Errt = param.getLa21Errt();
        this.la21Endt = param.getLa21Endt();
        this.la21Enid = param.getLa21Enid();
        this.la21Updt = param.getLa21Updt();
        this.la21Upid = param.getLa21Upid();
    }

	public RfidLa21IfKey getKey() {
		return key;
	}

	public void setKey(RfidLa21IfKey key) {
		this.key = key;
	}

}