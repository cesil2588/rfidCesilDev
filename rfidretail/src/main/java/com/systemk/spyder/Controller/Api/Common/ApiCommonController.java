package com.systemk.spyder.Controller.Api.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Response.ApiUserInfoResult;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.AppService;
import com.systemk.spyder.Service.InitService;
import com.systemk.spyder.Service.MobileService;
import com.systemk.spyder.Service.RfidTagStatusService;
import com.systemk.spyder.Service.UserService;

@RestController
@RequestMapping("/api")
public class ApiCommonController {

	@Autowired
	private UserService userService;

	@Autowired
	private MobileService mobileService;

	@Autowired
	private InitService initService;

	@Autowired
	private RfidTagStatusService rfidTagStatusService;

	@Autowired
	private AppService appService;

	/**
	 * 사용자 로그인(Get 방식)
	 * @param userId
	 * @param password
	 * @param version
	 * @param type
	 * @param appType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public List<ApiUserInfoResult> restMemberLogin(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
									 @RequestParam(value = "password", required = false, defaultValue = "") String password,
									 @RequestParam(value = "version", required = false, defaultValue = "") String version,
									 @RequestParam(value = "type", required = false, defaultValue = "") String type,
									 @RequestParam(value = "appType", required = false, defaultValue = "1") String appType) throws Exception {

		return userService.restMemberLogin(userId, password, version, type, appType);
	}

	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public ApiUserInfoResult restMemberLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null){
        	return new ApiUserInfoResult(ApiResultConstans.LOGOUT_FAIL_MESSAGE, ApiResultConstans.ERROR);
        }

        new SecurityContextLogoutHandler().logout(request, response, auth);

		return new ApiUserInfoResult(ApiResultConstans.LOGOUT_SUCCESS_MESSAGE, ApiResultConstans.SUCCESS);
	}

	// 동기화시 버전 체크
	@RequestMapping(value = "/app/versionCheck", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> versionCheck(@RequestParam(value = "version", required = false, defaultValue = "") String version,
									 						@RequestParam(value = "appType", required = false, defaultValue = "") String appType) throws Exception {

		return new ResponseEntity<Map<String, Object>>(appService.versionCheck(version, appType), HttpStatus.OK);
	}

	/**
	 * 태그 유효성 체크
	 * @param companySeq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.ExternalView.class)
	@RequestMapping(method = RequestMethod.GET, value = "/rfidTagValid/{rfidTag}")
	public ResponseEntity<Map<String, Object>> rfidTagValid(@PathVariable(value = "rfidTag", required = false) String rfidTag) throws Exception{

		Map<String, Object> obj = rfidTagStatusService.findByRfidTag(rfidTag);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}


	/**
	 * 모바일 M/W 태그 입고 작업 리스트
	 * @param companySeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/mobile/inspectionList")
	public ResponseEntity<Map<String, Object>> inspectionList(@RequestParam(value = "companySeq", required = true) Long companySeq) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("styleList", mobileService.inspectionStyleList(companySeq));
		obj.put("rfidList", mobileService.inspectionRfidTagList("1", companySeq));

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 모바일 M/W 상품 출고  작업 리스트
	 * @param barcode
	 * @param userSeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/mobile/releaseList")
	public ResponseEntity<Map<String, Object>> releaseList(@RequestParam(value = "companySeq", required = true) Long companySeq) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("styleList", mobileService.releaseStyleList(companySeq));
		obj.put("rfidList", mobileService.releaseRfidTagList("2", companySeq));
		obj.put("boxList", mobileService.releaseBoxList(companySeq));

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 물류 입고 초기화
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/test/initStorage")
	public ResponseEntity<Map<String, Object>> initStorage(@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{

		return new ResponseEntity<Map<String, Object>>(initService.initStorage(type), HttpStatus.OK);
	}

	/**
	 * 물류 입고 WMS 리셋
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/test/resetStorage")
	public ResponseEntity<Map<String, Object>> resetStorage() throws Exception{

		return new ResponseEntity<Map<String, Object>>(initService.resetStorage(), HttpStatus.OK);
	}

	/**
	 * 물류 출고 초기화
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/test/initRelease")
	public ResponseEntity<Map<String, Object>> initRelease(@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{

		return new ResponseEntity<Map<String, Object>>(initService.initRelease(type), HttpStatus.OK);
	}

	/**
	 * 물류 출고 WMS 리셋
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/test/resetRelease")
	public ResponseEntity<Map<String, Object>> resetRelease() throws Exception{

		return new ResponseEntity<Map<String, Object>>(initService.resetRelease(), HttpStatus.OK);
	}
}
