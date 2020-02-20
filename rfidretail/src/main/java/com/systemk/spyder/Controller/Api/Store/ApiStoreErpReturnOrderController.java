package com.systemk.spyder.Controller.Api.Store;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.*;
import com.systemk.spyder.Dto.Request.*;
import com.systemk.spyder.Entity.Main.View.*;
import com.systemk.spyder.Service.*;

@RestController
@RequestMapping("/api/store")
public class ApiStoreErpReturnOrderController {

	@Autowired
	private StoreReturnService storeReturnService;

	/**
	 * 지시 반품 내역 목록 조회
	 * @param startDate
	 * @param endDate
	 * @param returnType
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET, value = "/erpReturnInfo")
	public ResponseEntity<Map<String, Object>> erpReturnInfo(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			  											  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
														  @RequestParam(value = "returnType", required = false, defaultValue = "all") String returnType,
														  @RequestParam(value = "customerCode", required = false, defaultValue = "") String customerCode) throws Exception{
		Map<String, Object> map = storeReturnService.findErpReturnInfoAll(startDate, endDate, returnType, customerCode);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 지시 반품 상세 목록 조회
	 * @param returnInfoSeq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/erpReturnDetailInfo")
	public ResponseEntity<Map<String, Object>> erpReturnDetailInfo(@RequestParam(value = "returnInfoSeq", required = true) Long returnInfoSeq) throws Exception {
		Map<String, Object> map = storeReturnService.findErpReturnDetailInfo(returnInfoSeq);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 지시반품 작업 저장...erp_store_return_tag 테이블에 해당 정보 insert
	 * @param erpReturnWorkBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/erpReturnWorkSave")
	public ResponseEntity<Map<String, Object>> erpReturnWorkSave(@RequestBody List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception{

		Map<String, Object> map = storeReturnService.erpReturnWorkSave(erpReturnWorkBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 지시반품 작업 삭제...erp_store_return_tag 테이블에 해당 정보 delete
	 * @param workBoxNums(박스번호의 리스트)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteErpReturnWork")
	public ResponseEntity<Map<String, Object>> deleteErpReturnWork(@RequestBody List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception{

		Map<String, Object> map = storeReturnService.DeleteErpReturnWork(erpReturnWorkBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 지시반품 작업 확정...erp_store_return_tag 테이블의 complete_yn 업데이트
	 * @param workBoxNum(박스번호 하나만)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/completeReturnWork")
	public ResponseEntity<Map<String, Object>> completeReturnWork(@RequestBody ErpReturnWorkBean erpReturnWorkBean) throws Exception{

		Map<String, Object> map = storeReturnService.completeReturnWork(erpReturnWorkBean);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 지시반품 작업시 박스번호(max+1) 리턴
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getMaxBoxNo")
	public ResponseEntity<Map<String, Object>> getMaxBoxNo() throws Exception{
		Map<String, Object> map = storeReturnService.getMaxBoxNo();
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 지시 반품 작업 박스 상세 조회
	 * @param boxNum
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/getBoxDetailInfo")
	public ResponseEntity<Map<String, Object>> getBoxDetailInfo(@RequestParam(value = "boxNum", required = true) Long boxNum) throws Exception {
		Map<String, Object> map = storeReturnService.getBoxDetailInfo(boxNum);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
