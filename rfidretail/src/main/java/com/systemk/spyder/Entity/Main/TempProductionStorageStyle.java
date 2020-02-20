package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "임시 생산입고 스타일 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TempProductionStorageStyle implements Serializable{
	
	private static final long serialVersionUID = 1015839902053670499L;

	// 임시 생산 입고 스타일 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tempStyleSeq;
	
	// 생산 재고 일련번호
	//@ApiModelProperty(notes = "생산 재고 일련번호")
	private Long productionStorageSeq;
	
	// 태그 리스트
	//@ApiModelProperty(notes = "임시 생산입고 RFID 태그 목록")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tempStyleSeq")
	private Set<TempProductionStorageTag> tagList;

	public Long getProductionStorageSeq() {
		return productionStorageSeq;
	}

	public void setProductionStorageSeq(Long productionStorageSeq) {
		this.productionStorageSeq = productionStorageSeq;
	}

	public Long getTempStyleSeq() {
		return tempStyleSeq;
	}

	public void setTempStyleSeq(Long tempStyleSeq) {
		this.tempStyleSeq = tempStyleSeq;
	}

	public Set<TempProductionStorageTag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<TempProductionStorageTag> tagList) {
		this.tagList = tagList;
	}
}
