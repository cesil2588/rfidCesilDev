package com.systemk.spyder.Controller.Api.Web;

import java.util.ArrayList;
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

import com.systemk.spyder.Entity.Lepsilon.TboxPick;
import com.systemk.spyder.Entity.Lepsilon.Tshipment;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleDetailLogRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Service.DistributionStorageService;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.InitService;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;
import com.systemk.spyder.Service.CustomBean.Group.StorageScheduleGroupModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreReleaseGroupModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreScheduleGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Util.PagingUtil;

@RestController
@RequestMapping("/distribution")
public class DistributionController {
	
	@Autowired
	private DistributionStorageService distributionStorageService;
	
	@Autowired
	private DistributionStorageRepository distributionStorageRepository;
	
	@Autowired
	private StorageScheduleLogService storageScheduleLogService;
	
	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;
	
	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;
	
	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;
	
	@Autowired
	private ReleaseScheduleLogService releaseScheduleLogService;
	
	@Autowired
	private ReleaseScheduleDetailLogRepository releaseScheduleDetailLogRepository;
	
	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;
	
	@Autowired
	private InitService initService;

	
	/**
	 * 물류 재고 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<DistributionStorage>> findAll(@PageableDefault(sort = {"distributionStorageSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
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
		/*
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		*/
		return new ResponseEntity<Page<DistributionStorage>>(distributionStorageService.findAll(startDate, endDate, companySeq, productYy, productSeason, style, color, styleSize, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 물류 재고 수량 조회
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
		
		/*
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		*/
		
		JSONObject obj = distributionStorageService.countAll(startDate, endDate, companySeq, productSeason, search, option);
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 물류 재고 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<DistributionStorage> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<DistributionStorage>(distributionStorageRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 물류 입고 예정 작업 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageScheduleGroup")
	public ResponseEntity<Map<String, Object>> findStorageScheduleGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
																		@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																		@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																		@RequestParam(value = "search", required = false, defaultValue = "") String search,
																		@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		List<StorageScheduleGroupModel> storageScheduleGroupList = storageScheduleLogService.findStorageScheduleGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = storageScheduleLogService.CountStorageScheduleGroupList(startDate, endDate, companySeq, search, option);
		
		obj.put("content", storageScheduleGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 예정 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageSchedule")
	public ResponseEntity<Page<StorageScheduleLog>> findStorageSchedule(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "arrivalDate", required = false, defaultValue = "") String arrivalDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<StorageScheduleLog>>(storageScheduleLogService.findAll(arrivalDate, companySeq, "Y", completeYn, style, color, styleSize, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 예정 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/storageSchedule/countAll")
	public ResponseEntity<Map<String, Object>> storageScheduleCountAll(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																	   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> boxCountObj = new HashMap<String, Object>();
		Map<String, Object> boxStyleObj = new HashMap<String, Object>();
		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();
		
		CountModel boxCountModel = storageScheduleLogService.distributionStockStorageScheduleLogBoxCount(startDate, endDate, companySeq);
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxStyleModel = storageScheduleLogService.distributionStockStorageScheduleLogStyleCount(startDate, endDate, companySeq);
		boxStyleObj.put("stat1Amount", boxStyleModel.getStat1_amount());
		boxStyleObj.put("stat2Amount", boxStyleModel.getStat2_amount());
		boxStyleObj.put("totalAmount", boxStyleModel.getTotal_amount());
		
		CountModel boxTagCountModel = storageScheduleLogService.distributionStockStorageScheduleLogTagCount(startDate, endDate, companySeq);
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxStyleCount", boxStyleObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 예정 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageSchedule/{seq}")
	public ResponseEntity<StorageScheduleLog> findStorageSchedule(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StorageScheduleLog>(storageScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
	
	/**
	 * 매장 출고 예정 작업 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReleaseScheduleGroup")
	public ResponseEntity<Map<String, Object>> findStoreReleaseScheduleGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
																		@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																		@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																		@RequestParam(value = "search", required = false, defaultValue = "") String search,
																		@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		List<StoreScheduleGroupModel> storeScheduleGroupList = erpStoreScheduleService.findStoreScheduleGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = erpStoreScheduleService.CountStoreScheduleGroupList(startDate, endDate, companySeq, search, option);
		
		obj.put("content", storeScheduleGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 예정 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReleaseSchedule")
	public ResponseEntity<Page<ErpStoreSchedule>> findStoreReleaseSchedule(@PageableDefault(sort = {"completeDate"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "completeDate", required = false, defaultValue = "") String completeDate,
												 @RequestParam(value = "completeSeq", required = false, defaultValue = "") String completeSeq,
												 @RequestParam(value = "completeType", required = false, defaultValue = "") String completeType,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String size,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<ErpStoreSchedule>>(erpStoreScheduleService.findAll(completeDate, completeSeq, completeType, style, color, size, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 예정 수량 조회
	 * @return
	 * @throws Exception
	 */
	/*
	@RequestMapping(method = RequestMethod.GET, value="/storeSchedule/countAll")
	public ResponseEntity<Map<String, Object>> storeScheduleCountAll(@RequestParam(value = "arrivalDate", required = false, defaultValue = "") String arrivalDate,
			 														 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
			 														 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> boxCountObj = new HashMap<String, Object>();
		Map<String, Object> boxStyleObj = new HashMap<String, Object>();
		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();
		
		CountModel boxCountModel = storageScheduleLogService.distributionStockStorageScheduleLogBoxCount(arrivalDate, companySeq, completeYn, "");
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxStyleModel = storageScheduleLogService.distributionStockStorageScheduleLogStyleCount(arrivalDate, companySeq, completeYn, "");
		boxStyleObj.put("stat1Amount", boxStyleModel.getStat1_amount());
		boxStyleObj.put("stat2Amount", boxStyleModel.getStat2_amount());
		boxStyleObj.put("totalAmount", boxStyleModel.getTotal_amount());
		
		CountModel boxTagCountModel = storageScheduleLogService.distributionStockStorageScheduleLogTagCount(arrivalDate, companySeq, completeYn, "");
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxStyleCount", boxStyleObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	*/
	
	/**
	 * 매장 출고 예정 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReleaseSchedule/{seq}")
	public ResponseEntity<Map<String, Object>> findStoreReleaseSchedule(@PathVariable(value = "seq", required = true) Long seq) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(seq);
		List<ReleaseScheduleDetailLog> releaseScheduleDetailLogList = releaseScheduleDetailLogRepository.findByErpStoreScheduleSeq(seq);
		
		obj.put("erpStoreSchedule", erpStoreSchedule);
		obj.put("releaseScheduleDetailLogList", releaseScheduleDetailLogList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK); 
	}
	
	/**
	 * 매장 출고 예정 일괄 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeScheduleAll")
	public ResponseEntity<List<ErpStoreSchedule>> findStoreScheduleAll() throws Exception {
		return new ResponseEntity<List<ErpStoreSchedule>>(erpStoreScheduleRepository.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReleaseGroup")
	public ResponseEntity<Map<String,Object>> storeReleaseGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		List<StoreReleaseGroupModel> releaseGroupList = releaseScheduleLogService.findStoreReleaseGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = releaseScheduleLogService.CountReleaseGroupList(startDate, endDate, companySeq, search, option);
		
		obj.put("content", releaseGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 작업 그룹 카운트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReleaseGroup/countAll")
	public ResponseEntity<Map<String,Object>> storeReleaseGroupCountAll(@PageableDefault(size = 10) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		Map<String,Object> boxCountObj = new HashMap<String,Object>();
		Map<String,Object> boxTagCountObj = new HashMap<String,Object>();
		
		CountModel boxCountModel = releaseScheduleLogService.storeReleaseScheduleLogBoxCount(startDate, endDate, companySeq, "");
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("stat3Amount", boxCountModel.getStat3_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxTagCountModel = releaseScheduleLogService.storeReleaseScheduleLogTagCount(startDate, endDate, companySeq, "");
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("stat3Amount", boxTagCountModel.getStat3_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeRelease")
	public ResponseEntity<Page<ReleaseScheduleLog>> findAll(@PageableDefault(sort = {"releaseScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
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
	 * 매장 출고 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/storeRelease/countAll")
	public ResponseEntity<String> storeReleaseCountAll(@RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
										   			   @RequestParam(value = "workLine", required = false, defaultValue = "") String workLine,
										   			   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		JSONObject obj = new JSONObject();
		JSONObject boxCountObj = new JSONObject();
		JSONObject boxTagCountObj = new JSONObject();
		
		CountModel boxCountModel = releaseScheduleLogService.storeReleaseScheduleLogBoxCount(createDate, workLine, companySeq, "");
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("stat3Amount", boxCountModel.getStat3_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxTagCountModel = releaseScheduleLogService.storeReleaseScheduleLogTagCount(createDate, workLine, companySeq, "");
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("stat3Amount", boxTagCountModel.getStat3_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 매장 출고 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeRelease/{seq}")
	public ResponseEntity<ReleaseScheduleLog> findStoreRelease(@PathVariable(value = "seq", required = true) Long seq) throws Exception {
		return new ResponseEntity<ReleaseScheduleLog>(releaseScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
//	/**
//	 * 온라인 출고 예정 작업 리스트 조회
//	 * @param pageable
//	 * @param search
//	 * @param option
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(method = RequestMethod.GET, value = "/onlineScheduleGroup")
//	public ResponseEntity<Map<String, Object>> findOnlineScheduleGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
//																		@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
//																		@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
//																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
//																		@RequestParam(value = "search", required = false, defaultValue = "") String search,
//																		@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
//		Map<String,Object> obj = new HashMap<String,Object>();
//		
//		List<StoreScheduleGroupModel> storeScheduleGroupList = erpStoreScheduleService.findStoreScheduleGroupList(startDate, endDate, companySeq, search, option, pageable);
//		Long totaElements = erpStoreScheduleService.CountStoreScheduleGroupList(startDate, endDate, companySeq, search, option);
//		
//		obj.put("content", storeScheduleGroupList);
//		
//		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
//		
//		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
//	}
//	
//	/**
//	 * 매장 출고 예정 정보 리스트 조회
//	 * @param pageable
//	 * @param search
//	 * @param option
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(method = RequestMethod.GET, value = "/storeSchedule")
//	public ResponseEntity<Page<ErpStoreSchedule>> findStoreSchedule(@PageableDefault(sort = {"completeDate"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
//												 @RequestParam(value = "completeDate", required = false, defaultValue = "") String completeDate,
//												 @RequestParam(value = "completeSeq", required = false, defaultValue = "") String completeSeq,
//												 @RequestParam(value = "completeType", required = false, defaultValue = "") String completeType,
//												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
//												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
//												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String size,
//												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
//			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
//		
//		return new ResponseEntity<Page<ErpStoreSchedule>>(erpStoreScheduleService.findAll(completeDate, completeSeq, completeType, style, color, size, search, option, pageable), HttpStatus.OK);
//	}
//	
//	/**
//	 * 매장 출고 예정 수량 조회
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(method = RequestMethod.GET, value="/storeSchedule/countAll")
//	public ResponseEntity<Map<String, Object>> countAllStoreSchedule(@RequestParam(value = "arrivalDate", required = false, defaultValue = "") String arrivalDate,
//			 											@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
//			 											@RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn) throws Exception {
//		
//		Map<String, Object> obj = new HashMap<String, Object>();
//		Map<String, Object> boxCountObj = new HashMap<String, Object>();
//		Map<String, Object> boxStyleObj = new HashMap<String, Object>();
//		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();
//		
//		CountModel boxCountModel = storageScheduleLogService.distributionStockStorageScheduleLogBoxCount(arrivalDate, companySeq, completeYn, "");
//		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
//		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
//		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
//		
//		CountModel boxStyleModel = storageScheduleLogService.distributionStockStorageScheduleLogStyleCount(arrivalDate, companySeq, completeYn, "");
//		boxStyleObj.put("stat1Amount", boxStyleModel.getStat1_amount());
//		boxStyleObj.put("stat2Amount", boxStyleModel.getStat2_amount());
//		boxStyleObj.put("totalAmount", boxStyleModel.getTotal_amount());
//		
//		CountModel boxTagCountModel = storageScheduleLogService.distributionStockStorageScheduleLogTagCount(arrivalDate, companySeq, completeYn, "");
//		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
//		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
//		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
//		
//		obj.put("boxCount", boxCountObj);
//		obj.put("boxStyleCount", boxStyleObj);
//		obj.put("boxTagCount", boxTagCountObj);
//		
//		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
//	}
//	
//	/**
//	 * 매장 출고 예정 상세 조회
//	 * @param seq
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(method = RequestMethod.GET, value = "/storeSchedule/{seq}")
//	public ResponseEntity<Map<String, Object>> findStoreSchedule(@PathVariable(value = "seq", required = true) Long seq) throws Exception {
//		
//		Map<String, Object> obj = new HashMap<String, Object>();
//		
//		ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(seq);
//		List<ReleaseScheduleDetailLog> releaseScheduleDetailLogList = releaseScheduleDetailLogRepository.findByErpStoreScheduleErpStoreScheduleSeq(seq);
//		
//		obj.put("erpStoreSchedule", erpStoreSchedule);
//		obj.put("releaseScheduleDetailLogList", releaseScheduleDetailLogList);
//		
//		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK); 
//	}
	
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
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "productYy" :
				selectList = distributionStorageService.selectBartagYy(companySeq);
				break;
			case "productSeason" :
				selectList = distributionStorageService.selectBartagSeason(companySeq, productYy);
				break;
			case "style" :
				selectList = distributionStorageService.selectBartagStyle(companySeq, productYy, productSeason);
				break;
			case "color" :
				selectList = distributionStorageService.selectBartagColor(companySeq, productYy, productSeason, style);
				break;
			case "size" :
				selectList = distributionStorageService.selectBartagSize(companySeq, productYy, productSeason, style, color);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
	
	/**
	 * 물류입고 스타일, 컬러, 사이즈, 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/storage/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectStorageBartag(@PathVariable(value = "flag", required = true) String flag,
																	   @RequestParam(value = "arrivalDate", required = false, defaultValue = "") String arrivalDate,
																	   @RequestParam(value = "workLine", required = false, defaultValue = "") String workLine,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																	   @RequestParam(value = "style", required = false, defaultValue = "") String style,
																	   @RequestParam(value = "color", required = false, defaultValue = "") String color,
																	   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "style" :
				selectList = storageScheduleLogService.selectBartagStyle(arrivalDate, workLine, companySeq, "storage");
				break;
			case "color" :
				selectList = storageScheduleLogService.selectBartagColor(arrivalDate, workLine, companySeq, style, "storage");
				break;
			case "size" :
				selectList = storageScheduleLogService.selectBartagSize(arrivalDate, workLine, companySeq, style, color, "storage");
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
	
	/**
	 * 물류출고 스타일, 컬러, 사이즈, 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/release/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectReleaseBartag(@PathVariable(value = "flag", required = true) String flag,
																	   @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																	   @RequestParam(value = "style", required = false, defaultValue = "") String style,
																	   @RequestParam(value = "color", required = false, defaultValue = "") String color,
																	   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "style" :
				selectList = releaseScheduleLogService.selectBartagStyle(createDate, companySeq);
				break;
			case "color" :
				selectList = releaseScheduleLogService.selectBartagColor(createDate, companySeq, style);
				break;
			case "size" :
				selectList = releaseScheduleLogService.selectBartagSize(createDate, companySeq, style, color);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}

	
	/**
	 * WMS tboxpickList 정보 조회
	 * @param referenceList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/wmsTboxList")
	public ResponseEntity<Map<String, Object>> wmsTboxList(@RequestBody List<String> referenceList) throws Exception{
		
		Map<String, Object> obj = initService.tboxList(referenceList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tboxpick 바코드 사용 여부 조회
	 * @param barcode
	 * @param referenceNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/wmsTboxBarcodeCheck")
	public ResponseEntity<Map<String, Object>> wmsTboxCheck(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
															   @RequestParam(value = "referenceNo", required = false, defaultValue = "") String referenceNo) throws Exception {
		
		Map<String, Object> obj = initService.tboxBarcodeCheck(referenceNo, barcode);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK); 
	}
	
	/**
	 * WMS tboxpick 정보 등록
	 * @param tboxPick
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/wmsTbox")
	public ResponseEntity<Map<String, Object>> wmsTbox(@RequestBody TboxPick tboxPick) throws Exception{
		
		Map<String, Object> obj = initService.tboxSave(tboxPick);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tboxpick 수정
	 * @param tboxPick
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/wmsTbox")
	public ResponseEntity<Map<String, Object>> wmsTboxUpdate(@RequestBody List<TboxPick> tboxPickList) throws Exception{
		
		Map<String, Object> obj = initService.tboxUpdate(tboxPickList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tboxpick 삭제
	 * @param tboxPick
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/wmsTbox")
	public ResponseEntity<Map<String, Object>> wmsTboxDelete(@RequestBody List<TboxPick> tboxPickList) throws Exception{
		
		Map<String, Object> obj = initService.tboxDelete(tboxPickList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tboxPickList 정보 조회
	 * @param referenceList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/wmsTboxPickList")
	public ResponseEntity<Map<String, Object>> wmsTshipmentList() throws Exception{
		
		Map<String, Object> obj = initService.tboxPickList();
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tshipment 정보 조회
	 * @param referenceNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/wmsTshipment")
	public ResponseEntity<Map<String, Object>> wmsTshipment(@RequestParam(value = "referenceNo", required = false, defaultValue = "") String referenceNo) throws Exception {
		
		Map<String, Object> obj = initService.tshipment(referenceNo);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK); 
	}
	
	/**
	 * WMS tshipment 정보 등록
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/wmsTshipment")
	public ResponseEntity<Map<String, Object>> wmsTshipmentSave(@RequestBody List<TboxPick> tboxPickList) throws Exception{
		
		Map<String, Object> obj = initService.tshipmentSave(tboxPickList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tshipment 수정
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/wmsTshipment")
	public ResponseEntity<Map<String, Object>> wmsTshipmentUpdate(@RequestBody Tshipment tshipment) throws Exception{
		
		Map<String, Object> obj = initService.tshipmentUpdate(tshipment);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tshipment 삭제
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/wmsTshipment")
	public ResponseEntity<Map<String, Object>> wmsTshipmentDelete(@RequestBody List<TboxPick> tboxPickList) throws Exception{
		
		Map<String, Object> obj = initService.tshipmentDelete(tboxPickList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tshipment 초기화
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/wmsTshipmentInit")
	public ResponseEntity<Map<String, Object>> wmsTshipmentInit(@RequestBody Tshipment tshipment) throws Exception{
		
		Map<String, Object> obj = initService.tshipmentInit(tshipment);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * WMS tshipment 수정
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/wmsTshipmentTotal")
	public ResponseEntity<Map<String, Object>> wmsTshipmentTotalUpdate(@RequestBody Tshipment tshipment) throws Exception{
		
		Map<String, Object> obj = initService.tshipmentTotalUpdate(tshipment);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * ERP 매장출고예정 정보 등록
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/erpStoreSchedule")
	public ResponseEntity<Map<String, Object>> erpStoreScheduleSave(@RequestBody ErpStoreSchedule storeSchedule) throws Exception{
		
		Map<String, Object> obj = erpStoreScheduleService.save(storeSchedule);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * ERP 매장출고예정 정보 삭제
	 * @param tshipment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/erpStoreSchedule")
	public ResponseEntity<Map<String, Object>> erpStoreScheduleUpdate(@RequestBody List<ErpStoreSchedule> storeScheduleList) throws Exception{
		
		Map<String, Object> obj = erpStoreScheduleService.delete(storeScheduleList);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 박스 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleComplete")
	public ResponseEntity<Map<String, Object>> storeScheduleComplete(
								@RequestBody List<Map<String, Object>> barcodeList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = storageScheduleLogService.storeScheduleComplete(barcodeList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 작업 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleCompleteGroup")
	public ResponseEntity<Map<String, Object>> storeScheduleGroupComplete(
								@RequestBody List<Map<String, Object>> scheduleGroupList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = storageScheduleLogService.storeScheduleCompleteGroup(scheduleGroupList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 반송 작업 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleReturnGroup")
	public ResponseEntity<Map<String, Object>> storeScheduleReturnGroup(
								@RequestBody List<StorageScheduleModel> groupList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = storageScheduleLogService.storeScheduleReturnGroup(groupList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 물류 입고 반송 박스 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleReturn")
	public ResponseEntity<Map<String, Object>> storeScheduleReturn(
								@RequestBody List<StorageScheduleLog> storageScheduleLogList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = storageScheduleLogService.storeScheduleReturn(storageScheduleLogList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 물류 반품 입고 예정 작업 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageReturnScheduleGroup")
	public ResponseEntity<Map<String, Object>> findStorageReturnScheduleGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
																		@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																		@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																		@RequestParam(value = "search", required = false, defaultValue = "") String search,
																		@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		List<StorageScheduleGroupModel> storageScheduleGroupList = storageScheduleLogService.findStorageReturnScheduleGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = storageScheduleLogService.CountStorageReturnScheduleGroupList(startDate, endDate, companySeq, search, option);
		
		obj.put("content", storageScheduleGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 물류 반품 입고 예정 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/storageReturnSchedule/countAll")
	public ResponseEntity<Map<String, Object>> storageReturnScheduleCountAll(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																	   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> boxCountObj = new HashMap<String, Object>();
		Map<String, Object> boxStyleObj = new HashMap<String, Object>();
		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();
		
		CountModel boxCountModel = storageScheduleLogService.distributionStockStorageReturnScheduleLogBoxCount(startDate, endDate, companySeq);
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxStyleModel = storageScheduleLogService.distributionStockStorageReturnScheduleLogStyleCount(startDate, endDate, companySeq);
		boxStyleObj.put("stat1Amount", boxStyleModel.getStat1_amount());
		boxStyleObj.put("stat2Amount", boxStyleModel.getStat2_amount());
		boxStyleObj.put("totalAmount", boxStyleModel.getTotal_amount());
		
		CountModel boxTagCountModel = storageScheduleLogService.distributionStockStorageReturnScheduleLogTagCount(startDate, endDate, companySeq);
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxStyleCount", boxStyleObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	
	/**
	 * 물류 반품 입고 예정 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageReturnSchedule")
	public ResponseEntity<Page<StorageScheduleLog>> findReturnStorageSchedule(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "arrivalDate", required = false, defaultValue = "") String arrivalDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<StorageScheduleLog>>(storageScheduleLogService.findAll(arrivalDate, companySeq, "Y", completeYn, style, color, styleSize, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 물류 반품 입고 예정 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageReturnSchedule/{seq}")
	public ResponseEntity<StorageScheduleLog> findStorageReturnSchedule(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StorageScheduleLog>(storageScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 물류 반품 입고 미결번호별 상세 조회
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storageReturnDetailByBarcode/{barcode}")
	public ResponseEntity<Map<String,Object>> storageReturnDetailByBarcode(@PathVariable(value = "barcode", required = true) String barcode) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		List<StorageScheduleDetailLog> storageScheduleDetailList = storageScheduleLogService.findReturnScheduleByBarcode(barcode);
		obj.put("obj", storageScheduleDetailList);
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK); 
	}
	
	/**
	 * 물류 반품 입고 미결번호별 상세 조회
	 * @param barcode
	 * @return
	 * @throws Exception
	 *
	@RequestMapping(method = RequestMethod.GET, value = "/storageReturnDetailByBarcode/{barcode}")
	public ResponseEntity<List<StorageScheduleDetailLog>> storageReturnDetailByBarcode(@PathVariable(value = "barcode", required = true) String barcode) throws Exception {
		//Map<String,Object> obj = new HashMap<String,Object>();
		//List<StorageScheduleDetailLog> storageScheduleDetailList = storageScheduleLogService.findReturnScheduleByBarcode(barcode);
		//obj.put("obj", storageScheduleDetailList);
		return new ResponseEntity<List<StorageScheduleDetailLog>>(storageScheduleDetailLogRepository.findByBarcode(barcode), HttpStatus.OK); 
	}*/
}
