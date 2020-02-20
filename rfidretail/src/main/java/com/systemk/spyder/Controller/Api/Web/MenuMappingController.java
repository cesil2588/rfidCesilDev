package com.systemk.spyder.Controller.Api.Web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.MenuMapping;
import com.systemk.spyder.Service.MenuMappingService;


@RestController
@RequestMapping("/menuMapping")
public class MenuMappingController {
	
	@Autowired
	private MenuMappingService menuMappingService;
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateMenuMapping(@RequestBody List<MenuMapping> menuMapping) throws Exception {
		return new ResponseEntity<Map<String, Object>>(menuMappingService.updateMenuMapping(menuMapping), HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteMenuOfRole(@RequestBody String role) throws Exception {
		return new ResponseEntity<Map<String, Object>>(menuMappingService.deleteMenuOfRole(role), HttpStatus.OK);
	}
}
