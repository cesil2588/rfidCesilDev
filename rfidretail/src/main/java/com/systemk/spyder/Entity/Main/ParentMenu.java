package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "부모 메뉴 Entity")
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
public class ParentMenu implements Serializable {

	private static final long serialVersionUID = -5089621975582753068L;

	// 부모메뉴일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parentMenuSeq;

	// 메뉴명
	//@ApiModelProperty(notes = "메뉴명")
	@Column(nullable = false, length = 200)
	private String menuName;

	// 메뉴코드
	//@ApiModelProperty(notes = "메뉴코드")
	@Column(nullable = false, length = 50)
	private String menuCode;

	// URL
	//@ApiModelProperty(notes = "URL")
	@Column(nullable = true, length = 200)
	private String url;

	// 타입(popup, page, collapse)
	//@ApiModelProperty(notes = "타입(popup, page, collapse)")
	@Column(nullable = false, length = 10)
	private String type;

	// 세션타입(groupCurrent, current)
	//@ApiModelProperty(notes = "세션타입(groupCurrent, current)")
	@Column(nullable = true, length = 20)
	private String sessionType;

	// AngularJS 컨트롤러
	//@ApiModelProperty(notes = "AngularJS 맵핑 컨트롤러")
	@Column(nullable = true, length = 50)
	private String mappingController;

	// 팝업 사이즈
	//@ApiModelProperty(notes = "팝업 사이즈(sm, md, xl, xxl")
	@Column(nullable = true, length = 10)
	private String size;

	// 위치
	//@ApiModelProperty(notes = "위치(top, bottom, left, right, center)")
	@Column(nullable = false, length = 10)
	private String location;

	// 사용여부
	//@ApiModelProperty(notes = "사용 여부(Y, N)")
	@Column(nullable = false, length = 1)
	private String useYn;

	// 기본 아이콘 클래스
	//@ApiModelProperty(notes = "기본 아이콘 클래스(CSS)")
	@Column(nullable = false, length = 20)
	private String iconClass;

	// 변경시 아이콘 클래스
	//@ApiModelProperty(notes = "변경시 아이콘 클래스(CSS)")
	@Column(nullable = true, length = 20)
	private String changeIconClass;

	// 순서
	//@ApiModelProperty(notes = "순서")
	@Column
	private int rank;

	// 등록일
	//@ApiModelProperty(notes = "등록일")
	@Column
	private Date regDate;

	// 수정일
	//@ApiModelProperty(notes = "수정일")
	@Column
	private Date updDate;
	
	//@ApiModelProperty(notes = "하위 메뉴 목록")
	@JsonIgnoreProperties(value = {"parentMenu"}, allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "parentMenuSeq")
    private Set<ChildMenu> childMenu;
	
	//@ApiModelProperty(notes = "등록자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regUserSeq")
	private UserInfo regUserInfo;
	
	//@ApiModelProperty(notes = "수정자")
	@JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updUserSeq")
	private UserInfo updUserInfo;
	

	public void setParentMenuSeq(Long parentMenuSeq) {
		this.parentMenuSeq = parentMenuSeq;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	public String getMappingController() {
		return mappingController;
	}

	public void setMappingController(String mappingController) {
		this.mappingController = mappingController;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getChangeIconClass() {
		return changeIconClass;
	}

	public void setChangeIconClass(String changeIconClass) {
		this.changeIconClass = changeIconClass;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public Long getParentMenuSeq() {
		return parentMenuSeq;
	}

	public Set<ChildMenu> getChildMenu() {
		return childMenu;
	}


	public void setChildMenu(Set<ChildMenu> childMenu) {
		this.childMenu = childMenu;
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