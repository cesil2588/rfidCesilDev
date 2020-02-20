package com.systemk.spyder.Controller.Api.Web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.StoreMoveLog;
import com.systemk.spyder.Repository.Main.StoreMoveLogRepository;
import com.systemk.spyder.Service.StoreMoveLogService;
import com.systemk.spyder.Service.CustomBean.Group.StoreMoveGroupModel;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/store")
public class StoreMoveController {
	
	@Autowired
	private StoreMoveLogService storeMoveLogService;
	
	@Autowired
	private StoreMoveLogRepository storeMoveLogRepository;

	/**
	 * 매장간 이동 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeMoveGroup")
	public ResponseEntity<Map<String, Object>> storeMoveGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
															   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
															   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
															   @RequestParam(value = "startCompanySeq", required = false, defaultValue = "0") Long startCompanySeq,
															   @RequestParam(value = "endCompanySeq", required = false, defaultValue = "0") Long endCompanySeq,
															   @RequestParam(value = "companyType", required = false, defaultValue = "all") String companyType) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(companyType.equals("start") && (SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special"))){
			startCompanySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		} else if(companyType.equals("end") && (SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special"))){
			endCompanySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<StoreMoveGroupModel> groupList = storeMoveLogService.findAll(startDate, endDate, startCompanySeq, endCompanySeq, companyType, pageable);
		Long totaElements = storeMoveLogService.CountGroupList(startDate, endDate, startCompanySeq, endCompanySeq, companyType);
		
		obj.put("content", groupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장간 이동 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeMove")
	public ResponseEntity<Page<StoreMoveLog>> findAll(@PageableDefault(sort = {"storeMoveLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
												 @RequestParam(value = "startCompanySeq", required = false, defaultValue = "0") Long startCompanySeq,
												 @RequestParam(value = "endCompanySeq", required = false, defaultValue = "0") Long endCompanySeq,
												 @RequestParam(value = "workYn", required = false, defaultValue = "all") String workYn,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn,
												 @RequestParam(value = "disuseYn", required = false, defaultValue = "all") String disuseYn,
												 @RequestParam(value = "companyType", required = false, defaultValue = "all") String companyType,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(companyType.equals("start") && (SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special"))){
			startCompanySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		} else if(companyType.equals("end") && (SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special"))){
			endCompanySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<StoreMoveLog>>(storeMoveLogService.findAll(createDate, startCompanySeq, endCompanySeq, workYn, confirmYn, disuseYn, companyType, search, option, pageable), HttpStatus.OK);
	}
	
	
	/**
	 * 매장간 이동 정보 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeMove/{seq}")
	public ResponseEntity<StoreMoveLog> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StoreMoveLog>(storeMoveLogRepository.findOne(seq), HttpStatus.OK); 
	}
}
