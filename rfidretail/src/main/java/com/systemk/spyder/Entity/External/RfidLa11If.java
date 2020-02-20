package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidLa11IfKey;

// 온라인 출고요청
@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_la11_if")
public class RfidLa11If implements Serializable{

	private static final long serialVersionUID = 1619944064879171591L;

	@EmbeddedId
	private RfidLa11IfKey key;

	@Column(name="la11_jmgb")
    private String la11Jmgb;

	@Column(name="la11_exsq")
    private BigDecimal la11Exsq;

	@Column(name="la11_mjcd")
    private String la11Mjcd;

	@Column(name="la11_corn")
    private String la11Corn;

	@Column(name="la11_styl")
    private String la11Styl;

	@Column(name="la11_stcd")
    private String la11Stcd;

	@Column(name="la11_jqty")
    private BigDecimal la11Jqty;

	@Column(name="la11_amps")
    private BigDecimal la11Amps;

	@Column(name="la11_milg")
    private BigDecimal la11Milg;

	@Column(name="la11_cpam")
    private BigDecimal la11Cpam;

	@Column(name="la11_gamt")
    private BigDecimal la11Gamt;

	@Column(name="la11_cfqt")
    private BigDecimal la11Cfqt;

	@Column(name="la11_majp")
    private BigDecimal la11Majp;

	@Column(name="la11_stat")
    private String la11Stat;

	@Column(name="la11_bogb")
    private String la11Bogb;

	@Column(name="la11_cnam")
    private String la11Cnam;

	@Column(name="la11_smsn")
    private String la11Smsn;

	@Column(name="la11_teln")
    private String la11Teln;

	@Column(name="la11_zipc")
    private String la11Zipc;

	@Column(name="la11_adr1")
    private String la11Adr1;

	@Column(name="la11_adr2")
    private String la11Adr2;

	@Column(name="la11_comp")
    private String la11Comp;

	@Column(name="la11_sjno")
    private String la11Sjno;

	@Column(name="la11_bigo")
    private String la11Bigo;

	@Column(name="la11_cgcd")
    private String la11Cgcd;

	@Column(name="la11_cgco")
    private String la11Cgco;

	@Column(name="la11_chdt")
    private String la11Chdt;

	@Column(name="la11_chsq")
    private BigDecimal la11Chsq;

	@Column(name="la11_chsr")
    private BigDecimal la11Chsr;

	@Column(name="la11_mgdt")
    private String la11Mgdt;

	@Column(name="la11_frcd")
    private String la11Frcd;

	@Column(name="la11_frco")
    private String la11Frco;

	@Column(name="la11_mgsq")
    private BigDecimal la11Mgsq;

	@Column(name="la11_mgsr")
    private BigDecimal la11Mgsr;

	@Column(name="la11_pmdt")
    private String la11Pmdt;

	@Column(name="la11_ysno")
    private BigDecimal la11Ysno;

	@Column(name="la11_clsy")
	private String la11Clsy;

	@Column(name="la11_bst1")
    private String la11Bst1;

	@Column(name="la11_bdt1")
    private String la11Bdt1;

	@Column(name="la11_btm1")
    private String la11Btm1;

	@Column(name="la11_bid1")
    private String la11Bid1;

	@Column(name="la11_bst2")
    private String la11Bst2;

	@Column(name="la11_bdt2")
    private String la11Bdt2;

	@Column(name="la11_btm2")
    private String la11Btm2;

	@Column(name="la11_bid2")
    private String la11Bid2;

	@Column(name="la11_rqno")
    private BigDecimal la11Rqno;

	@Column(name="la11_rqdt")
    private String la11Rqdt;

	@Column(name="la11_rqsq")
    private BigDecimal la11Rqsq;

	@Column(name="la11_rqsr")
    private BigDecimal la11Rqsr;

	@Column(name="la11_ykyn")
    private String la11Ykyn;

	@Column(name="la11_sygb")
    private String la11Sygb;

