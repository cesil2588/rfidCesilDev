package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;

import javax.persistence.Column;

public class RfidSd06RtIfKey implements Serializable{

	/**
	 * 매장 이동지시 실적을 위한 Entity key
	 */
	private static final long serialVersionUID = 166897707940403715L;
	
	//이동지시일자
	@Column(name="sd06rt_iddt")
	private String sd06RtIddt;
	
	//이동지시 시퀀스
	@Column(name="sd06rt_idsq")
	private Long sd06RtIdsq;
	
	//from매장코드
	@Column(name="sd06rt_frcd")
	private String sd06RtFrcd;
	
	//from매장코너코드
	@Column(name="sd06rt_frco")
	private String sd06RtFrco;
	
	//스타일
	@Column(name="sd06rt_styl")
	private String sd06RtStyl;
	
	//나머지 제품코드
	@Column(name="sd06rt_stcd")
	private String sd06RtStcd;
	
	//박스번호
	@Column(name="sd06rt_bxno")
	private Long sd06RtBxno;

	public String getSd06RtIddt() {
		return sd06RtIddt;
	}

	public void setSd06RtIddt(String sd06RtIddt) {
		this.sd06RtIddt = sd06RtIddt;
	}

	public Long getSd06RtIdsq() {
		return sd06RtIdsq;
	}

	public void setSd06RtIdsq(Long sd06RtIdsq) {
		this.sd06RtIdsq = sd06RtIdsq;
	}

	public String getSd06RtFrcd() {
		return sd06RtFrcd;
	}

	public void setSd06RtFrcd(String sd06RtFrcd) {
		this.sd06RtFrcd = sd06RtFrcd;
	}

	public String getSd06RtFrco() {
		return sd06RtFrco;
	}

	public void setSd06RtFrco(String sd06RtFrco) {
		this.sd06RtFrco = sd06RtFrco;
	}

	public String getSd06RtStyl() {
		return sd06RtStyl;
	}

	public void setSd06RtStyl(String sd06RtStyl) {
		this.sd06RtStyl = sd06RtStyl;
	}

	public String getSd06RtStcd() {
		return sd06RtStcd;
	}

	public void setSd06RtStcd(String sd06RtStcd) {
		this.sd06RtStcd = sd06RtStcd;
	}

	public Long getSd06RtBxno() {
		return sd06RtBxno;
	}

	public void setSd06RtBxno(Long sd06RtBxno) {
		this.sd06RtBxno = sd06RtBxno;
	}
	

}
