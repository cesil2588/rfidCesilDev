package com.systemk.spyder.Controller.Api.Store;

import com.systemk.spyder.Dto.Request.MemberBean;
import com.systemk.spyder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiStoreLoginController {

	private final UserService userService;

	@Autowired
	public ApiStoreLoginController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 사용자 로그인(Post 방식)
	 * @param member
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public Map<String, Object> restMemberLogin(@RequestBody MemberBean member) throws Exception {
		return userService.restMemberLogin(member);
	}
}