	@Column(name="la11_sayu")
    private String la11Sayu;

	@Column(name="la11_engb")
    private String la11Engb;

	@Column(name="la11_jmdt_o")
    private String la11JmdtO;

	@Column(name="la11_jmid_o")
    private String la11JmidO;

	@Column(name="la11_jmsr_o")
    private BigDecimal la11JmsrO;

	@Column(name="la11_endt")
    private String la11Endt;

	@Column(name="la11_enid")
    private String la11Enid;

	@Column(name="la11_updt")
    private String la11Updt;

	@Column(name="la11_upid")
    private String la11Upid;

	@Column(name="la11_tryn")
    private String la11Tryn;

    @Column(name="la11_trdt")
    private Date la11Trdt;

    public String getLa11Jmgb() {
        return la11Jmgb;
    }

    public void setLa11Jmgb(String la11Jmgb) {
        this.la11Jmgb = la11Jmgb;
    }

    public BigDecimal getLa11Exsq() {
        return la11Exsq;
    }

    public void setLa11Exsq(BigDecimal la11Exsq) {
        this.la11Exsq = la11Exsq;
    }

    public String getLa11Mjcd() {
        return la11Mjcd;
    }

    public void setLa11Mjcd(String la11Mjcd) {
        this.la11Mjcd = la11Mjcd;
    }

    public String getLa11Corn() {
        return la11Corn;
    }

    public void setLa11Corn(String la11Corn) {
        this.la11Corn = la11Corn;
    }

    public String getLa11Styl() {
        return la11Styl;
    }

    public void setLa11Styl(String la11Styl) {
        this.la11Styl = la11Styl;
    }

    public String getLa11Stcd() {
        return la11Stcd;
    }

    public void setLa11Stcd(String la11Stcd) {
        this.la11Stcd = la11Stcd;
    }

    public BigDecimal getLa11Jqty() {
        return la11Jqty;
    }

    public void setLa11Jqty(BigDecimal la11Jqty) {
        this.la11Jqty = la11Jqty;
    }

    public BigDecimal getLa11Amps() {
        return la11Amps;
    }

    public void setLa11Amps(BigDecimal la11Amps) {
        this.la11Amps = la11Amps;
    }

    public BigDecimal getLa11Milg() {
        return la11Milg;
    }

    public void setLa11Milg(BigDecimal la11Milg) {
        this.la11Milg = la11Milg;
    }

    public BigDecimal getLa11Cpam() {
        return la11Cpam;
    }

    public void setLa11Cpam(BigDecimal la11Cpam) {
        this.la11Cpam = la11Cpam;
    }

    public BigDecimal getLa11Gamt() {
        return la11Gamt;
    }

    public void setLa11Gamt(BigDecimal la11Gamt) {
        this.la11Gamt = la11Gamt;
    }

    public BigDecimal getLa11Cfqt() {
        return la11Cfqt;
    }

    public void setLa11Cfqt(BigDecimal la11Cfqt) {
        this.la11Cfqt = la11Cfqt;
    }

    public BigDecimal getLa11Majp() {
        return la11Majp;
    }

    public void setLa11Majp(BigDecimal la11Majp) {
        this.la11Majp = la11Majp;
    }

    public String getLa11Stat() {
        return la11Stat;
    }

    public void setLa11Stat(String la11Stat) {
        this.la11Stat = la11Stat;
    }

    public String getLa11Bogb() {
        return la11Bogb;
    }

    public void setLa11Bogb(String la11Bogb) {
        this.la11Bogb = la11Bogb;
    }

    public String getLa11Cnam() {
        return la11Cnam;
    }

    public void setLa11Cnam(String la11Cnam) {
        this.la11Cnam = la11Cnam;
    }

    public String getLa11Smsn() {
        return la11Smsn;
    }

    public void setLa11Smsn(String la11Smsn) {
        this.la11Smsn = la11Smsn;
    }

    public String getLa11Teln() {
        return la11Teln;
    }

