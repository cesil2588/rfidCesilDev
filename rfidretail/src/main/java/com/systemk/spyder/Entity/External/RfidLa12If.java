package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLa12IfKey;

// 온라인 반품요청
@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_la12_if")
public class RfidLa12If implements Serializable{

	private static final long serialVersionUID = 3545112525263528416L;

	@EmbeddedId
	private RfidLa12IfKey key;

	@Column(name="la12_crgb")
    private String la12Crgb;

	@Column(name="la12_stat")
    private String la12Stat;

	@Column(name="la12_styl")
    private String la12Styl;

	@Column(name="la12_stcd")
    private String la12Stcd;

	@Column(name="la12_bpqt")
    private BigDecimal la12Bpqt;

	@Column(name="la12_amps")
    private BigDecimal la12Amps;

	@Column(name="la12_milg")
    private BigDecimal la12Milg;

	@Column(name="la12_cpam")
    private BigDecimal la12Cpam;

	@Column(name="la12_gamt")
    private BigDecimal la12Gamt;

	@Column(name="la12_cfqt")
    private BigDecimal la12Cfqt;

	@Column(name="la12_cnam")
    private String la12Cnam;

	@Column(name="la12_smsn")
    private String la12Smsn;

	@Column(name="la12_teln")
    private String la12Teln;

	@Column(name="la12_zipc")
    private String la12Zipc;

	@Column(name="la12_adr1")
    private String la12Adr1;

	@Column(name="la12_adr2")
    private String la12Adr2;

	@Column(name="la12_bpdt")
    private String la12Bpdt;

	@Column(name="la12_bpsq")
    private BigDecimal la12Bpsq;

	@Column(name="la12_bpsy")
    private String la12Bpsy;

	@Column(name="la12_bpsj")
    private String la12Bpsj;

	@Column(name="la12_bpco")
    private String la12Bpco;

	@Column(name="la12_pmdt")
    private String la12Pmdt;

	@Column(name="la12_ysno")
    private BigDecimal la12Ysno;

	@Column(name="la12_chdt")
    private String la12Chdt;

	@Column(name="la12_chsj")
    private String la12Chsj;

	@Column(name="la12_chco")
    private String la12Chco;

	@Column(name="la12_bigo")
    private String la12Bigo;

	@Column(name="la12_cgcd")
    private String la12Cgcd;

	@Column(name="la12_cgco")
    private String la12Cgco;

	@Column(name="la12_mjcd")
    private String la12Mjcd;

	@Column(name="la12_corn")
    private String la12Corn;

	@Column(name="la12_rqdt")
    private String la12Rqdt;

	@Column(name="la12_rqsq")
    private BigDecimal la12Rqsq;

	@Column(name="la12_rqsr")
    private BigDecimal la12Rqsr;

	@Column(name="la12_engb")
    private String la12Engb;

	@Column(name="la12_jmdt_o")
    private String la12JmdtO;

	@Column(name="la12_jmid_o")
    private String la12JmidO;

	@Column(name="la12_jmsr_o")
    private BigDecimal la12JmsrO;

	@Column(name="la12_endt")
    private String la12Endt;

	@Column(name="la12_enid")
    private String la12Enid;

	@Column(name="la12_updt")
    private String la12Updt;

	@Column(name="la12_upid")
    private String la12Upid;

	@Column(name="la12_tryn")
    private String la12Tryn;

    @Column(name="la12_trdt")
    private Date la12Trdt;

    public String getLa12Crgb() {
        return la12Crgb;
    }

    public void setLa12Crgb(String la12Crgb) {
        this.la12Crgb = la12Crgb;
    }

    public String getLa12Stat() {
        return la12Stat;
    }

    public void setLa12Stat(String la12Stat) {
        this.la12Stat = la12Stat;
    }

    public String getLa12Styl() {
        return la12Styl;
    }

    public void setLa12Styl(String la12Styl) {
        this.la12Styl = la12Styl;
    }

    public String getLa12Stcd() {
        return la12Stcd;
    }

    public void setLa12Stcd(String la12Stcd) {
        this.la12Stcd = la12Stcd;
    }

    public BigDecimal getLa12Bpqt() {
        return la12Bpqt;
    }

    public void setLa12Bpqt(BigDecimal la12Bpqt) {
        this.la12Bpqt = la12Bpqt;
    }

    public BigDecimal getLa12Amps() {
        return la12Amps;
    }

    public void setLa12Amps(BigDecimal la12Amps) {
        this.la12Amps = la12Amps;
    }

    public BigDecimal getLa12Milg() {
        return la12Milg;
    }

    public void setLa12Milg(BigDecimal la12Milg) {
        this.la12Milg = la12Milg;
    }

