package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import com.systemk.spyder.Entity.Main.ParentMenu;
public interface ParentMenuService {
	public List<ParentMenu> findAllList() throws Exception;
	public List<ParentMenu> findUseList(String useYn) throws Exception;
	public Map<String, Object> save(ParentMenu parentMenu) throws Exception;
	public Map<String, Object> update(ParentMenu parentMenu) throws Exception;
}
