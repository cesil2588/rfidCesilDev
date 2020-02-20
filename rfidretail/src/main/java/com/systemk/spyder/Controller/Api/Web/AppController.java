package com.systemk.spyder.Controller.Api.Web;

import java.util.Date;

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

import com.systemk.spyder.Entity.Main.AppInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.AppInfoRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.AppService;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppService appService;

	@Autowired
	private AppInfoRepository appInfoRepository;

	/**
	 * PDA App 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param representYn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<AppInfo>> findAll(@PageableDefault(sort = {"appSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
			  					 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  					 @RequestParam(value = "option", required = false, defaultValue = "") String option,
			  					 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			  					 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
			  					 @RequestParam(value = "type", required = false, defaultValue = "") String type,
			  					 @RequestParam(value = "representYn", required = false, defaultValue = "") String representYn) throws Exception {


		return new ResponseEntity<Page<AppInfo>>(appService.findAll(startDate, endDate, type, representYn, search, option, pageable), HttpStatus.OK);
	}

	/**
	 * PDA App 등록
	 * @param appInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AppInfo> save(@RequestBody AppInfo appInfo) throws Exception {

		if(appInfo.getRepresentYn().equals("N")){
			Long count = appService.countRepresentYn(appInfo.getType());

			if(count == 0){
				return new ResponseEntity<AppInfo>(appInfo, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			appInfo.setRegUserInfo(regUserInfo);
			appInfo.setRegDate(new Date());
		}

		appInfo.setFilePath("/storage/" + appInfo.getFileName());

        return new ResponseEntity<AppInfo>(appService.save(appInfo), HttpStatus.OK);
    }

	/**
	 * PDA App 수정
	 * @param appInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<AppInfo> update(@RequestBody AppInfo appInfo) throws Exception {

		if(appInfo.getRepresentYn().equals("N")){
			Long count = appService.countByRepresentYnAndAppSeqNotIn(appInfo.getAppSeq(), appInfo.getType());

			if(count == 0){
				return new ResponseEntity<AppInfo>(appInfo, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(user.getUserSeq());
			appInfo.setUpdUserInfo(userInfo);
			appInfo.setUpdDate(new Date());
		}

		appInfo.setFilePath("/storage/" + appInfo.getFileName());

        return new ResponseEntity<AppInfo>(appService.save(appInfo), HttpStatus.OK);
    }


	/**
	 * PDA App 상세
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<AppInfo> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<AppInfo>(appInfoRepository.findOne(seq), HttpStatus.OK);
	}

	/**
	 * PDA App 대표 버전 내려주기
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/represent")
	public ResponseEntity<AppInfo> findRepresent(@RequestParam(value = "appType", required = false, defaultValue = "") String appType) throws Exception {
		return new ResponseEntity<AppInfo>(appInfoRepository.findByRepresentYnAndType("Y", appType), HttpStatus.OK);
	}

	/**
	 * PDA App 다운로드 로그 생성
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/download/{seq}")
	public ResponseEntity<Long> downloadLog(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		appService.saveLog(seq);
		return new ResponseEntity<Long>(seq, HttpStatus.OK);
	}
}
