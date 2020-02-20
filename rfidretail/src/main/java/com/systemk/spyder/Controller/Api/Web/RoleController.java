package com.systemk.spyder.Controller.Api.Web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.RoleInfo;
import com.systemk.spyder.Service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	public RoleService roleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<RoleInfo> findAllPage(@PageableDefault(sort = {"role"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
			@RequestParam(value = "role", required = false, defaultValue = "") String role,
			@RequestParam(value = "useYn", required = false, defaultValue = "") String useYn,   
			@RequestParam(value = "style", required = false, defaultValue = "") String style,
			@RequestParam(value = "search", required = false, defaultValue = "") String search,
			@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return roleService.findAllPage(role,useYn,search, option, pageable);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/selectList")
	public List<RoleInfo> findAll() throws Exception {
		return roleService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addRole(@RequestBody RoleInfo roleInfo) throws Exception {

		return new ResponseEntity<Map<String, Object>>(roleService.addRole(roleInfo), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateRole(@RequestBody RoleInfo roleInfo) throws Exception {
		return new ResponseEntity<Map<String, Object>>(roleService.updateRole(roleInfo), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addList")
	public List<RoleInfo> findSelectList() throws Exception {
		return roleService.findSelectGroupBy();
	}

}
