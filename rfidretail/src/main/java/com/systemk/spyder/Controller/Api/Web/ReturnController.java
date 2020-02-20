package com.systemk.spyder.Controller.Api.Web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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

import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;
import com.systemk.spyder.Service.CustomBean.Group.ReturnGroupModel;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/store")
public class ReturnController {
	
	@Autowired
	private StorageScheduleLogService storageScheduleLogService;
	
	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	/**
	 * 반품 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/returnGroup")
	public ResponseEntity<Map<String,Object>> returnGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
															  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
															  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
															  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
															  @RequestParam(value = "search", required = false, defaultValue = "") String search,
															  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<ReturnGroupModel> groupList = storageScheduleLogService.findReturnGroupList(startDate, endDate, companySeq, "10-R", pageable);
		Long totaElements = storageScheduleLogService.CountReturnGroupList(startDate, endDate, companySeq, "10-R");
		
		obj.put("content", groupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 반품 작업 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/returnGroup")
	public ResponseEntity<Map<String,Object>> updateReturnGroup(@RequestBody List<StorageScheduleModel> groupList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.updateReturnGroup(groupList), HttpStatus.OK);
	}
	
	
	/**
	 * 반품 작업 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/returnGroup")
	public ResponseEntity<Map<String,Object>> deleteReturnGroup(@RequestBody List<StorageScheduleModel> groupList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.deleteReturnGroup(groupList), HttpStatus.OK);
	}
	
	/**
	 * 반품 작업 상세 리스트 조회(확정 팝업)
	 * @param groupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/returnGroupList")
	public ResponseEntity<List<StorageScheduleModel>> returnGroupList(@RequestBody List<ReturnGroupModel> groupList) throws Exception {
		return new ResponseEntity<List<StorageScheduleModel>>(storageScheduleLogService.findReturnGroupDetailList(groupList), HttpStatus.OK);
	}
	
	/**
	 * 반품 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/return")
	public ResponseEntity<Page<StorageScheduleLog>> findAll(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<StorageScheduleLog>>(storageScheduleLogService.findReturnAll(createDate, companySeq, confirmYn, completeYn, "10-R", search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 반품 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/return/countAll")
	public ResponseEntity<String> countAll(@RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
										   @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
										   @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
										   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		JSONObject obj = new JSONObject();
		JSONObject boxCountObj = new JSONObject();
		JSONObject boxTagCountObj = new JSONObject();
		
		CountModel boxCountModel = storageScheduleLogService.storageScheduleLogBoxCount(createDate, confirmYn, completeYn, companySeq, "10-R");
		
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("stat3Amount", boxCountModel.getStat3_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxTagCountModel = storageScheduleLogService.storageScheduleLogTagCount(createDate, confirmYn, completeYn, companySeq, "10-R");
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("stat3Amount", boxTagCountModel.getStat3_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 반품 정보 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/return/{seq}")
	public ResponseEntity<StorageScheduleLog> findReturn(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StorageScheduleLog>(storageScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 반품 요청 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/return")
	public ResponseEntity<Map<String,Object>> updateReturn(@RequestBody List<StorageScheduleLog> storageScheduleLogList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.updateReturn(storageScheduleLogList), HttpStatus.OK);
	}
	
	
	/**
	 * 반품 요청 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/return")
	public ResponseEntity<Map<String,Object>> deleteReturn(@RequestBody List<StorageScheduleLog> storageScheduleLogList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.deleteReturn(storageScheduleLogList), HttpStatus.OK);
	}
}