    public void setLa11Teln(String la11Teln) {
        this.la11Teln = la11Teln;
    }

    public String getLa11Zipc() {
        return la11Zipc;
    }

    public void setLa11Zipc(String la11Zipc) {
        this.la11Zipc = la11Zipc;
    }

    public String getLa11Adr1() {
        return la11Adr1;
    }

    public void setLa11Adr1(String la11Adr1) {
        this.la11Adr1 = la11Adr1;
    }

    public String getLa11Adr2() {
        return la11Adr2;
    }

    public void setLa11Adr2(String la11Adr2) {
        this.la11Adr2 = la11Adr2;
    }

    public String getLa11Comp() {
        return la11Comp;
    }

    public void setLa11Comp(String la11Comp) {
        this.la11Comp = la11Comp;
    }

    public String getLa11Sjno() {
        return la11Sjno;
    }

    public void setLa11Sjno(String la11Sjno) {
        this.la11Sjno = la11Sjno;
    }

    public String getLa11Bigo() {
        return la11Bigo;
    }

    public void setLa11Bigo(String la11Bigo) {
        this.la11Bigo = la11Bigo;
    }

    public String getLa11Cgcd() {
        return la11Cgcd;
    }

    public void setLa11Cgcd(String la11Cgcd) {
        this.la11Cgcd = la11Cgcd;
    }

    public String getLa11Cgco() {
        return la11Cgco;
    }

    public void setLa11Cgco(String la11Cgco) {
        this.la11Cgco = la11Cgco;
    }

    public String getLa11Chdt() {
        return la11Chdt;
    }

    public void setLa11Chdt(String la11Chdt) {
        this.la11Chdt = la11Chdt;
    }

    public BigDecimal getLa11Chsq() {
        return la11Chsq;
    }

    public void setLa11Chsq(BigDecimal la11Chsq) {
        this.la11Chsq = la11Chsq;
    }

    public BigDecimal getLa11Chsr() {
        return la11Chsr;
    }

    public void setLa11Chsr(BigDecimal la11Chsr) {
        this.la11Chsr = la11Chsr;
    }

    public String getLa11Mgdt() {
        return la11Mgdt;
    }

    public void setLa11Mgdt(String la11Mgdt) {
        this.la11Mgdt = la11Mgdt;
    }

    public String getLa11Frcd() {
        return la11Frcd;
    }

    public void setLa11Frcd(String la11Frcd) {
        this.la11Frcd = la11Frcd;
    }

    public String getLa11Frco() {
        return la11Frco;
    }

    public void setLa11Frco(String la11Frco) {
        this.la11Frco = la11Frco;
    }

    public BigDecimal getLa11Mgsq() {
        return la11Mgsq;
    }

    public void setLa11Mgsq(BigDecimal la11Mgsq) {
        this.la11Mgsq = la11Mgsq;
    }

    public BigDecimal getLa11Mgsr() {
        return la11Mgsr;
    }

    public void setLa11Mgsr(BigDecimal la11Mgsr) {
        this.la11Mgsr = la11Mgsr;
    }

    public String getLa11Pmdt() {
        return la11Pmdt;
    }

    public void setLa11Pmdt(String la11Pmdt) {
        this.la11Pmdt = la11Pmdt;
    }

    public BigDecimal getLa11Ysno() {
        return la11Ysno;
    }

    public void setLa11Ysno(BigDecimal la11Ysno) {
        this.la11Ysno = la11Ysno;
    }

    public String getLa11Clsy() {
        return la11Clsy;
    }

    public void setLa11Clsy(String la11Clsy) {
        this.la11Clsy = la11Clsy;
    }

    public String getLa11Bst1() {
        return la11Bst1;
    }

    public void setLa11Bst1(String la11Bst1) {
        this.la11Bst1 = la11Bst1;
    }

    public String getLa11Bdt1() {
        return la11Bdt1;
    }

