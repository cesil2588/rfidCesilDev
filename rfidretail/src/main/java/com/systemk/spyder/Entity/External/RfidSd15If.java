package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd15IfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Table(name="rfid_sd15_if")
public class RfidSd15If implements Serializable{

	/**
	 * ERP 반품지시 실적 Entity
	 */
	private static final long serialVersionUID = 962559288112784726L;
	
	@EmbeddedId
	private RfidSd15IfKey rfidSd15IfKey;
	
	//스캔수량
	@Column(name="sd15_bqty")
	private Long sd15Bqty;
	
	//등록구분(신규 추가시 'NEW')
	@Column(name="sd15_gubn")
	private String sd15Gubn;
	
	//비고
	@Column(name="sd15_note" ,insertable=false)
	private String sd15Note;
	
	//미결일자
	@Column(name="sd15_mgdt" ,insertable=false)
	private String sd15Mgdt;
	
	//미결번호
	@Column(name="sd15_mgsq" ,insertable=false)
	private Long sd15Mgsq;
	
	//미결 Serial
	@Column(name="sd15_mgsr" ,insertable=false)
	private Long sd15Mgsr;
	
	//등록일자
	@Column(name="sd15_endt")
	private Date sd15Endt;
	
	//등록ID
	@Column(name="sd15_enid")
	private String sd15Enid;
	
	//수정일자
	@Column(name="sd15_updt" ,insertable=false)
	private Date sd15Updt;
	
	//수정ID
	@Column(name="sd15_upid" ,insertable=false)
	private String sd15UpId;

	public RfidSd15IfKey getRfidSd15IfKey() {
		return rfidSd15IfKey;
	}

	public void setRfidSd15IfKey(RfidSd15IfKey rfidSd15IfKey) {
		this.rfidSd15IfKey = rfidSd15IfKey;
	}

	public Long getSd15Bqty() {
		return sd15Bqty;
	}

	public void setSd15Bqty(Long sd15Bqty) {
		this.sd15Bqty = sd15Bqty;
	}

	public String getSd15Note() {
		return sd15Note;
	}

	public void setSd15Note(String sd15Note) {
		this.sd15Note = sd15Note;
	}

	public String getSd15Mgdt() {
		return sd15Mgdt;
	}

	public void setSd15Mgdt(String sd15Mgdt) {
		this.sd15Mgdt = sd15Mgdt;
	}

	public Long getSd15Mgsq() {
		return sd15Mgsq;
	}

	public void setSd15Mgsq(Long sd15Mgsq) {
		this.sd15Mgsq = sd15Mgsq;
	}

	public Long getSd15Mgsr() {
		return sd15Mgsr;
	}

	public void setSd15Mgsr(Long sd15Mgsr) {
		this.sd15Mgsr = sd15Mgsr;
	}

	public Date getSd15Endt() {
		return sd15Endt;
	}

	public void setSd15Endt(Date sd15Endt) {
		this.sd15Endt = sd15Endt;
	}

	public String getSd15Enid() {
		return sd15Enid;
	}

	public void setSd15Enid(String sd15Enid) {
		this.sd15Enid = sd15Enid;
	}

	public Date getSd15Updt() {
		return sd15Updt;
	}

	public void setSd15Updt(Date sd15Updt) {
		this.sd15Updt = sd15Updt;
	}

	public String getSd15UpId() {
		return sd15UpId;
	}

	public void setSd15UpId(String sd15UpId) {
		this.sd15UpId = sd15UpId;
	}

	public String getSd15Gubn() {
		return sd15Gubn;
	}

	public void setSd15Gubn(String sd15Gubn) {
		this.sd15Gubn = sd15Gubn;
	}
	

}
