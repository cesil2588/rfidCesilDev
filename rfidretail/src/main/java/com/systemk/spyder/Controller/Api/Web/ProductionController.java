package com.systemk.spyder.Controller.Api.Web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BatchTriggerService;
import com.systemk.spyder.Service.ProductionStorageService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/production")
public class ProductionController {
	
	@Autowired
	private ProductionStorageService productionStorageService;
	
	@Autowired
	private ProductionStorageRepository productionStorageRepository;
	
	@Autowired
	private StorageScheduleLogService storageScheduleLogService;
	
	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private BatchTriggerService batchTriggerService;
	
	@Autowired
	private UserNotiService userNotiService;
	
	/**
	 * 생산 재고 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProductionStorage>> findAll(@PageableDefault(sort = {"productionStorageSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
														  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
														  @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
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
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		return new ResponseEntity<Page<ProductionStorage>>(productionStorageService.findAll(startDate, endDate, companySeq, subCompanyName, productYy, productSeason, style, color, styleSize, "", search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 생산 재고 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/countAll")
	public ResponseEntity<String> countAll(@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
										   @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
										   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
										   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
										   @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
										   @RequestParam(value = "search", required = false, defaultValue = "") String search,
										   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		JSONObject obj = productionStorageService.countAll(startDate, endDate, companySeq, subCompanyName, productSeason, search, option, "");
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 생산 재고 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<ProductionStorage> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<ProductionStorage>(productionStorageRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 출고 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/releaseGroup")
	public ResponseEntity<Map<String,Object>> releaseGroupList(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<StorageScheduleModel> releaseGroupList = storageScheduleLogService.findReleaseGroupList(startDate, endDate, companySeq, confirmYn, "01", search, option, pageable);
		Long totaElements = storageScheduleLogService.CountReleaseGroupList(startDate, endDate, companySeq, confirmYn, "01", search, option);
		
		obj.put("content", releaseGroupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 출고 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/releaseGroup/countAll")
	public ResponseEntity<Map<String,Object>> releaseGroupCountAll(@PageableDefault(size = 10) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		Map<String,Object> boxCountObj = new HashMap<String,Object>();
		Map<String,Object> boxTagCountObj = new HashMap<String,Object>();
		
		CountModel boxCountModel = storageScheduleLogService.storageScheduleLogBoxCount(startDate, endDate, companySeq, "", "01");
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("stat3Amount", boxCountModel.getStat3_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());
		
		CountModel boxTagCountModel = storageScheduleLogService.storageScheduleLogTagCount(startDate, endDate, companySeq, "", "01");
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("stat3Amount", boxTagCountModel.getStat3_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());
		
		obj.put("boxCount", boxCountObj);
		obj.put("boxTagCount", boxTagCountObj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 출고 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/release")
	public ResponseEntity<Page<StorageScheduleLog>> releaseList(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<StorageScheduleLog>>(storageScheduleLogService.findAll(startDate, endDate, companySeq, confirmYn, completeYn, style, color, styleSize, search, option, pageable, "10-R"), HttpStatus.OK);
	}
	
	/**
	 * 출고 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/release/countAll")
	public ResponseEntity<StorageScheduleModel> countAll(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
										   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
										   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
										   @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
										   @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
										   @RequestParam(value = "style", required = false, defaultValue = "") String style,
										   @RequestParam(value = "color", required = false, defaultValue = "") String color,
										   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
										   @RequestParam(value = "search", required = false, defaultValue = "") String search,
		  								   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		StorageScheduleModel countModel = storageScheduleLogService.storageScheduleLogCount(startDate, endDate, companySeq, confirmYn, completeYn, style, color, styleSize, option, search, "01");
		
		return new ResponseEntity<StorageScheduleModel>(countModel, HttpStatus.OK);
	}
	
	/**
	 * 출고 작업 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/releaseGroup")
	public ResponseEntity<Map<String,Object>> updateReleaseGroup(@RequestBody List<StorageScheduleModel> groupList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.updateReleaseGroup(groupList), HttpStatus.OK);
	}
	
	
	/**
	 * 출고 작업 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/releaseGroup")
	public ResponseEntity<Map<String,Object>> deleteReleaseGroup(@RequestBody List<StorageScheduleModel> groupList) throws Exception{
		
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.deleteReleaseGroup(groupList), HttpStatus.OK);
	}
	
	/**
	 * 출고 요청 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/release")
	public ResponseEntity<Map<String,Object>> updateRelease(@RequestBody List<StorageScheduleLog> storageScheduleLogList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.updateRelease(storageScheduleLogList), HttpStatus.OK);
	}
	
	
	/**
	 * 출고 요청 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/release")
	public ResponseEntity<Map<String,Object>> deleteRelease(@RequestBody List<StorageScheduleLog> storageScheduleLogList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.deleteRelease(storageScheduleLogList), HttpStatus.OK);
	}
	
	/**
	 * 출고 요청 삭제(관리자 요청)
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/releaseConfirm")
	public ResponseEntity<Map<String,Object>> deleteReleaseConfirm(@RequestBody List<StorageScheduleLog> storageScheduleLogList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(storageScheduleLogService.deleteReleaseConfirm(storageScheduleLogList), HttpStatus.OK);
	}
	
	
	/**
	 * 출고 정보 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/release/{seq}")
	public ResponseEntity<StorageScheduleLog> findRelease(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<StorageScheduleLog>(storageScheduleLogRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 체크된 출고 작업 엑셀 다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/release/groupExcelDownload")
	public AbstractXlsView groupExcelDownload(@RequestBody List<StorageScheduleModel> groupList)throws Exception{
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("생산 출고 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);
		        
		        Row row = null;
		        Cell cell = null;
		        
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		        style.setAlignment((short)2);	
		        style.setVerticalAlignment((short)1);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 17) {
		        	
		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } 
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        
		        cell = row.createCell(0);
		        cell.setCellValue("생성일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(1);
		        cell.setCellValue("작업라인");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(2);
		        cell.setCellValue("타입");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(3); 
		        cell.setCellValue("바코드");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(4);
		        cell.setCellValue("스타일");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(5);
		        cell.setCellValue("컬러");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(6);
		        cell.setCellValue("사이즈");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(7);
		        cell.setCellValue("오더차수");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(8);
		        cell.setCellValue("수량");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(9);
		        cell.setCellValue("총 수량");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(9);
		        cell.setCellValue("출발지");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(10);
		        cell.setCellValue("도착지");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(11);
		        cell.setCellValue("확정여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(12);
		        cell.setCellValue("확정일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(13);
		        cell.setCellValue("입고여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(14);
		        cell.setCellValue("입고여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(15);
		        cell.setCellValue("입고일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(16);
		        cell.setCellValue("INVOICE번호");
		        cell.setCellStyle(style);
		         
		        int rowIndex = 1;
		        String excelName = "production_release_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");
		        
		        for(StorageScheduleModel group : groupList){
		        	
		        	List<StorageScheduleLog> scheduleList = storageScheduleLogService.findAll(group.getCreateDate(), group.getWorkLine(), group.getStartCompanySeq());
		        	
		        	for(StorageScheduleLog schedule : scheduleList) {
		        		
		        		int count = 0;
		        		int totalAmount = 0;
		        		int startRowIndex = rowIndex;
		        		int endRowIndex = startRowIndex + (schedule.getStorageScheduleDetailLog().size() - 1);
		        		
		        		for(StorageScheduleDetailLog detailLog : schedule.getStorageScheduleDetailLog()) {
		        			totalAmount += detailLog.getAmount();
		        		};
		        		
		        		for(StorageScheduleDetailLog detailLog : schedule.getStorageScheduleDetailLog()) {
		        			row = worksheet.createRow(rowIndex++);
		        			
		        			if(count == 0) {
		        				
		        				cell = row.createCell(0);
		        				cell.setCellValue(schedule.getCreateDate());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(1);
		        				cell.setCellValue(schedule.getWorkLine());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(2);
		        				cell.setCellValue(schedule.getOrderType().equals("OP-R") ? "물류 입고" : schedule.getOrderType().equals("OP-R2") ? "해외 입고" : "반품");
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(3);
		        				cell.setCellValue(schedule.getBoxInfo().getBarcode());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(9);
		        				cell.setCellValue(totalAmount);
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(10);
		        				cell.setCellValue(schedule.getBoxInfo().getStartCompanyInfo().getName());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(11);
		        				cell.setCellValue(schedule.getBoxInfo().getEndCompanyInfo().getName());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(12);
		        				cell.setCellValue(schedule.getConfirmYn());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(13);
		        				cell.setCellValue(schedule.getConfirmDate() != null ? CalendarUtil.convertFormat("yyyy-MM-dd HH:mm:ss", schedule.getConfirmDate()) : "-");
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(14);
		        				cell.setCellValue(schedule.getCompleteYn());
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(15);
		        				cell.setCellValue(schedule.getCompleteDate() != null ? CalendarUtil.convertFormat("yyyy-MM-dd HH:mm:ss", schedule.getCompleteDate()) : "-");
		        				cell.setCellStyle(style);
		        				
		        				cell = row.createCell(16);
		        				cell.setCellValue(schedule.getInvoiceNum());
		        				cell.setCellStyle(style);
		        				
						        count++;
					        }
		        			
		        			cell = row.createCell(4);
	        				cell.setCellValue(detailLog.getStyle());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(5);
	        				cell.setCellValue(detailLog.getColor());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(6);
	        				cell.setCellValue(detailLog.getSize());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(7);
	        				cell.setCellValue(detailLog.getOrderDegree());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(8);
	        				cell.setCellValue(detailLog.getAmount());
	        				cell.setCellStyle(style);
		        		}
		        		
						if (startRowIndex != endRowIndex) {
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 0, 0)); // 생성일자
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 1, 1)); // 작업라인
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 2, 2)); // 타입
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 3, 3)); // 바코드
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 9, 9)); // 총 수량
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 10, 10)); // 출발지
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 11, 11)); // 도착지
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 12, 12)); // 확정여부
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 13, 13)); // 확정일자
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 14, 14)); // 입고여부
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 15, 15)); // 입고일자
							worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 16, 16)); // 인보이스번호
						}
						
			        }
		        }
		        
		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }
				
			}
		};
	}
	
	/**
	 * 체크된 출고 작업 엑셀 다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/release/excelDownload")
	public AbstractXlsView excelDownload(@RequestBody List<StorageScheduleLog> scheduleList)throws Exception{
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("생산 출고 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);
		        
		        Row row = null;
		        Cell cell = null;
		        
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		        style.setAlignment((short)2);	
		        style.setVerticalAlignment((short)1);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 17) {
		        	
		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 2000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } 
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        
		        cell = row.createCell(0);
		        cell.setCellValue("생성일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(1);
		        cell.setCellValue("작업라인");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(2);
		        cell.setCellValue("타입");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(3); 
		        cell.setCellValue("바코드");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(4);
		        cell.setCellValue("스타일");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(5);
		        cell.setCellValue("컬러");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(6);
		        cell.setCellValue("사이즈");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(7);
		        cell.setCellValue("오더차수");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(8);
		        cell.setCellValue("수량");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(9);
		        cell.setCellValue("총 수량");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(9);
		        cell.setCellValue("출발지");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(10);
		        cell.setCellValue("도착지");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(11);
		        cell.setCellValue("확정여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(12);
		        cell.setCellValue("확정일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(13);
		        cell.setCellValue("입고여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(14);
		        cell.setCellValue("입고여부");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(15);
		        cell.setCellValue("입고일자");
		        cell.setCellStyle(style);
		        
		        cell = row.createCell(16);
		        cell.setCellValue("INVOICE번호");
		        cell.setCellStyle(style);
		         
		        int rowIndex = 1;
		        String excelName = "production_release_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");
		        
		        for(StorageScheduleLog schedule : scheduleList) {
	        		
	        		int count = 0;
	        		int totalAmount = 0;
	        		int startRowIndex = rowIndex;
	        		int endRowIndex = startRowIndex + (schedule.getStorageScheduleDetailLog().size() - 1);
	        		
	        		for(StorageScheduleDetailLog detailLog : schedule.getStorageScheduleDetailLog()) {
	        			totalAmount += detailLog.getAmount();
	        		};
	        		
	        		for(StorageScheduleDetailLog detailLog : schedule.getStorageScheduleDetailLog()) {
	        			row = worksheet.createRow(rowIndex++);
	        			
	        			if(count == 0) {
	        				
	        				cell = row.createCell(0);
	        				cell.setCellValue(schedule.getCreateDate());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(1);
	        				cell.setCellValue(schedule.getWorkLine());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(2);
	        				cell.setCellValue(schedule.getOrderType().equals("OP-R") ? "물류 입고" : schedule.getOrderType().equals("OP-R2") ? "해외 입고" : "반품");
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(3);
	        				cell.setCellValue(schedule.getBoxInfo().getBarcode());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(9);
	        				cell.setCellValue(totalAmount);
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(10);
	        				cell.setCellValue(schedule.getBoxInfo().getStartCompanyInfo().getName());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(11);
	        				cell.setCellValue(schedule.getBoxInfo().getEndCompanyInfo().getName());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(12);
	        				cell.setCellValue(schedule.getConfirmYn());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(13);
	        				cell.setCellValue(schedule.getConfirmDate() != null ? CalendarUtil.convertFormat("yyyy-MM-dd HH:mm:ss", schedule.getConfirmDate()) : "-");
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(14);
	        				cell.setCellValue(schedule.getCompleteYn());
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(15);
	        				cell.setCellValue(schedule.getCompleteDate() != null ? CalendarUtil.convertFormat("yyyy-MM-dd HH:mm:ss", schedule.getCompleteDate()) : "-");
	        				cell.setCellStyle(style);
	        				
	        				cell = row.createCell(16);
	        				cell.setCellValue(schedule.getInvoiceNum());
	        				cell.setCellStyle(style);
	        				
					        count++;
				        }
	        			
	        			cell = row.createCell(4);
        				cell.setCellValue(detailLog.getStyle());
        				cell.setCellStyle(style);
        				
        				cell = row.createCell(5);
        				cell.setCellValue(detailLog.getColor());
        				cell.setCellStyle(style);
        				
        				cell = row.createCell(6);
        				cell.setCellValue(detailLog.getSize());
        				cell.setCellStyle(style);
        				
        				cell = row.createCell(7);
        				cell.setCellValue(detailLog.getOrderDegree());
        				cell.setCellStyle(style);
        				
        				cell = row.createCell(8);
        				cell.setCellValue(detailLog.getAmount());
        				cell.setCellStyle(style);
	        		}
	        		
					if (startRowIndex != endRowIndex) {
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 0, 0)); // 생성일자
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 1, 1)); // 작업라인
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 2, 2)); // 타입
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 3, 3)); // 바코드
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 9, 9)); // 총 수량
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 10, 10)); // 출발지
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 11, 11)); // 도착지
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 12, 12)); // 확정여부
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 13, 13)); // 확정일자
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 14, 14)); // 입고여부
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 15, 15)); // 입고일자
						worksheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, 16, 16)); // 인보이스번호
					}
					
		        }
		        
		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }
				
			}
		};
	}
	
	/**
	 * 생산 입고 예정 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/schedule")
	public ResponseEntity<Page<ProductionStorage>> scheduleList(@PageableDefault(sort = {"productionStorageSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
														  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
														  @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
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
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		return new ResponseEntity<Page<ProductionStorage>>(productionStorageService.findAll(startDate, endDate, companySeq, subCompanyName, productYy, productSeason, style, color, styleSize, "schedule", search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 생산 입고 예정 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/schedule/countAll")
	public ResponseEntity<String> scheduleListCountAll(@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
										   @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
										   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
										   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
										   @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
										   @RequestParam(value = "search", required = false, defaultValue = "") String search,
										   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		JSONObject obj = productionStorageService.countAll(startDate, endDate, companySeq, subCompanyName, productSeason, search, option, "schedule");
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 생산 재고 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/schedule/{seq}")
	public ResponseEntity<ProductionStorage> scheduleFindOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<ProductionStorage>(productionStorageRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 생산 입고 배치 예약
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/scheduleBatch")
    public ResponseEntity<Boolean> scheduleBatch(@RequestParam(value = "companySeq", required = true) Long companySeq) throws Exception {
		
		boolean success = false;
		
		try {
			
		    LoginUser user = SecurityUtil.getCustomUser();
		    
		    if(user != null){
		    	
		    	UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());
		    	
		    	BatchTrigger batchTrigger = batchTriggerService.findBatchSchedule(companySeq.toString(), "3", "1");
		    	
		    	if(batchTrigger != null){
		    		return new ResponseEntity<Boolean>(success, HttpStatus.NOT_ACCEPTABLE); 
		    	}
			    
			    success = batchTriggerService.save(companySeq.toString(), "3", userInfo);
			    // 알림 추가
			    userNotiService.save(success ? "생산 입고 배치 작업이 예약되었습니다." : "생산 입고 배치 작업이 예약 실패하였습니다.", userInfo, "inspectionBatch", Long.valueOf(0));
		    }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Boolean>(success, HttpStatus.OK); 
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
				selectList = productionStorageService.selectBartagYy(companySeq);
				break;
			case "productSeason" :
				selectList = productionStorageService.selectBartagSeason(companySeq, productYy);
				break;
			case "style" :
				selectList = productionStorageService.selectBartagStyle(companySeq, productYy, productSeason);
				break;
			case "color" :
				selectList = productionStorageService.selectBartagColor(companySeq, productYy, productSeason, style);
				break;
			case "size" :
				selectList = productionStorageService.selectBartagSize(companySeq, productYy, productSeason, style, color);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
	
	
	/**
	 * 생산 출고 스타일, 컬러, 사이즈, 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/release/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectReleaseBartag(@PathVariable(value = "flag", required = true) String flag,
																	   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																	   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
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
			case "style" :
				selectList = storageScheduleLogService.selectBartagStyle(startDate, endDate, companySeq, "release");
				break;
			case "color" :
				selectList = storageScheduleLogService.selectBartagColor(startDate, endDate, companySeq, style, "release");
				break;
			case "size" :
				selectList = storageScheduleLogService.selectBartagSize(startDate, endDate, companySeq, style, color, "release");
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
}
