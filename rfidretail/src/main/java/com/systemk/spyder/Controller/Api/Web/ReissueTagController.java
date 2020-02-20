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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
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

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.RfidTagReissueRequestRepository;
import com.systemk.spyder.Repository.Main.Specification.RfidTagReissueRequestSpecification;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.ReissueTagService;
import com.systemk.spyder.Service.RfidTagReissueRequestService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.ReissueTagGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/reissueTag")
public class ReissueTagController {

	@Autowired
	private ReissueTagService reissueTagService;
	
	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;
	
	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;
	
	@Autowired
	private RfidTagReissueRequestService rfidTagReissueRequestService;
	
	@Autowired
	private RfidTagReissueRequestRepository rfidTagReissueRequestRepository;
	
	@Autowired
	private BartagService bartagService;
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	/**
	 * 연도, 시즌, 스타일, 컬러, 사이즈, 오더차수, 추가발주 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectBartag(@PathVariable(value = "flag", required = true) String flag,
																@RequestParam(value = "type", required = false, defaultValue = "") String type,
																@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
																@RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
																@RequestParam(value = "style", required = false, defaultValue = "") String style,
																@RequestParam(value = "color", required = false, defaultValue = "") String color,
																@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
																@RequestParam(value = "orderDegree", required = false, defaultValue = "") String orderDegree) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "productYy" :
				selectList = reissueTagService.selectBartagYy(companySeq, type);
				break;
			case "productSeason" :
				selectList = reissueTagService.selectBartagSeason(companySeq, productYy, type);
				break;
			case "style" :
				selectList = reissueTagService.selectBartagStyle(companySeq, productYy, productSeason, type);
				break;
			case "color" :
				selectList = reissueTagService.selectBartagColor(companySeq, productYy, productSeason, style, type);
				break;
			case "size" :
				selectList = reissueTagService.selectBartagSize(companySeq, productYy, productSeason, style, color, type);
				break;
			case "orderDegree" :
				selectList = reissueTagService.selectBartagOrderDegree(companySeq, productYy, productSeason, style, color, styleSize, type);
				break;
			case "additionOrderDegree" :
				selectList = reissueTagService.selectBartagAdditionOrderDegree(companySeq, productYy, productSeason, style, color, styleSize, orderDegree, type);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/productionRfidTagList")
	public ResponseEntity<Page<ProductionStorageRfidTag>> productionRfidTagList(@PageableDefault(sort = {"rfidTag"}, direction = Sort.Direction.DESC, size = 200) Pageable pageable,
																	  @RequestParam(value = "seq", required = false, defaultValue = "0") Long seq,
																	  @RequestParam(value = "startRfidSeq", required = false, defaultValue = "") String startRfidSeq,
																	  @RequestParam(value = "endRfidSeq", required = false, defaultValue = "") String endRfidSeq) throws Exception {
		
		return new ResponseEntity<Page<ProductionStorageRfidTag>>(productionStorageRfidTagService.findStartEndRfidSeq(seq, startRfidSeq, endRfidSeq, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/distributionRfidTagList")
	public ResponseEntity<Page<DistributionStorageRfidTag>> distributionRfidTagList(@PageableDefault(sort = {"rfidTag"}, direction = Sort.Direction.DESC, size = 200) Pageable pageable,
																	  @RequestParam(value = "seq", required = false, defaultValue = "0") Long seq,
																	  @RequestParam(value = "startRfidSeq", required = false, defaultValue = "") String startRfidSeq,
																	  @RequestParam(value = "endRfidSeq", required = false, defaultValue = "") String endRfidSeq) throws Exception {
		
		return new ResponseEntity<Page<DistributionStorageRfidTag>>(distributionStorageRfidTagService.findStartEndRfidSeq(seq, startRfidSeq, endRfidSeq, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/storeRfidTagList")
	public ResponseEntity<Page<StoreStorageRfidTag>> storeRfidTagList(@PageableDefault(sort = {"rfidTag"}, direction = Sort.Direction.DESC, size = 200) Pageable pageable,
																	  @RequestParam(value = "seq", required = false, defaultValue = "0") Long seq,
																	  @RequestParam(value = "startRfidSeq", required = false, defaultValue = "") String startRfidSeq,
																	  @RequestParam(value = "endRfidSeq", required = false, defaultValue = "") String endRfidSeq) throws Exception {
		
		return new ResponseEntity<Page<StoreStorageRfidTag>>(storeStorageRfidTagService.findStartEndRfidSeq(seq, startRfidSeq, endRfidSeq, pageable), HttpStatus.OK);
	}
	
	
	/**
	 * 재발행요청
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	/*
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> production(@RequestBody RfidTagReissueRequest rfidTagReissueRequest) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.reissueRequest(rfidTagReissueRequest), HttpStatus.OK);
	}
	*/
	
