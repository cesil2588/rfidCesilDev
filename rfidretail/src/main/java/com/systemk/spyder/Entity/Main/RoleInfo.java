package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "권한 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class RoleInfo implements Serializable {

	private static final long serialVersionUID = -2266158553662161814L;

	// 권한명(기본키)
	//@ApiModelProperty(notes = "권한명")
	@Id
	@Column(length = 20)
	private String role;

	// 사용 여부
	//@ApiModelProperty(notes = "사용여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String useYn;

	// 메뉴 맵핑 목록
	//@ApiModelProperty(notes = "메뉴 맵핑 목록")
	@JsonIgnoreProperties(value = {"roleInfo"}, allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "role")
	private Set<MenuMapping> menuMapping;

	// 메뉴명
	//@ApiModelProperty(notes = "메뉴명")
	@Column(nullable = false, length = 200)
	private String roleName;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@Column
	private Date regDate;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@Column
	private Date updDate;

	// 등록자
	//@ApiModelProperty(notes = "등록자")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@JoinColumn(name = "regUserSeq")
	private UserInfo regUserInfo;

	// 수정자
	//@ApiModelProperty(notes = "수정자")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@JoinColumn(name = "updUserSeq")
	private UserInfo updUserInfo;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUseYn() {
		return useYn;
	}
	
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Set<MenuMapping> getMenuMapping() {
		return menuMapping;
	}

	public void setMenuMapping(Set<MenuMapping> menuMapping) {
		this.menuMapping = menuMapping;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

}