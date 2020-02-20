package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidMd16IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_md16_if")
public class RfidMd16If implements Serializable{

	private static final long serialVersionUID = 4884687051980302866L;

	@EmbeddedId
	private RfidMd16IfKey key;

	@Column(name="md16_brnd")
    private String md16Brnd;

	@Column(name="md16_jpyy")
    private String md16Jpyy;

	@Column(name="md16_jpss")
    private String md16Jpss;

	@Column(name="md16_it01")
    private String md16It01;

	@Column(name="md16_it02")
    private String md16It02;

	@Column(name="md16_it03")
    private String md16It03;

	@Column(name="md16_it04")
    private String md16It04;

	@Column(name="md16_it05")
    private String md16It05;

	@Column(name="md16_it06")
    private String md16It06;

	@Column(name="md16_it07")
    private String md16It07;

	@Column(name="md16_it08")
    private String md16It08;

	@Column(name="md16_it09")
    private String md16It09;

	@Column(name="md16_it10")
    private String md16It10;

	@Column(name="md16_zlcq")
    private BigDecimal md16Zlcq;

	@Column(name="md16_sil1")
    private BigDecimal md16Sil1;

	@Column(name="md16_sil2")
    private BigDecimal md16Sil2;

	@Column(name="md16_cfqt")
    private BigDecimal md16Cfqt;

	@Column(name="md16_note")
    private String md16Note;

	@Column(name="md16_sbdt")
    private String md16Sbdt;

	@Column(name="md16_cfyn")
    private String md16Cfyn;

	@Column(name="md16_endt")
    private String md16Endt;

	@Column(name="md16_enid")
    private String md16Enid;

	@Column(name="md16_updt")
    private String md16Updt;

	@Column(name="md16_upid")
    private String md16Upid;

    public String getMd16Brnd() {
        return md16Brnd;
    }

    public void setMd16Brnd(String md16Brnd) {
        this.md16Brnd = md16Brnd;
    }

    public String getMd16Jpyy() {
        return md16Jpyy;
    }

    public void setMd16Jpyy(String md16Jpyy) {
        this.md16Jpyy = md16Jpyy;
    }

    public String getMd16Jpss() {
        return md16Jpss;
    }

    public void setMd16Jpss(String md16Jpss) {
        this.md16Jpss = md16Jpss;
    }

    public String getMd16It01() {
        return md16It01;
    }

    public void setMd16It01(String md16It01) {
        this.md16It01 = md16It01;
    }

    public String getMd16It02() {
        return md16It02;
    }

    public void setMd16It02(String md16It02) {
        this.md16It02 = md16It02;
    }

    public String getMd16It03() {
        return md16It03;
    }

    public void setMd16It03(String md16It03) {
        this.md16It03 = md16It03;
    }

    public String getMd16It04() {
        return md16It04;
    }

    public void setMd16It04(String md16It04) {
        this.md16It04 = md16It04;
    }

    public String getMd16It05() {
        return md16It05;
    }

    public void setMd16It05(String md16It05) {
        this.md16It05 = md16It05;
    }

    public String getMd16It06() {
        return md16It06;
    }

    public void setMd16It06(String md16It06) {
        this.md16It06 = md16It06;
    }

    public String getMd16It07() {
        return md16It07;
    }

    public void setMd16It07(String md16It07) {
        this.md16It07 = md16It07;
    }

    public String getMd16It08() {
        return md16It08;
    }

    public void setMd16It08(String md16It08) {
        this.md16It08 = md16It08;
    }

    public String getMd16It09() {
        return md16It09;
    }

    public void setMd16It09(String md16It09) {
        this.md16It09 = md16It09;
    }

    public String getMd16It10() {
        return md16It10;
    }

    public void setMd16It10(String md16It10) {
        this.md16It10 = md16It10;
    }

    public BigDecimal getMd16Zlcq() {
        return md16Zlcq;
    }

    public void setMd16Zlcq(BigDecimal md16Zlcq) {
        this.md16Zlcq = md16Zlcq;
    }

    public BigDecimal getMd16Sil1() {
        return md16Sil1;
    }

    public void setMd16Sil1(BigDecimal md16Sil1) {
        this.md16Sil1 = md16Sil1;
    }

    public BigDecimal getMd16Sil2() {
        return md16Sil2;
    }

    public void setMd16Sil2(BigDecimal md16Sil2) {
        this.md16Sil2 = md16Sil2;
    }

    public BigDecimal getMd16Cfqt() {
        return md16Cfqt;
    }

    public void setMd16Cfqt(BigDecimal md16Cfqt) {
        this.md16Cfqt = md16Cfqt;
    }

    public String getMd16Note() {
        return md16Note;
    }

    public void setMd16Note(String md16Note) {
        this.md16Note = md16Note;
    }

    public String getMd16Sbdt() {
        return md16Sbdt;
    }

    public void setMd16Sbdt(String md16Sbdt) {
        this.md16Sbdt = md16Sbdt;
    }

    public String getMd16Cfyn() {
        return md16Cfyn;
    }

    public void setMd16Cfyn(String md16Cfyn) {
        this.md16Cfyn = md16Cfyn;
    }

    public String getMd16Endt() {
        return md16Endt;
    }

    public void setMd16Endt(String md16Endt) {
        this.md16Endt = md16Endt;
    }

    public String getMd16Enid() {
        return md16Enid;
    }

    public void setMd16Enid(String md16Enid) {
        this.md16Enid = md16Enid;
    }

    public String getMd16Updt() {
        return md16Updt;
    }

    public void setMd16Updt(String md16Updt) {
        this.md16Updt = md16Updt;
    }

    public String getMd16Upid() {
        return md16Upid;
    }

    public void setMd16Upid(String md16Upid) {
        this.md16Upid = md16Upid;
    }

    // RfidMd16If 모델 복사
    public void CopyData(RfidMd16If param)
    {
        this.md16Brnd = param.getMd16Brnd();
        this.md16Jpyy = param.getMd16Jpyy();
        this.md16Jpss = param.getMd16Jpss();
        this.md16It01 = param.getMd16It01();
        this.md16It02 = param.getMd16It02();
        this.md16It03 = param.getMd16It03();
        this.md16It04 = param.getMd16It04();
        this.md16It05 = param.getMd16It05();
        this.md16It06 = param.getMd16It06();
        this.md16It07 = param.getMd16It07();
        this.md16It08 = param.getMd16It08();
        this.md16It09 = param.getMd16It09();
        this.md16It10 = param.getMd16It10();
        this.md16Zlcq = param.getMd16Zlcq();
        this.md16Sil1 = param.getMd16Sil1();
        this.md16Sil2 = param.getMd16Sil2();
        this.md16Cfqt = param.getMd16Cfqt();
        this.md16Note = param.getMd16Note();
        this.md16Sbdt = param.getMd16Sbdt();
        this.md16Cfyn = param.getMd16Cfyn();
        this.md16Endt = param.getMd16Endt();
        this.md16Enid = param.getMd16Enid();
        this.md16Updt = param.getMd16Updt();
        this.md16Upid = param.getMd16Upid();
    }

	public RfidMd16IfKey getKey() {
		return key;
	}

	public void setKey(RfidMd16IfKey key) {
		this.key = key;
	}

}