package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidLf01IfKey implements Serializable{

	private static final long serialVersionUID = -2318176840958246910L;
	
	//물류반품일자
	@Column(name="lf01_bpdt")
	private String lf01Bpdt;
	
	//오더
	@Column(name="lf01_orno")
	private String lf01Orno;
	
	//라인
	@Column(name="lf01_lino")
	private String lf01Lino;

	public String getLf01Bpdt() {
		return lf01Bpdt;
	}

	public void setLf01Bpdt(String lf01Bpdt) {
		this.lf01Bpdt = lf01Bpdt;
	}

	public String getLf01Orno() {
		return lf01Orno;
	}

	public void setLf01Orno(String lf01Orno) {
		this.lf01Orno = lf01Orno;
	}

	public String getLf01Lino() {
		return lf01Lino;
	}

	public void setLf01Lino(String lf01Lino) {
		this.lf01Lino = lf01Lino;
	}
	
	
}