package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.systemk.spyder.Entity.Main.MenuMapping;

public interface MenuMappingService {
	public Map<String, Object> updateMenuMapping(List<MenuMapping> menuMapping) throws Exception;
	public Map<String, Object> deleteMenuOfRole(String role) throws Exception;
}
