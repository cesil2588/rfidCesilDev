package com.systemk.spyder.Entity.Lepsilon;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.systemk.spyder.Entity.Lepsilon.Key.TshipmentKey;

@Entity
@Table(name="TSHIPMENT")
public class Tshipment implements Serializable{

	private static final long serialVersionUID = -553792211900818129L;
	
	@EmbeddedId
	private TshipmentKey key;
	
	@Column(name="REFERENCENO", nullable = false, length = 22)
	private String referenceNo;
	
	@Column(name="STATUS", nullable = false, length = 3)
	private String status;
	
	@Column(name="EDITWHO", nullable = true, length = 14)
	private String editWho;
	
	@Column(name="CUSTORDERTYPECD", nullable = true, length = 3)
	private String custOrderTypeCd;
	
	@Transient
	private List<TboxPick> tboxPickList;

	public TshipmentKey getKey() {
		return key;
	}

	public void setKey(TshipmentKey key) {
		this.key = key;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TboxPick> getTboxPickList() {
		return tboxPickList;
	}

	public void setTboxPickList(List<TboxPick> tboxPickList) {
		this.tboxPickList = tboxPickList;
	}

	public String getEditWho() {
		return editWho;
	}

	public void setEditWho(String editWho) {
		this.editWho = editWho;
	}

	public String getCustOrderTypeCd() {
		return custOrderTypeCd;
	}

	public void setCustOrderTypeCd(String custOrderTypeCd) {
		this.custOrderTypeCd = custOrderTypeCd;
	}
}
