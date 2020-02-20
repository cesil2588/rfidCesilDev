package com.systemk.spyder.Controller.Api.Store;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.StoreMoveBean;
import com.systemk.spyder.Dto.Request.StoreMoveListBean;
import com.systemk.spyder.Entity.Main.StoreMoveLog;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.CompanyService;
import com.systemk.spyder.Service.StoreMoveLogService;
import com.systemk.spyder.Service.CustomBean.Select.SelectCompanyModel;

@RestController
@RequestMapping("/api/storeRfidTag")
public class ApiStoreMoveController {

	@Autowired
	private StoreMoveLogService storeMoveLogService;

	@Autowired
	private CompanyService companyService;

	/**
	 * 매장간 이동 내역 목록 조회
	 * @param pageable
	 * @param startDate
	 * @param endDate
	 * @param companySeq
	 * @param completeYn
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET, value = "/storeMove")
	public ResponseEntity<Page<StoreMoveLog>> storeMoveList(@PageableDefault(sort = {"storeMoveLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
														  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			  											  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
														  @RequestParam(value = "startCompanySeq", required = false, defaultValue = "0") Long startCompanySeq,
														  @RequestParam(value = "endCompanySeq", required = false, defaultValue = "0") Long endCompanySeq,
														  @RequestParam(value = "workYn", required = false, defaultValue = "all") String workYn,
														  @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn,
														  @RequestParam(value = "disuseYn", required = false, defaultValue = "all") String disuseYn,
														  @RequestParam(value = "companyType", required = false, defaultValue = "") String companyType) throws Exception{
		return new ResponseEntity<Page<StoreMoveLog>>(storeMoveLogService.findAll(startDate, endDate, startCompanySeq, endCompanySeq, workYn, confirmYn, disuseYn, companyType, pageable), HttpStatus.OK);
	}

	/**
	 * 매장간 이동 내역 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/storeMove/{seq}")
	public ResponseEntity<Map<String, Object>> storeMoveDetail(@PathVariable(value = "seq", required = true) Long seq) throws Exception{
		return new ResponseEntity<Map<String, Object>>(storeMoveLogService.detail(seq), HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 완료(PDA 작업 등록)
	 * @param storeMoveBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeMove")
	public ResponseEntity<Map<String, Object>> storeMoveSave(@RequestBody StoreMoveBean storeMoveBean) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveSave(storeMoveBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 완료(ERP 작업 등록)
	 * @param barcode
	 * @param userSeq
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeMoveErp")
	public ResponseEntity<Map<String, Object>> storeMoveErp(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
															  @RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
															  @RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveErp(barcode, userSeq, type);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 수정(PDA 작업 수정)
	 * @param storeMoveBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/storeMove")
	public ResponseEntity<Map<String, Object>> storeMoveUpdate(@RequestBody StoreMoveBean storeMoveBean) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveUpdate(storeMoveBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 삭제(PDA 작업 삭제)
	 * @param storeMoveBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/storeMove")
	public ResponseEntity<Map<String, Object>> storeMoveDelete(@RequestBody StoreMoveListBean storeMoveListBean) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveDelete(storeMoveListBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 확정(ERP 전달)
	 * @param storeMoveListBean
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeMoveComplete")
	public ResponseEntity<Map<String, Object>> storeMoveComplete(@RequestBody StoreMoveListBean storeMoveListBean) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveComplete(storeMoveListBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 종결
	 * @param storeMoveListBean
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeMoveDisuse")
	public ResponseEntity<Map<String, Object>> storeMoveDisuse(@RequestBody StoreMoveListBean storeMoveListBean) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveDisuse(storeMoveListBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 바코드 확인
	 * @param barcode
	 * @param userSeq
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/storeMoveBarcode/{barcode}")
	public ResponseEntity<Map<String, Object>> storeMoveBarcode(@PathVariable(value = "barcode", required = true) String barcode,
																@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																@RequestParam(value = "companyType", required = false, defaultValue = "") String companyType,
															  	@RequestParam(value = "erpYn", required = false, defaultValue = "") String erpYn) throws Exception{

		Map<String, Object> obj = storeMoveLogService.storeMoveBarcode(barcode, companySeq, companyType, erpYn);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 업체 선택
	 * @param barcode
	 * @param userSeq
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeMoveCompany/{flag}")
	public ResponseEntity<Map<String, Object>> storeMoveCompany(@PathVariable(value = "flag", required = true) String flag,
																@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();

		List<SelectCompanyModel> selectList = new ArrayList<SelectCompanyModel>();

		switch(flag){
			case "header" :
				selectList = companyService.selectApiCompanyHeader("store");
				break;
			case "detail" :
				selectList = companyService.selectApiCompanyDetail(type);
				break;
		}

		obj.put("headerList", selectList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 매장간 이동 작업 완료(PDA 작업 등록)
	 * @param storeMoveBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeMoveTest")
	public void storeMoveTest(@RequestBody StoreMoveBean storeMoveBean) throws Exception{
		throw new NullPointerException("test exception");
	}
}