	/**
	 * 재발행요청 완료
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/reissueComplete")
	public ResponseEntity<Map<String, Object>> reissueComplete(@RequestBody List<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailList) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.reissueComplete(rfidTagReissueRequestDetailList), HttpStatus.OK);
	}
	
	/**
	 * 태그 재발행 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/reissueTagGroup")
	public ResponseEntity<Map<String, Object>> reissueTagGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
															   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
															   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
															   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
															   @RequestParam(value = "type", required = false, defaultValue = "") String type,
															   @RequestParam(value = "search", required = false, defaultValue = "") String search,
															   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<ReissueTagGroupModel> groupList = rfidTagReissueRequestService.findAll(startDate, endDate, companySeq, type, search, option, pageable);
		Long totaElements = rfidTagReissueRequestService.CountReissueTagGroupList(startDate, endDate, companySeq, type, search, option);
		
		obj.put("content", groupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 태그 재발행 작업 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/reissueTagGroup")
	public ResponseEntity<Map<String,Object>> reissueGroupUpdate(@RequestBody List<ReissueTagGroupModel> groupList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(rfidTagReissueRequestService.reissueGroupUpdate(groupList), HttpStatus.OK);
	}
	
	
	/**
	 * 태그 재발행 작업 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/reissueTagGroup")
	public ResponseEntity<Map<String,Object>> reissueGroupDelete(@RequestBody List<ReissueTagGroupModel> groupList) throws Exception{
		return new ResponseEntity<Map<String,Object>>(rfidTagReissueRequestService.reissueGroupDelete(groupList), HttpStatus.OK);
	}
	
	/**
	 * 태그 재발행 정보 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RfidTagReissueRequest>> reissueTagList(@PageableDefault(sort = {"storageScheduleLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<RfidTagReissueRequest>>(rfidTagReissueRequestService.findAll(createDate, companySeq, confirmYn, completeYn, search, option, pageable), HttpStatus.OK);
	}
	
	/**
	 * 태그 재발행 정보 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/countAll")
	public ResponseEntity<String> countReissueTagAll(@RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
										   @RequestParam(value = "confirmYn", required = false, defaultValue = "") String confirmYn,
										   @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
										   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		JSONObject obj = new JSONObject();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		JSONObject tagCountObj = new JSONObject();
		
		CountModel tagCountModel = rfidTagReissueRequestService.reissueTagCount(createDate, companySeq, confirmYn, completeYn);
		tagCountObj.put("stat1Amount", tagCountModel.getStat1_amount());
		tagCountObj.put("stat2Amount", tagCountModel.getStat2_amount());
		tagCountObj.put("stat3Amount", tagCountModel.getStat3_amount());
		tagCountObj.put("totalAmount", tagCountModel.getTotal_amount());
		
		obj.put("tagCount", tagCountObj);
		
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 태그 재발행 정보 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<RfidTagReissueRequest> findReissueTag(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<RfidTagReissueRequest>(rfidTagReissueRequestRepository.findOne(seq), HttpStatus.OK); 
	}
	
	/**
	 * 태그 재발행 상세 그룹 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/detailList")
	public ResponseEntity<List<RfidTagReissueRequestDetail>> findDetailList(@RequestBody List<ReissueTagGroupModel> groupList) throws Exception {
		return new ResponseEntity<List<RfidTagReissueRequestDetail>>(rfidTagReissueRequestService.findDetailList(groupList), HttpStatus.OK); 
	}
	
	/**
	 * 태그 재발행 요청
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> save(@RequestBody RfidTagReissueRequest rfidTagReissueRequest) throws Exception {
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.reissueSave(rfidTagReissueRequest), HttpStatus.OK); 
	}
	
	/**
	 * 태그 재발행 요청 확인 완료
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String,Object>> updateReissueTag(@RequestBody List<RfidTagReissueRequest> rfidTagReissueRequest) throws Exception{
		return new ResponseEntity<Map<String,Object>>(rfidTagReissueRequestService.reissueUpdate(rfidTagReissueRequest), HttpStatus.OK);
	}
	
	
	/**
	 * 태그 재발행 요청 삭제
	 * @param releaseGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Map<String,Object>> deleteReissueTag(@RequestBody List<RfidTagReissueRequest> rfidTagReissueRequest) throws Exception{
		return new ResponseEntity<Map<String,Object>>(rfidTagReissueRequestService.reissueDelete(rfidTagReissueRequest), HttpStatus.OK);
	}
	
	/**
	 * 재발행 요청 그룹 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reissueGroupExcelDownload")
	public AbstractXlsView reissueGroupExcelDownload(@RequestBody List<ReissueTagGroupModel> groupList)throws Exception{
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("재발행 태그 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);
		        
		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		         
		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {
		             
		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 10000);
		            } 
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("ERPKey");
		        row.createCell(7).setCellValue("시즌");
		        row.createCell(8).setCellValue("오더차수");
		        row.createCell(9).setCellValue("생산업체코드");
		        row.createCell(10).setCellValue("일련번호");
		        row.createCell(11).setCellValue("RFID태그");
		         
		        int rowIndex = 1;
		        
		        CompanyInfo companyInfo = companyInfoRepository.findOne(groupList.get(0).getCompanySeq());
		        
		        String createDate = groupList.get(0).getCreateDate();
		        
		        String seqStr = bartagService.bartagDownloadSeq(createDate + "-" + companyInfo.getCode(), SecurityUtil.getCustomUser());
		        
		        String excelName = createDate + "-" + companyInfo.getCode() + "-" + seqStr;
				
				for(ReissueTagGroupModel group : groupList){
					Specifications<RfidTagReissueRequest> specifications = Specifications.where(RfidTagReissueRequestSpecification.createDateEqual(group.getCreateDate()));
					specifications = specifications.and(RfidTagReissueRequestSpecification.companySeqEqual(group.getCompanySeq()));
					
					List<RfidTagReissueRequest> tempReissueList = rfidTagReissueRequestRepository.findAll(specifications);
					
					for(RfidTagReissueRequest tempReissue : tempReissueList){
						
						for(RfidTagReissueRequestDetail vo : tempReissue.getRfidTagReissueRequestDetail()){
							
							int cellIndex = 0;
    				        
					        row = worksheet.createRow(rowIndex++);
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getCreateDate());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getSeq());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getLineSeq());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getStyle());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getColor());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getSize());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getErpKey());
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getProductRfidYySeason());
					        String subOrder = (vo.getOrderDegree()).substring(1, vo.getProductionStorage().getBartagMaster().getOrderDegree().length());
					        row.createCell(cellIndex++).setCellValue(subOrder);
					        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getCompanyInfo().getCode());
					        row.createCell(cellIndex++).setCellValue(vo.getRfidSeq());
				        	row.createCell(cellIndex++).setCellValue(vo.getRfidTag());
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
	 * 재발행 요청 그룹 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reissueExcelDownload")
	public AbstractXlsView reissueExcelDownload(@RequestBody List<RfidTagReissueRequest> rfidTagReissueRequestList)throws Exception{
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("재발행 태그 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);
		        
		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		         
		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {
		             
		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 10000);
		            } 
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("ERPKey");
		        row.createCell(7).setCellValue("시즌");
		        row.createCell(8).setCellValue("오더차수");
		        row.createCell(9).setCellValue("생산업체코드");
		        row.createCell(10).setCellValue("일련번호");
		        row.createCell(11).setCellValue("RFID태그");
		         
		        int rowIndex = 1;
		        
		        String createDate = rfidTagReissueRequestList.get(0).getCreateDate();
		        
		        String seqStr = bartagService.bartagDownloadSeq(createDate + "-" + rfidTagReissueRequestList.get(0).getCompanyInfo().getCode(), SecurityUtil.getCustomUser());
		        
		        String excelName = createDate + "-" + rfidTagReissueRequestList.get(0).getCompanyInfo().getCode() + "-" + seqStr;
				
		        for(RfidTagReissueRequest tempReissue : rfidTagReissueRequestList){
					
					for(RfidTagReissueRequestDetail vo : tempReissue.getRfidTagReissueRequestDetail()){
						
						int cellIndex = 0;
				        
				        row = worksheet.createRow(rowIndex++);
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getCreateDate());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getSeq());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getLineSeq());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getStyle());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getColor());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getSize());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getErpKey());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getBartagMaster().getProductRfidYySeason());
				        String subOrder = (vo.getOrderDegree()).substring(1, vo.getProductionStorage().getBartagMaster().getOrderDegree().length());
				        row.createCell(cellIndex++).setCellValue(subOrder);
				        row.createCell(cellIndex++).setCellValue(vo.getProductionStorage().getCompanyInfo().getCode());
				        row.createCell(cellIndex++).setCellValue(vo.getRfidSeq());
			        	row.createCell(cellIndex++).setCellValue(vo.getRfidTag());
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
}
