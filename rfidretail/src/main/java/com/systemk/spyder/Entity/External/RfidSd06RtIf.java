package com.systemk.spyder.Entity.External;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.systemk.spyder.Entity.External.Key.RfidSd06RtIfKey;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Table(name="rfid_sd06rt_if")
public class RfidSd06RtIf implements Serializable{

	/**
	 * 매장 이동지시 실적을 위한 Entity
	 */
	private static final long serialVersionUID = 7466551594750353372L;
	
	@EmbeddedId
	private RfidSd06RtIfKey key;
	
	//스캔수량
	@Column(name = "sd06rt_bqty")
	private Long sd06rtBqty;
	
	//to매장코드
	@Column(name = "sd06rt_tocd")
	private String sd06rtTocd;
	
	//to매장코너코드
	@Column(name = "sd06rt_toco")
	private String sd06rtToco;
	
	//비고
	@Column(name = "sd06rt_bigo", insertable=false)
	private String sd06rtBigo;
	
	//미결일자
	@Column(name = "sd06rt_mgdt" , insertable=false)
	private String sd06rtMgdt;
	
	//미결번호
	@Column(name = "sd06rt_mgsq" , insertable=false)
	private Long sd06rtMgsq;
	
	//미결Serial
	@Column(name = "sd06rt_mgsr" , insertable=false)
	private Long sd06rtMgsr;
	
	//등록일자
	@Column(name = "sd06rt_endt")
	private Date sd06rtEndt;
	
	//등록 ID
	@Column(name = "sd06rt_enid")
	private String sd06rtEnid;
	
	//수정일자
	@Column(name = "sd06rt_updt" , insertable=false)
	private Date sd06rtUpdt;
	
	//수정ID
	@Column(name = "sd06rt_upid" , insertable=false)
	private String sd06rtUpid;

	public RfidSd06RtIfKey getKey() {
		return key;
	}

	public void setKey(RfidSd06RtIfKey key) {
		this.key = key;
	}

	public Long getSd06rtBqty() {
		return sd06rtBqty;
	}

	public void setSd06rtBqty(Long sd06rtBqty) {
		this.sd06rtBqty = sd06rtBqty;
	}

	public String getSd06rtTocd() {
		return sd06rtTocd;
	}

	public void setSd06rtTocd(String sd06rtTocd) {
		this.sd06rtTocd = sd06rtTocd;
	}

	public String getSd06rtToco() {
		return sd06rtToco;
	}

	public void setSd06rtToco(String sd06rtToco) {
		this.sd06rtToco = sd06rtToco;
	}

	public String getSd06rtBigo() {
		return sd06rtBigo;
	}

	public void setSd06rtBigo(String sd06rtBigo) {
		this.sd06rtBigo = sd06rtBigo;
	}

	public String getSd06rtMgdt() {
		return sd06rtMgdt;
	}

	public void setSd06rtMgdt(String sd06rtMgdt) {
		this.sd06rtMgdt = sd06rtMgdt;
	}

	public Long getSd06rtMgsq() {
		return sd06rtMgsq;
	}

	public void setSd06rtMgsq(Long sd06rtMgsq) {
		this.sd06rtMgsq = sd06rtMgsq;
	}

	public Long getSd06rtMgsr() {
		return sd06rtMgsr;
	}

	public void setSd06rtMgsr(Long sd06rtMgsr) {
		this.sd06rtMgsr = sd06rtMgsr;
	}

	public Date getSd06rtEndt() {
		return sd06rtEndt;
	}

	public void setSd06rtEndt(Date sd06rtEndt) {
		this.sd06rtEndt = sd06rtEndt;
	}

	public String getSd06rtEnid() {
		return sd06rtEnid;
	}

	public void setSd06rtEnid(String sd06rtEnid) {
		this.sd06rtEnid = sd06rtEnid;
	}

	public Date getSd06rtUpdt() {
		return sd06rtUpdt;
	}

	public void setSd06rtUpdt(Date sd06rtUpdt) {
		this.sd06rtUpdt = sd06rtUpdt;
	}

	public String getSd06rtUpid() {
		return sd06rtUpid;
	}

	public void setSd06rtUpid(String sd06rtUpid) {
		this.sd06rtUpid = sd06rtUpid;
	}
	
	

}
