package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.ChildMenu;

/**
 * PDA 하위 메뉴 목록
 * @author escho
 *
 */
public class ChildMenuResult implements Serializable {

	private static final long serialVersionUID = -7904449813143797542L;

	// 메뉴 명
	private String menuName;

	// 메뉴코드
	private String menuCode;

	// 사용여부
	private String useYn;

	// 순서
	private int rank;

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
	
	public ChildMenuResult() {
		
	}

	public ChildMenuResult(ChildMenu param) {
		this.menuName = param.getMenuName();
		this.menuCode = param.getMenuCode();
		this.useYn = param.getUseYn();
		this.rank = param.getRank();
	}
}
