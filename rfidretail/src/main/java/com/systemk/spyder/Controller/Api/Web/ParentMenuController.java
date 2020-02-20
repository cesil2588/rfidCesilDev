package com.systemk.spyder.Controller.Api.Web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.ParentMenu;
import com.systemk.spyder.Service.ParentMenuService;

@RestController
@RequestMapping("/parentMenu")
public class ParentMenuController {
	
	@Autowired
	public ParentMenuService parentMenuService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ParentMenu> findAllList() throws Exception {
		return parentMenuService.findAllList();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/useList")
	public List<ParentMenu> findUseList(@RequestParam(value = "useYn", required = false, defaultValue = "Y") String useYn) throws Exception {
		return parentMenuService.findUseList(useYn);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addParentMenu (@RequestBody ParentMenu parentMenu) throws Exception {
		return new ResponseEntity<Map<String, Object>>(parentMenuService.save(parentMenu), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateParentMenu (@RequestBody ParentMenu parentMenu) throws Exception {
		return new ResponseEntity<Map<String, Object>>(parentMenuService.update(parentMenu), HttpStatus.OK);
	}
}
