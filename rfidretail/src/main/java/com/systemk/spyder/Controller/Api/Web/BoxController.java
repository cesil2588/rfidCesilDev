package com.systemk.spyder.Controller.Api.Web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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

import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.BoxWorkGroup;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.BoxWorkGroupRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BoxService;
import com.systemk.spyder.Service.BoxWorkGroupService;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/box")
public class BoxController {

	@Autowired
	private BoxInfoRepository boxInfoRepository;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private BoxWorkGroupRepository boxWorkGroupRepository;
	
	@Autowired
	private BoxWorkGroupService boxWorkGroupService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<BoxInfo>> findAll(@PageableDefault(sort = {"boxNum"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable,
												 @RequestParam(value = "seq", required = false, defaultValue = "0") Long seq,
												 @RequestParam(value = "type", required = false, defaultValue = "") String type,
												 @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<BoxInfo>>(boxService.findAll(seq, type, stat, search, option, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> save(@RequestBody BoxInfo boxInfo) throws Exception {
        return new ResponseEntity<Map<String, Object>>(boxService.save(boxInfo), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST, value="/all")
    public ResponseEntity<List<BoxInfo>> saveAll(@RequestBody List<BoxInfo> boxList) throws Exception {
		
		LoginUser user = SecurityUtil.getCustomUser();
		
		if(user != null){
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			
			boxService.saveAll(boxList, regUserInfo);
		}
		
        return new ResponseEntity<List<BoxInfo>>(boxList, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> update(@RequestBody BoxInfo boxInfo) throws Exception {
        return new ResponseEntity<Map<String, Object>>(boxService.update(boxInfo), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<BoxInfo> delete(@RequestBody BoxInfo boxInfo) throws Exception {
		boxInfoRepository.delete(boxInfo);
        return new ResponseEntity<BoxInfo>(boxInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public BoxInfo findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return boxInfoRepository.findOne(seq); 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{flag}/{seq}")
	public List<StyleModel> styleCount(@PathVariable(value = "flag", required = true) String flag, 
									   @PathVariable(value = "seq", required = true) long seq) throws Exception {
		if(flag.equals("production")){
			return boxService.boxStyleCountProductionList(seq);
		} else if(flag.equals("distribution")){
			return boxService.boxStyleCountDistributionList(seq);
		} else {
			return boxService.boxStyleCountStoreList(seq);
		}
	}
	
	/**
	 * 박스 맵핑 태그 리스트 
	 * @param boxSeq
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/boxMappingTagList/{boxSeq}")
	public ResponseEntity<Map<String, Object>> findBoxMappingTagList(@PathVariable(value = "boxSeq", required = true) Long boxSeq,
																	 @RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception {
		return new ResponseEntity<Map<String, Object>>(boxService.findBoxMappingTagList(type, boxSeq), HttpStatus.OK); 
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/popup")
	public ResponseEntity<Page<BoxInfo>> findAllPopup(@PageableDefault(sort = {"boxNum"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
													  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		
		return new ResponseEntity<Page<BoxInfo>>(boxService.findAll(companySeq, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/boxWorkGroup")
	public ResponseEntity<Page<BoxWorkGroup>> findAll(@PageableDefault(sort = {"boxWorkGroupSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "startCompanySeq", required = false, defaultValue = "0") Long startCompanySeq,
												 @RequestParam(value = "endCompanySeq", required = false, defaultValue = "0") Long endCompanySeq,
												 @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
												 @RequestParam(value = "type", required = false, defaultValue = "") String type,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("store")){
			startCompanySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<BoxWorkGroup>>(boxWorkGroupService.findAll(startDate, endDate, startCompanySeq, endCompanySeq, stat, type, pageable, search), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/boxWorkGroup/all")
    public ResponseEntity<Map<String, Object>> boxWorkSaveAll(@RequestBody List<BoxWorkGroup> boxWorkGroupList) throws Exception {
		
		boolean success = boxWorkGroupService.saveAll(boxWorkGroupList);
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("result", success);
		
		if(!success){
			return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.CONFLICT);
		}
		
        return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE, value="/boxWorkGroup")
    public ResponseEntity<List<BoxWorkGroup>> delete(@RequestBody List<BoxWorkGroup> boxWorkGroupList) throws Exception {
		
		boolean mappingCheck = false;
		
		for(BoxWorkGroup boxWorkGroup : boxWorkGroupList){
			for(BoxInfo boxInfo : boxWorkGroup.getBoxInfo()){
				if(!boxInfo.getStat().equals("1")){
					mappingCheck = true;
					break;
				}
			}
		}
		
		if(mappingCheck){
			return new ResponseEntity<List<BoxWorkGroup>>(boxWorkGroupList, HttpStatus.CONFLICT);
		}
		
		boxWorkGroupRepository.delete(boxWorkGroupList);
		
        return new ResponseEntity<List<BoxWorkGroup>>(boxWorkGroupList, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST, value="/boxWorkGroup/updateStat")
    public ResponseEntity<Map<String, Object>> updateStat(@RequestBody List<BoxWorkGroup> boxWorkGroupList) throws Exception {
        return new ResponseEntity<Map<String, Object>>(boxWorkGroupService.updateBoxGroupStat(boxWorkGroupList), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/boxWorkGroup/{seq}")
	public ResponseEntity<List<BoxInfo>> findDetail(@PathVariable(value="seq", required = true) long boxWorkSeq) throws Exception{
		return new ResponseEntity<List<BoxInfo>>(boxInfoRepository.findByBoxWorkGroupSeq(boxWorkSeq), HttpStatus.OK);
	}

	
	/**
	 * 박스 작업 그룹 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/boxWorkGroupExcelDownload")
	public AbstractXlsView boxWorkGroupExcelDownload(@RequestBody List<BoxWorkGroup> groupList)throws Exception{
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("박스 작업 정보");
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
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } 
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("내용");
		        row.createCell(1).setCellValue("날짜");
		        row.createCell(2).setCellValue("박스번호");
		        row.createCell(3).setCellValue("출발지");
		        row.createCell(4).setCellValue("도착지");
		        row.createCell(5).setCellValue("박스바코드");
		         
		        int rowIndex = 1;
		        
		        String createDate = groupList.get(0).getCreateDate();
		        
		        String excelName = createDate + "-" + groupList.get(0).getStartCompanyInfo().getCode();
				
				for(BoxWorkGroup group : groupList){
					
					for(BoxInfo boxInfo : group.getBoxInfo()){
						
						int cellIndex = 0;
				        
				        row = worksheet.createRow(rowIndex++);
				        row.createCell(cellIndex++).setCellValue(boxInfo.getType());
				        row.createCell(cellIndex++).setCellValue(boxInfo.getCreateDate());
				        row.createCell(cellIndex++).setCellValue(boxInfo.getBoxNum());
				        row.createCell(cellIndex++).setCellValue(boxInfo.getStartCompanyInfo().getName());
				        row.createCell(cellIndex++).setCellValue(boxInfo.getEndCompanyInfo().getName());
				        row.createCell(cellIndex++).setCellValue(boxInfo.getBarcode());
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
