package com.systemk.spyder.Controller.Api.Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Service.SearchService;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	/**
	 * 태그 & 박스 바코드 검색
	 * @param searchData
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/searchAll/{searchData}")
	public ResponseEntity<Map<String,Object>> searchAll(@PathVariable(value = "searchData", required = true) String searchData,
														@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
														@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
														@RequestParam(value = "style", required = false, defaultValue = "") String style,
														@RequestParam(value = "color", required = false, defaultValue = "") String color,
														@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Map<String,Object>>(searchService.searchAll(searchData, searchType, companySeq, style, color, styleSize), HttpStatus.OK);
	}
	
	/**
	 * RFID 태그 상세 검색
	 * @param rfidTagMaster
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/searchRfidTag")
	public ResponseEntity<Map<String,Object>> searchRfidTag(@RequestBody RfidTagMaster rfidTagMaster) throws Exception {
		return new ResponseEntity<Map<String,Object>>(searchService.searchRfidTag(rfidTagMaster), HttpStatus.OK);
	}
	
	/**
	 * 스타일, 컬러, 사이즈, 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectReleaseBartag(@PathVariable(value = "flag", required = true) String flag,
																	   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																	   @RequestParam(value = "style", required = false, defaultValue = "") String style,
																	   @RequestParam(value = "color", required = false, defaultValue = "") String color,
																	   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();
		
		switch(flag){
			case "style" :
				selectList = searchService.selectBartagStyle(companySeq);
				break;
			case "color" :
				selectList = searchService.selectBartagColor(companySeq, style);
				break;
			case "size" :
				selectList = searchService.selectBartagSize(companySeq, style, color);
				break;
		}
		
		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}
}
