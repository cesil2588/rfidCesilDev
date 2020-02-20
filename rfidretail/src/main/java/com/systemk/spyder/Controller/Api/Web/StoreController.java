package com.systemk.spyder.Controller.Api.Web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Repository.Main.ErpStoreReturnScheduleRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import com.systemk.spyder.Service.StoreStorageService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreReleaseGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	private StoreStorageService storeStorageService;
	
	@Autowired
	private StoreStorageRepository storeStorageRepository;
	
	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;
	
	@Autowired
	private ReleaseScheduleLogService releaseScheduleLogService;
	
	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;
	
	/**
	 * 매장 재고 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<StoreStorage>> findAll(@PageableDefault(sort = {"storeStorageSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
														  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
														  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
														  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
														  @RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
							  							  @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
														  @RequestParam(value = "style", required = false, defaultValue = "") String style,
														  @RequestParam(value = "color", required = false, defaultValue = "") String color,
														  @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
														  @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  											  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<StoreStorage>>(storeStorageService.findAll(startDate, endDate, companySeq, productYy, productSeason, style, color, styleSize, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 매장 재고 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/countAll")
	public ResponseEntity<String> countAll(@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
										   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
										   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
										   @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
										   @RequestParam(value = "search", required = false, defaultValue = "") String search,
										   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		JSONObject obj = storeStorageService.countAll(startDate, endDate, companySeq, productSeason, search, option);
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 매장 재고 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<StoreStorage> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StoreStorage>(storeStorageRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 매장 입고 예정 작업 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageScheduleGroup")
	public ResponseEntity<Map<String, Object>> findStorageScheduleGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																		@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																		@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																		@RequestParam(value = "search", required = false, defaultValue = "") String search,
																		@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		List<StoreReleaseGroupModel> storageScheduleGroupList = releaseScheduleLogService.findStoreReleaseGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = releaseScheduleLogService.CountReleaseGroupList(startDate, endDate, companySeq, search, option);
		
		obj.put("content", storageScheduleGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장 입고 예정 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageSchedule")
	public ResponseEntity<Page<ReleaseScheduleLog>> findStorageSchedule(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String size,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<ReleaseScheduleLog>>(releaseScheduleLogService.findAll(createDate, companySeq, completeYn, style, color, size, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 매장 입고 예정 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/storageSchedule/countAll")
	public ResponseEntity<Map<String, Object>> countAll(@RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
			 											@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
			 											@RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> boxCountObj = new HashMap<String, Object>();
		Map<String, Object> boxStyleObj = new HashMap<String, Object>();
		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();
		
		CountModel boxCountModel = releaseScheduleLogService.storeReleaseScheduleLogBoxCount(createDate, companySeq, completeYn, "");
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("stat3Amount", boxCountModel.getStat3_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxStyleModel = releaseScheduleLogService.storeReleaseScheduleLogStyleCount(createDate, companySeq, completeYn, "");
		boxStyleObj.put("stat2Amount", boxStyleModel.getStat2_amount());
		boxStyleObj.put("stat3Amount", boxStyleModel.getStat3_amount());
		boxStyleObj.put("totalAmount", boxStyleModel.getTotal_amount());
		
		CountModel boxTagCountModel = releaseScheduleLogService.storeReleaseScheduleLogTagCount(createDate, companySeq, completeYn, "");
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("stat3Amount", boxTagCountModel.getStat3_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxStyleCount", boxStyleObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장 입고 예정 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageSchedule/{seq}")
	public ResponseEntity<ReleaseScheduleLog> findStorageSchedule(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<ReleaseScheduleLog>(releaseScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 연도, 시즌, 스타일, 컬러, 사이즈, 오더차수, 추가발주 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectBartag(@PathVariable(value = "flag", required = true) String flag,
																@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
																@RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
																@RequestParam(value = "style", required = false, defaultValue = "") String style,
																@RequestParam(value = "color", required = false, defaultValue = "") String color,
																@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "productYy" :
				selectList = storeStorageService.selectBartagYy(companySeq);
				break;
			case "productSeason" :
				selectList = storeStorageService.selectBartagSeason(companySeq, productYy);
				break;
			case "style" :
				selectList = storeStorageService.selectBartagStyle(companySeq, productYy, productSeason);
				break;
			case "color" :
				selectList = storeStorageService.selectBartagColor(companySeq, productYy, productSeason, style);
				break;
			case "size" :
				selectList = storeStorageService.selectBartagSize(companySeq, productYy, productSeason, style, color);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
	
	/*
	//프로시저 테스트
	@RequestMapping(method = RequestMethod.GET, value="/procedureTest")
	public ResponseEntity<Map<String,Object>> getProcedure(@RequestParam(value = "testNo", required = false, defaultValue = "") String testNo) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj = erpStoreScheduleService.getResultProcedure(testNo);
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	*/
}