    public BigDecimal getLa12Cpam() {
        return la12Cpam;
    }

    public void setLa12Cpam(BigDecimal la12Cpam) {
        this.la12Cpam = la12Cpam;
    }

    public BigDecimal getLa12Gamt() {
        return la12Gamt;
    }

    public void setLa12Gamt(BigDecimal la12Gamt) {
        this.la12Gamt = la12Gamt;
    }

    public BigDecimal getLa12Cfqt() {
        return la12Cfqt;
    }

    public void setLa12Cfqt(BigDecimal la12Cfqt) {
        this.la12Cfqt = la12Cfqt;
    }

    public String getLa12Cnam() {
        return la12Cnam;
    }

    public void setLa12Cnam(String la12Cnam) {
        this.la12Cnam = la12Cnam;
    }

    public String getLa12Smsn() {
        return la12Smsn;
    }

    public void setLa12Smsn(String la12Smsn) {
        this.la12Smsn = la12Smsn;
    }

    public String getLa12Teln() {
        return la12Teln;
    }

    public void setLa12Teln(String la12Teln) {
        this.la12Teln = la12Teln;
    }

    public String getLa12Zipc() {
        return la12Zipc;
    }

    public void setLa12Zipc(String la12Zipc) {
        this.la12Zipc = la12Zipc;
    }

    public String getLa12Adr1() {
        return la12Adr1;
    }

    public void setLa12Adr1(String la12Adr1) {
        this.la12Adr1 = la12Adr1;
    }

    public String getLa12Adr2() {
        return la12Adr2;
    }

    public void setLa12Adr2(String la12Adr2) {
        this.la12Adr2 = la12Adr2;
    }

    public String getLa12Bpdt() {
        return la12Bpdt;
    }

    public void setLa12Bpdt(String la12Bpdt) {
        this.la12Bpdt = la12Bpdt;
    }

    public BigDecimal getLa12Bpsq() {
        return la12Bpsq;
    }

    public void setLa12Bpsq(BigDecimal la12Bpsq) {
        this.la12Bpsq = la12Bpsq;
    }

    public String getLa12Bpsy() {
        return la12Bpsy;
    }

    public void setLa12Bpsy(String la12Bpsy) {
        this.la12Bpsy = la12Bpsy;
    }

    public String getLa12Bpsj() {
        return la12Bpsj;
    }

    public void setLa12Bpsj(String la12Bpsj) {
        this.la12Bpsj = la12Bpsj;
    }

    public String getLa12Bpco() {
        return la12Bpco;
    }

    public void setLa12Bpco(String la12Bpco) {
        this.la12Bpco = la12Bpco;
    }

    public String getLa12Pmdt() {
        return la12Pmdt;
    }

    public void setLa12Pmdt(String la12Pmdt) {
        this.la12Pmdt = la12Pmdt;
    }

    public BigDecimal getLa12Ysno() {
        return la12Ysno;
    }

    public void setLa12Ysno(BigDecimal la12Ysno) {
        this.la12Ysno = la12Ysno;
    }

    public String getLa12Chdt() {
        return la12Chdt;
    }

    public void setLa12Chdt(String la12Chdt) {
        this.la12Chdt = la12Chdt;
    }

    public String getLa12Chsj() {
        return la12Chsj;
    }

    public void setLa12Chsj(String la12Chsj) {
        this.la12Chsj = la12Chsj;
    }

    public String getLa12Chco() {
        return la12Chco;
    }

    public void setLa12Chco(String la12Chco) {
        this.la12Chco = la12Chco;
    }

    public String getLa12Bigo() {
        return la12Bigo;
    }

    public void setLa12Bigo(String la12Bigo) {
        this.la12Bigo = la12Bigo;
    }

    public String getLa12Cgcd() {
        return la12Cgcd;
    }

    public void setLa12Cgcd(String la12Cgcd) {
        this.la12Cgcd = la12Cgcd;
    }

    public String getLa12Cgco() {
        return la12Cgco;
    }

    public void setLa12Cgco(String la12Cgco) {
        this.la12Cgco = la12Cgco;
    }

    public String getLa12Mjcd() {
        return la12Mjcd;
    }

    public void setLa12Mjcd(String la12Mjcd) {
        this.la12Mjcd = la12Mjcd;
    }

    public String getLa12Corn() {
        return la12Corn;
    }

    public void setLa12Corn(String la12Corn) {
        this.la12Corn = la12Corn;
    }

    public String getLa12Rqdt() {
        return la12Rqdt;
    }

    public void setLa12Rqdt(String la12Rqdt) {
        this.la12Rqdt = la12Rqdt;
    }

    public BigDecimal getLa12Rqsq() {
        return la12Rqsq;
    }

