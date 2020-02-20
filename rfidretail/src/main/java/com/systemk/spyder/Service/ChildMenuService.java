package com.systemk.spyder.Service;

import java.util.Map;

import com.systemk.spyder.Entity.Main.ChildMenu;

public interface ChildMenuService {
	public Map<String, Object> save(ChildMenu childMenu) throws Exception;
	public Map<String, Object> update(ChildMenu childMenu) throws Exception;
}
