package com.systemk.spyder.Entity.External;

import com.systemk.spyder.Entity.External.Key.RfidMd14IfKey;
import com.systemk.spyder.Entity.External.Key.RfidMd14RtIfKey;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;
import com.systemk.spyder.Entity.Main.InventoryScheduleTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Util.CalendarUtil;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name="rfid_md14rt_if")
public class RfidMd14RtIf implements Serializable{

	private static final long serialVersionUID = 3305971304036044811L;

	@EmbeddedId
	private RfidMd14RtIfKey key;

    @Column(name="md14rt_sil1")
    private BigDecimal  md14rtSil1;

    @Column(name="md14rt_bygb")
    private String      md14rtBygb;

    @Column(name="md14rt_bydt")
    private String      md14rtBydt;

    @Column(name="md14rt_byid")
    private String      md14rtByid;

    @Column(name="md14rt_endt")
    private String      md14rtEndt;

    @Column(name="md14rt_enid")
    private String      md14rtEnid;

    public RfidMd14RtIfKey getKey() {
        return key;
    }

    public void setKey(RfidMd14RtIfKey key) {
        this.key = key;
    }

    public BigDecimal getMd14rtSil1() {
        return md14rtSil1;
    }

    public void setMd14rtSil1(BigDecimal md14rtSil1) {
        this.md14rtSil1 = md14rtSil1;
    }

    public String getMd14rtBygb() {
        return md14rtBygb;
    }

    public void setMd14rtBygb(String md14rtBygb) {
        this.md14rtBygb = md14rtBygb;
    }

    public String getMd14rtBydt() {
        return md14rtBydt;
    }

    public void setMd14rtBydt(String md14rtBydt) {
        this.md14rtBydt = md14rtBydt;
    }

    public String getMd14rtByid() {
        return md14rtByid;
    }

    public void setMd14rtByid(String md14rtByid) {
        this.md14rtByid = md14rtByid;
    }

    public String getMd14rtEndt() {
        return md14rtEndt;
    }

    public void setMd14rtEndt(String md14rtEndt) {
        this.md14rtEndt = md14rtEndt;
    }

    public String getMd14rtEnid() {
        return md14rtEnid;
    }

    public void setMd14rtEnid(String md14rtEnid) {
        this.md14rtEnid = md14rtEnid;
    }

    public RfidMd14RtIf() {
    }

    public RfidMd14RtIf(InventoryScheduleHeader header, InventoryScheduleStyle style, InventoryScheduleTag tag, UserInfo userInfo) {
        this.key            = new RfidMd14RtIfKey(header, style, tag);
        this.md14rtSil1     = new BigDecimal(style.getCompleteAmount());
        this.md14rtEndt     = CalendarUtil.convertFormat("yyyyMMdd");
        this.md14rtEnid     = userInfo.getUserId();
        this.md14rtBygb     = "N";
        this.md14rtBydt     = "";
        this.md14rtByid     = "";
    }

    public RfidMd14RtIf(InventoryScheduleHeader header, InventoryScheduleStyle style, UserInfo userInfo) {
        this.key            = new RfidMd14RtIfKey(header, style);
        this.md14rtSil1     = new BigDecimal(style.getCompleteAmount());
        this.md14rtEndt     = CalendarUtil.convertFormat("yyyyMMdd");
        this.md14rtEnid     = userInfo.getUserId();
        this.md14rtBygb     = "N";
        this.md14rtBydt     = "";
        this.md14rtByid     = "";
    }
}