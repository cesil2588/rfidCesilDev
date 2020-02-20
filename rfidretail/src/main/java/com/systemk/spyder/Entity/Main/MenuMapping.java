package com.systemk.spyder.Entity.Main;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "메뉴 맵핑 Entity")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
public class MenuMapping implements Serializable {

	private static final long serialVersionUID = -6792606989227759516L;

	// mapping id
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long menuMappingSeq;

	//@ApiModelProperty(notes = "부모 메뉴 일련번호")
	@JsonIgnoreProperties(value = {"menuMapping"},allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@OrderBy("rank")
	@JoinColumn(name = "parentMenuSeq")
	private ParentMenu parentMenu;

	//@ApiModelProperty(notes = "권한명")
	@JsonIgnoreProperties(value = {"menuMapping"}, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	private RoleInfo roleInfo;

	//@ApiModelProperty(notes = "맵핑 순서")
	private Long rank;

	public Long getMenuMappingSeq() {
		return menuMappingSeq;
	}

	public void setMenuMappingSeq(Long menuMappingSeq) {
		this.menuMappingSeq = menuMappingSeq;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public ParentMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(ParentMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
}
