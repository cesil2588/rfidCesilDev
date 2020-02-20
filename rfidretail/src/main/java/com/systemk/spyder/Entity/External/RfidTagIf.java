package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;
import com.systemk.spyder.Entity.External.Key.RfidTagIfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
public class RfidTagIf implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -4414688601661765590L;

	@EmbeddedId
	private RfidTagIfKey key;

	@Column(nullable = false, length = 20)
    private String tagRfbc;

	@Column(nullable = false, length = 10)
    private String tagErpk;

	@Column(nullable = false, length = 3)
    private String tagYyss;

	@Column(nullable = false, length = 2)
    private String tagOrdq;

	@Column(nullable = false, length = 3)
    private String tagPrcd;

	@Column(nullable = false, length = 1)
    private String tagPrtj;

	@Column(nullable = false, length = 6)
    private String tagPrdt;

	@Column(nullable = false, length = 2)
    private String tagPrch;

	@Column(nullable = false, length = 5)
    private String tagSeqn;

	@Column(nullable = false, length = 1)
	private String tagUsyn;

	@Column(nullable = false, length = 10)
    private String tagStat;

	@Column(nullable = false, length = 20)
    private String tagStyl;

	@Column(nullable = false, length = 20)
    private String tagStcd;

	@Column(nullable = false, length = 3)
    private String tagJjch;

	@Column(nullable = true)
    private Date tagEndt;

	@Column(nullable = true)
    private Date tagUpdt;

    public String getTagJjch() {
        return tagJjch;
    }

    public void setTagJjch(String tagJjch) {
        this.tagJjch = tagJjch;
    }

    public String getTagPrcd() {
		return tagPrcd;
	}

	public void setTagPrcd(String tagPrcd) {
		this.tagPrcd = tagPrcd;
	}

	public String getTagPrtj() {
        return tagPrtj;
    }

    public void setTagPrtj(String tagPrtj) {
        this.tagPrtj = tagPrtj;
    }

    public String getTagPrdt() {
        return tagPrdt;
    }

    public void setTagPrdt(String tagPrdt) {
        this.tagPrdt = tagPrdt;
    }

    public String getTagPrch() {
        return tagPrch;
    }

    public void setTagPrch(String tagPrch) {
        this.tagPrch = tagPrch;
    }

    public String getTagSeqn() {
        return tagSeqn;
    }

    public void setTagSeqn(String tagSeqn) {
        this.tagSeqn = tagSeqn;
    }

    public String getTagStat() {
        return tagStat;
    }

    public void setTagStat(String tagStat) {
        this.tagStat = tagStat;
    }

	public Date getTagEndt() {
		return tagEndt;
	}

	public void setTagEndt(Date tagEndt) {
		this.tagEndt = tagEndt;
	}

	public String getTagRfbc() {
		return tagRfbc;
	}

	public void setTagRfbc(String tagRfbc) {
		this.tagRfbc = tagRfbc;
	}

	public String getTagErpk() {
		return tagErpk;
	}

	public void setTagErpk(String tagErpk) {
		this.tagErpk = tagErpk;
	}

	public String getTagYyss() {
		return tagYyss;
	}

	public void setTagYyss(String tagYyss) {
		this.tagYyss = tagYyss;
	}

	public String getTagOrdq() {
		return tagOrdq;
	}

	public void setTagOrdq(String tagOrdq) {
		this.tagOrdq = tagOrdq;
	}

	public String getTagUsyn() {
		return tagUsyn;
	}

	public void setTagUsyn(String tagUsyn) {
		this.tagUsyn = tagUsyn;
	}

	public String getTagStyl() {
		return tagStyl;
	}

	public void setTagStyl(String tagStyl) {
		this.tagStyl = tagStyl;
	}

	public String getTagStcd() {
		return tagStcd;
	}

	public void setTagStcd(String tagStcd) {
		this.tagStcd = tagStcd;
	}

	public Date getTagUpdt() {
		return tagUpdt;
	}

	public void setTagUpdt(Date tagUpdt) {
		this.tagUpdt = tagUpdt;
	}

	public RfidTagIfKey getKey() {
		return key;
	}

	public void setKey(RfidTagIfKey key) {
		this.key = key;
	}

}
