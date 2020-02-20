package com.systemk.spyder.Controller.Api.Web;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.ChildMenu;
import com.systemk.spyder.Service.ChildMenuService;

@RestController
@RequestMapping("/childMenu")
public class ChildMenuController {
	
	@Autowired
	public ChildMenuService childMenuService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addChildtMenu (@RequestBody ChildMenu childMenu) throws Exception {
		System.out.println(childMenu);
		return new ResponseEntity<Map<String, Object>>(childMenuService.save(childMenu), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateChildParentMenu (@RequestBody ChildMenu childMenu) throws Exception {
		return new ResponseEntity<Map<String, Object>>(childMenuService.update(childMenu), HttpStatus.OK);
	}
}