    public void setLa12Rqsq(BigDecimal la12Rqsq) {
        this.la12Rqsq = la12Rqsq;
    }

    public BigDecimal getLa12Rqsr() {
        return la12Rqsr;
    }

    public void setLa12Rqsr(BigDecimal la12Rqsr) {
        this.la12Rqsr = la12Rqsr;
    }

    public String getLa12Engb() {
        return la12Engb;
    }

    public void setLa12Engb(String la12Engb) {
        this.la12Engb = la12Engb;
    }

    public String getLa12JmdtO() {
        return la12JmdtO;
    }

    public void setLa12JmdtO(String la12JmdtO) {
        this.la12JmdtO = la12JmdtO;
    }

    public String getLa12JmidO() {
        return la12JmidO;
    }

    public void setLa12JmidO(String la12JmidO) {
        this.la12JmidO = la12JmidO;
    }

    public BigDecimal getLa12JmsrO() {
        return la12JmsrO;
    }

    public void setLa12JmsrO(BigDecimal la12JmsrO) {
        this.la12JmsrO = la12JmsrO;
    }

    public String getLa12Endt() {
        return la12Endt;
    }

    public void setLa12Endt(String la12Endt) {
        this.la12Endt = la12Endt;
    }

    public String getLa12Enid() {
        return la12Enid;
    }

    public void setLa12Enid(String la12Enid) {
        this.la12Enid = la12Enid;
    }

    public String getLa12Updt() {
        return la12Updt;
    }

    public void setLa12Updt(String la12Updt) {
        this.la12Updt = la12Updt;
    }

    public String getLa12Upid() {
        return la12Upid;
    }

    public void setLa12Upid(String la12Upid) {
        this.la12Upid = la12Upid;
    }

    // RfidLa12If 모델 복사
    public void CopyData(RfidLa12If param)
    {
        this.la12Crgb = param.getLa12Crgb();
        this.la12Stat = param.getLa12Stat();
        this.la12Styl = param.getLa12Styl();
        this.la12Stcd = param.getLa12Stcd();
        this.la12Bpqt = param.getLa12Bpqt();
        this.la12Amps = param.getLa12Amps();
        this.la12Milg = param.getLa12Milg();
        this.la12Cpam = param.getLa12Cpam();
        this.la12Gamt = param.getLa12Gamt();
        this.la12Cfqt = param.getLa12Cfqt();
        this.la12Cnam = param.getLa12Cnam();
        this.la12Smsn = param.getLa12Smsn();
        this.la12Teln = param.getLa12Teln();
        this.la12Zipc = param.getLa12Zipc();
        this.la12Adr1 = param.getLa12Adr1();
        this.la12Adr2 = param.getLa12Adr2();
        this.la12Bpdt = param.getLa12Bpdt();
        this.la12Bpsq = param.getLa12Bpsq();
        this.la12Bpsy = param.getLa12Bpsy();
        this.la12Bpsj = param.getLa12Bpsj();
        this.la12Bpco = param.getLa12Bpco();
        this.la12Pmdt = param.getLa12Pmdt();
        this.la12Ysno = param.getLa12Ysno();
        this.la12Chdt = param.getLa12Chdt();
        this.la12Chsj = param.getLa12Chsj();
        this.la12Chco = param.getLa12Chco();
        this.la12Bigo = param.getLa12Bigo();
        this.la12Cgcd = param.getLa12Cgcd();
        this.la12Cgco = param.getLa12Cgco();
        this.la12Mjcd = param.getLa12Mjcd();
        this.la12Corn = param.getLa12Corn();
        this.la12Rqdt = param.getLa12Rqdt();
        this.la12Rqsq = param.getLa12Rqsq();
        this.la12Rqsr = param.getLa12Rqsr();
        this.la12Engb = param.getLa12Engb();
        this.la12JmdtO = param.getLa12JmdtO();
        this.la12JmidO = param.getLa12JmidO();
        this.la12JmsrO = param.getLa12JmsrO();
        this.la12Endt = param.getLa12Endt();
        this.la12Enid = param.getLa12Enid();
        this.la12Updt = param.getLa12Updt();
        this.la12Upid = param.getLa12Upid();
    }

	public RfidLa12IfKey getKey() {
		return key;
	}

	public void setKey(RfidLa12IfKey key) {
		this.key = key;
	}

	public String getLa12Tryn() {
		return la12Tryn;
	}

	public void setLa12Tryn(String la12Tryn) {
		this.la12Tryn = la12Tryn;
	}

	public Date getLa12Trdt() {
		return la12Trdt;
	}

	public void setLa12Trdt(Date la12Trdt) {
		this.la12Trdt = la12Trdt;
	}

}