    public void setLa11Bdt1(String la11Bdt1) {
        this.la11Bdt1 = la11Bdt1;
    }

    public String getLa11Btm1() {
        return la11Btm1;
    }

    public void setLa11Btm1(String la11Btm1) {
        this.la11Btm1 = la11Btm1;
    }

    public String getLa11Bid1() {
        return la11Bid1;
    }

    public void setLa11Bid1(String la11Bid1) {
        this.la11Bid1 = la11Bid1;
    }

    public String getLa11Bst2() {
        return la11Bst2;
    }

    public void setLa11Bst2(String la11Bst2) {
        this.la11Bst2 = la11Bst2;
    }

    public String getLa11Bdt2() {
        return la11Bdt2;
    }

    public void setLa11Bdt2(String la11Bdt2) {
        this.la11Bdt2 = la11Bdt2;
    }

    public String getLa11Btm2() {
        return la11Btm2;
    }

    public void setLa11Btm2(String la11Btm2) {
        this.la11Btm2 = la11Btm2;
    }

    public String getLa11Bid2() {
        return la11Bid2;
    }

    public void setLa11Bid2(String la11Bid2) {
        this.la11Bid2 = la11Bid2;
    }

    public BigDecimal getLa11Rqno() {
        return la11Rqno;
    }

    public void setLa11Rqno(BigDecimal la11Rqno) {
        this.la11Rqno = la11Rqno;
    }

    public String getLa11Rqdt() {
        return la11Rqdt;
    }

    public void setLa11Rqdt(String la11Rqdt) {
        this.la11Rqdt = la11Rqdt;
    }

    public BigDecimal getLa11Rqsq() {
        return la11Rqsq;
    }

    public void setLa11Rqsq(BigDecimal la11Rqsq) {
        this.la11Rqsq = la11Rqsq;
    }

    public BigDecimal getLa11Rqsr() {
        return la11Rqsr;
    }

    public void setLa11Rqsr(BigDecimal la11Rqsr) {
        this.la11Rqsr = la11Rqsr;
    }

    public String getLa11Ykyn() {
        return la11Ykyn;
    }

    public void setLa11Ykyn(String la11Ykyn) {
        this.la11Ykyn = la11Ykyn;
    }

    public String getLa11Sygb() {
        return la11Sygb;
    }

    public void setLa11Sygb(String la11Sygb) {
        this.la11Sygb = la11Sygb;
    }

    public String getLa11Sayu() {
        return la11Sayu;
    }

    public void setLa11Sayu(String la11Sayu) {
        this.la11Sayu = la11Sayu;
    }

    public String getLa11Engb() {
        return la11Engb;
    }

    public void setLa11Engb(String la11Engb) {
        this.la11Engb = la11Engb;
    }

    public String getLa11JmdtO() {
        return la11JmdtO;
    }

    public void setLa11JmdtO(String la11JmdtO) {
        this.la11JmdtO = la11JmdtO;
    }

    public String getLa11JmidO() {
        return la11JmidO;
    }

    public void setLa11JmidO(String la11JmidO) {
        this.la11JmidO = la11JmidO;
    }

    public BigDecimal getLa11JmsrO() {
        return la11JmsrO;
    }

    public void setLa11JmsrO(BigDecimal la11JmsrO) {
        this.la11JmsrO = la11JmsrO;
    }

    public String getLa11Endt() {
        return la11Endt;
    }

    public void setLa11Endt(String la11Endt) {
        this.la11Endt = la11Endt;
    }

    public String getLa11Enid() {
        return la11Enid;
    }

    public void setLa11Enid(String la11Enid) {
        this.la11Enid = la11Enid;
    }

    public String getLa11Updt() {
        return la11Updt;
    }

    public void setLa11Updt(String la11Updt) {
        this.la11Updt = la11Updt;
    }

    public String getLa11Upid() {
        return la11Upid;
    }

    public void setLa11Upid(String la11Upid) {
        this.la11Upid = la11Upid;
    }

