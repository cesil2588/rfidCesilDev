package com.systemk.spyder.Controller.Api.Web;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.UserEmailInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Security.CustomUserDetailService;
import com.systemk.spyder.Service.MailService;
import com.systemk.spyder.Service.UserService;
import com.systemk.spyder.Util.PasswordUtil;


@RestController
@RequestMapping("/member")
public class UserController {
	
	@Autowired
    private UserInfoRepository userInfoRepository;
    
	@Autowired
    private PasswordEncoder passwordEncoder;
    
	@Autowired
    private CustomUserDetailService customUserDetailService;
    
	@Autowired
    private UserService userService;
    
	@Autowired
    private MailService mailService;
    
	@Autowired
    private Environment env;
    
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserInfo> addUser(@RequestBody UserInfo user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUseYn("Y");
		user.setCheckYn("N");
		user.setRegDate(new Date());
		
		user = userInfoRepository.save(user);
		
		String adminMail = env.getProperty("admin.mail");
		
		mailService.sendMail(adminMail, "사용자가입확인", "새로운 사용자가 가입하였습니다.<br /> 로그인 아이디: " + user.getUserId() + "<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 확인해주세요.", "1");
		
		UserDetails userDetail = customUserDetailService.loadUserByUsername(user.getUserId());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetail, user.getPassword(),
					userDetail.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }
	
	@RequestMapping(value="/admin", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> addAdminUser(@RequestBody UserInfo user) {
		
		List<UserInfo> checkUserList = userInfoRepository.findByCompanyInfoCompanySeq(user.getCompanyInfo().getCompanySeq());
		
		if(checkUserList != null && checkUserList.size() > 0){
			return new ResponseEntity<UserInfo>(user, HttpStatus.CONFLICT);
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUseYn("Y");
		user.setRegDate(new Date());
		
		user = userInfoRepository.save(user);
		
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserInfo> modUser(@RequestBody UserInfo user) {
		
		UserInfo tempUserInfo = userInfoRepository.findOne(user.getUserSeq());
		
		if(tempUserInfo.getCompanyInfo().getCompanySeq() != user.getCompanyInfo().getCompanySeq()){
			List<UserInfo> checkUserList = userInfoRepository.findByCompanyInfoCompanySeq(user.getCompanyInfo().getCompanySeq());
			
			if(checkUserList != null && checkUserList.size() > 0){
				return new ResponseEntity<UserInfo>(user, HttpStatus.CONFLICT);
			}
		}
		
		if(user.getPassword() == null){
			user.setPassword(tempUserInfo.getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		user.setUpdDate(new Date());
		
        return new ResponseEntity<UserInfo>(userInfoRepository.save(user), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<UserInfo> delUser(@RequestBody UserInfo user) {
		
		/*
		user = userInfoRepository.findOne(user.getUserSeq());
		
		user.setUseYn("N");
		user.setUpdDate(new Date());
		
        return new ResponseEntity<UserInfo>(userInfoRepository.save(user), HttpStatus.OK);
        */
		
		userInfoRepository.delete(user);
		
		return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }
	
	@RequestMapping("/userAuth")
	public Principal user(Principal user) {
	    return user;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logoutPage (HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		boolean success = false;
		JSONObject obj = new JSONObject();
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
            success = true;
        }
        
        obj.put("result", success ? "success" : "false");
        
        return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<UserInfo> getUserList(@PageableDefault(sort = {"userSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												@RequestParam(value = "role", required = false, defaultValue = "") String role,
												@RequestParam(value = "checkYn", required = false, defaultValue = "") String checkYn,
												@RequestParam(value = "search", required = false, defaultValue = "") String search,
												@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return userService.findAll(companySeq, role, checkYn, search, option, pageable);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public UserInfo getUserInfo(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		
		return userInfoRepository.findOne(seq); 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/userIdVerification/{userId}")
	public Long getUserIdVerification(@PathVariable(value = "userId", required = true) String userId) throws Exception {
		
		return userInfoRepository.countByUserId(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/emailVerification/{email:.+}")
	public Long getEmailVerification(@PathVariable(value = "email", required = true) String email) throws Exception {
		return userInfoRepository.countByUserEmailInfoEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/emailVerification/{userSeq}/{email:.+}")
	public Long getEmailVerification(@PathVariable(value = "email", required = true) String email,
									 @PathVariable(value = "userSeq", required = true) Long userSeq) throws Exception {
		return userInfoRepository.countByUserSeqNotAndUserEmailInfoEmail(userSeq, email);
	}
	
	@RequestMapping(value="/findUserId", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> findUserId(@RequestBody UserEmailInfo emailInfo){
        return new ResponseEntity<UserInfo>(userInfoRepository.findByUserEmailInfoEmail(emailInfo.getEmail()), HttpStatus.OK);
    }
	
	@RequestMapping(value="/findPassword", method = RequestMethod.POST)
    public ResponseEntity<String> findPassword(@RequestBody UserInfo user) throws Exception{
		
		boolean success = false;
		JSONObject obj = new JSONObject();
		String tempPassword = "";
		
		Iterator<UserEmailInfo> iter = user.getUserEmailInfo().iterator();
		UserEmailInfo emailInfo = new UserEmailInfo();
		while(iter.hasNext()){
			emailInfo = iter.next();
		}
		UserInfo userInfo = userInfoRepository.findByUserIdAndUserEmailInfoEmail(user.getUserId(), emailInfo.getEmail());
		
		if(userInfo != null){
			tempPassword = PasswordUtil.randomPassword();
			userInfo.setPassword(passwordEncoder.encode(tempPassword));
			userInfoRepository.save(userInfo);
			success = true;
		}
		
		obj.put("result", success);
		obj.put("tempPassword", tempPassword);
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
    }
	
	
	@RequestMapping(method = RequestMethod.POST, value="/adminTestLogin")
    public ResponseEntity<UserInfo> adminTestLogin(@RequestBody UserInfo user) {
		
		UserInfo userInfo = userInfoRepository.findByUserId(user.getUserId());
		
		UserDetails userDetail = customUserDetailService.loadUserByUsername(user.getUserId());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetail, userInfo.getPassword(),
					userDetail.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }
}
