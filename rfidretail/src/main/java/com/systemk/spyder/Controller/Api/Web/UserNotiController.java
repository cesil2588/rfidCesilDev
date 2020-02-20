package com.systemk.spyder.Controller.Api.Web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.UserNoti;
import com.systemk.spyder.Repository.Main.UserNotiRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/userNoti")
public class UserNotiController {
	
	@Autowired
	private UserNotiService userNotiService;
	
	@Autowired
	private UserNotiRepository userNotiRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/pushList")
	public ResponseEntity<List<UserNoti>> findAll() throws Exception {
		
		List<UserNoti> userNotiList = new ArrayList<UserNoti>();
		
		LoginUser user = SecurityUtil.getCustomUser();
		
		if(user != null){
			userNotiList = userNotiService.findAll(user.getUserSeq());
		}
		
		return new ResponseEntity<List<UserNoti>>(userNotiList,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/check")
	public ResponseEntity<UserNoti> checkNoti(@RequestBody UserNoti userNoti) throws Exception {
		
		userNoti.setCheckYn("Y");
		userNoti.setUpdDate(new Date());
		
		return new ResponseEntity<UserNoti>(userNotiService.update(userNoti),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<UserNoti> findAll(@PageableDefault(sort = {"userNotiSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
												@RequestParam(value = "checkYn", required = false, defaultValue = "") String checkYn,
												@RequestParam(value = "search", required = false, defaultValue = "") String search,
												@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		userSeq = SecurityUtil.getCustomUser().getUserSeq();
		
		return userNotiService.findAll(startDate, endDate, userSeq, checkYn, search, option, pageable);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<UserNoti> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<UserNoti>(userNotiRepository.findOne(seq),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public ResponseEntity<UserNoti> testNoti() throws Exception {
		return new ResponseEntity<UserNoti>(userNotiService.test("test 입니다.", Long.valueOf(1), "test", Long.valueOf(0)),HttpStatus.OK);
	}
}