    // RfidLa11If 모델 복사
    public void CopyData(RfidLa11If param)
    {
        this.la11Jmgb = param.getLa11Jmgb();
        this.la11Exsq = param.getLa11Exsq();
        this.la11Mjcd = param.getLa11Mjcd();
        this.la11Corn = param.getLa11Corn();
        this.la11Styl = param.getLa11Styl();
        this.la11Stcd = param.getLa11Stcd();
        this.la11Jqty = param.getLa11Jqty();
        this.la11Amps = param.getLa11Amps();
        this.la11Milg = param.getLa11Milg();
        this.la11Cpam = param.getLa11Cpam();
        this.la11Gamt = param.getLa11Gamt();
        this.la11Cfqt = param.getLa11Cfqt();
        this.la11Majp = param.getLa11Majp();
        this.la11Stat = param.getLa11Stat();
        this.la11Bogb = param.getLa11Bogb();
        this.la11Cnam = param.getLa11Cnam();
        this.la11Smsn = param.getLa11Smsn();
        this.la11Teln = param.getLa11Teln();
        this.la11Zipc = param.getLa11Zipc();
        this.la11Adr1 = param.getLa11Adr1();
        this.la11Adr2 = param.getLa11Adr2();
        this.la11Comp = param.getLa11Comp();
        this.la11Sjno = param.getLa11Sjno();
        this.la11Bigo = param.getLa11Bigo();
        this.la11Cgcd = param.getLa11Cgcd();
        this.la11Cgco = param.getLa11Cgco();
        this.la11Chdt = param.getLa11Chdt();
        this.la11Chsq = param.getLa11Chsq();
        this.la11Chsr = param.getLa11Chsr();
        this.la11Mgdt = param.getLa11Mgdt();
        this.la11Frcd = param.getLa11Frcd();
        this.la11Frco = param.getLa11Frco();
        this.la11Mgsq = param.getLa11Mgsq();
        this.la11Mgsr = param.getLa11Mgsr();
        this.la11Pmdt = param.getLa11Pmdt();
        this.la11Ysno = param.getLa11Ysno();
        this.la11Clsy = param.getLa11Clsy();
        this.la11Bst1 = param.getLa11Bst1();
        this.la11Bdt1 = param.getLa11Bdt1();
        this.la11Btm1 = param.getLa11Btm1();
        this.la11Bid1 = param.getLa11Bid1();
        this.la11Bst2 = param.getLa11Bst2();
        this.la11Bdt2 = param.getLa11Bdt2();
        this.la11Btm2 = param.getLa11Btm2();
        this.la11Bid2 = param.getLa11Bid2();
        this.la11Rqno = param.getLa11Rqno();
        this.la11Rqdt = param.getLa11Rqdt();
        this.la11Rqsq = param.getLa11Rqsq();
        this.la11Rqsr = param.getLa11Rqsr();
        this.la11Ykyn = param.getLa11Ykyn();
        this.la11Sygb = param.getLa11Sygb();
        this.la11Sayu = param.getLa11Sayu();
        this.la11Engb = param.getLa11Engb();
        this.la11JmdtO = param.getLa11JmdtO();
        this.la11JmidO = param.getLa11JmidO();
        this.la11JmsrO = param.getLa11JmsrO();
        this.la11Endt = param.getLa11Endt();
        this.la11Enid = param.getLa11Enid();
        this.la11Updt = param.getLa11Updt();
        this.la11Upid = param.getLa11Upid();
    }

    public RfidLa11IfKey getKey() {
		return key;
	}

	public void setKey(RfidLa11IfKey key) {
		this.key = key;
	}

	public String getLa11Tryn() {
		return la11Tryn;
	}

	public void setLa11Tryn(String la11Tryn) {
		this.la11Tryn = la11Tryn;
	}

	public Date getLa11Trdt() {
		return la11Trdt;
	}

	public void setLa11Trdt(Date la11Trdt) {
		this.la11Trdt = la11Trdt;
	}

}