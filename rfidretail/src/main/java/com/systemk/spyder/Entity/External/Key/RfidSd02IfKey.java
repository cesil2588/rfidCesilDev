package com.systemk.spyder.Entity.External.Key;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RfidSd02IfKey implements Serializable{

	private static final long serialVersionUID = -2318176840958246910L;
	
	//wms 오더
	@Column(name="sd02_orno")
	private String sd02Orno;
	
	//wms 오더라인
	@Column(name="sd02_lino")
	private String sd02Lino;

	public String getSd02Orno() {
		return sd02Orno;
	}

	public void setSd02Orno(String sd02Orno) {
		this.sd02Orno = sd02Orno;
	}

	public String getSd02Lino() {
		return sd02Lino;
	}

	public void setSd02Lino(String sd02Lino) {
		this.sd02Lino = sd02Lino;
	}
  
}
