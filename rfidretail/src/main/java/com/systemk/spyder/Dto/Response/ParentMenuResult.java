package com.systemk.spyder.Dto.Response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.systemk.spyder.Entity.Main.ChildMenu;
import com.systemk.spyder.Entity.Main.ParentMenu;

/**
 * PDA 상위 메뉴 목록
 * @author escho
 *
 */
public class ParentMenuResult implements Serializable {

	private static final long serialVersionUID = -5513057443171919310L;

	// 메뉴명
	private String menuName;

	// 메뉴코드
	private String menuCode;

	// 사용여부
	private String useYn;

	// 순서
	private int rank;

	// 하위 메뉴 목록
    private Set<ChildMenuResult> childMenu = new HashSet<ChildMenuResult>();

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

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Set<ChildMenuResult> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(Set<ChildMenuResult> childMenu) {
		this.childMenu = childMenu;
	}

	public ParentMenuResult() {
		
	}
	
	public ParentMenuResult(ParentMenu param) {
		this.menuName = param.getMenuName();
		this.menuCode = param.getMenuCode();
		this.useYn = param.getUseYn();
		this.rank = param.getRank();
		
		// ChildMenu 데이터 셋팅
		for(ChildMenu child : param.getChildMenu()) {
			this.childMenu.add(new ChildMenuResult(child));
		}
	}
}