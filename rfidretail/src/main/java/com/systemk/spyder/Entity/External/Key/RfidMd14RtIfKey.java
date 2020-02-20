package com.systemk.spyder.Entity.External.Key;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;
import com.systemk.spyder.Entity.Main.InventoryScheduleTag;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RfidMd14RtIfKey implements Serializable{

	private static final long serialVersionUID = 5610843740347575880L;

	@Column(name="md14rt_mjcd")
	private String md14rtMjcd;

	@Column(name="md14rt_corn")
	private String md14rtCorn;

	@Column(name="md14rt_bsdt")
	private String md14rtBsdt;

	@Column(name="md14rt_rfid")
	private String md14rtRfid;

	@Column(name="md14rt_styl")
	private String md14rtStyl;

	@Column(name="md14rt_stcd")
	private String md14rtStcd;

	public String getMd14rtMjcd() {
		return md14rtMjcd;
	}

	public void setMd14rtMjcd(String md14rtMjcd) {
		this.md14rtMjcd = md14rtMjcd;
	}

	public String getMd14rtCorn() {
		return md14rtCorn;
	}

	public void setMd14rtCorn(String md14rtCorn) {
		this.md14rtCorn = md14rtCorn;
	}

	public String getMd14rtBsdt() {
		return md14rtBsdt;
	}

	public void setMd14rtBsdt(String md14rtBsdt) {
		this.md14rtBsdt = md14rtBsdt;
	}

	public String getMd14rtRfid() {
		return md14rtRfid;
	}

	public void setMd14rtRfid(String md14rtRfid) {
		this.md14rtRfid = md14rtRfid;
	}

	public String getMd14rtStyl() {
		return md14rtStyl;
	}

	public void setMd14rtStyl(String md14rtStyl) {
		this.md14rtStyl = md14rtStyl;
	}

	public String getMd14rtStcd() {
		return md14rtStcd;
	}

	public void setMd14rtStcd(String md14rtStcd) {
		this.md14rtStcd = md14rtStcd;
	}

	public RfidMd14RtIfKey() {
	}

	public RfidMd14RtIfKey(InventoryScheduleHeader header, InventoryScheduleStyle style, InventoryScheduleTag tag) {
		this.md14rtMjcd 	= header.getCompanyInfo().getCustomerCode();
		this.md14rtCorn		= header.getCompanyInfo().getCornerCode();
		this.md14rtBsdt		= header.getCreateDate();
		this.md14rtStyl		= style.getStyle();
		this.md14rtStcd		= style.getColor() + style.getSize();
		this.md14rtRfid		= tag.getRfidTag();
	}

	public RfidMd14RtIfKey(InventoryScheduleHeader header, InventoryScheduleStyle style) {
		this.md14rtMjcd 	= header.getCompanyInfo().getCustomerCode();
		this.md14rtCorn		= header.getCompanyInfo().getCornerCode();
		this.md14rtBsdt		= header.getCreateDate();
		this.md14rtStyl		= style.getStyle();
		this.md14rtStcd		= style.getColor() + style.getSize();
		this.md14rtRfid		= "";
	}
}
