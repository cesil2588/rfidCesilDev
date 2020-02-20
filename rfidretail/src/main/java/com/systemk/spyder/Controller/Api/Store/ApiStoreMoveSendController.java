package com.systemk.spyder.Controller.Api.Store;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.ErpReturnWorkBean;
import com.systemk.spyder.Dto.Request.ErpWorkBean;
import com.systemk.spyder.Dto.Request.StoreMoveSendBean;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.ErpStoreMoveService;

@RestController
@RequestMapping("/api/store/move")
public class ApiStoreMoveSendController {
	
	@Autowired
	private ErpStoreMoveService erpStoreMoveService;

	/**
	 * 매장 이동 목록 조회(이동지시, 매장등록, 이동받기 동일 API호출, 첫 진입 파라미터만 다름)
	 * move_Kind - 1: 매장간 보내기, 2: 매장간 받기
	 * @Param regDate
	 * @Param completeYn
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findAll")
	public ResponseEntity<Map<String, Object>> erpReturnInfo(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			  											  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
														  @RequestParam(value = "completeYn", required = false, defaultValue = "all") String completeYn,
														  @RequestParam(value = "customerCode", required = true) String customerCode,
														  @RequestParam(value = "moveType", required = false) String moveType,
														  @RequestParam(value = "moveKind", required = false, defaultValue = "1") String moveKind) throws Exception{
		Map<String, Object> map = erpStoreMoveService.findAll(startDate, endDate, completeYn, customerCode, moveType, moveKind);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 상세 조회(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @param moveOrderSeq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findDetail")
	public ResponseEntity<Map<String, Object>> findDetail(@RequestParam(value = "moveSeq", required = true) Long moveSeq) throws Exception {
		Map<String, Object> map = erpStoreMoveService.findDetail(moveSeq);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 작업시 박스번호(max+1) 리턴(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getMaxBoxNo")
	public ResponseEntity<Map<String, Object>> getMaxBoxNo() throws Exception{
		Map<String, Object> map = erpStoreMoveService.getMaxBoxNo();
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 작업 박스 상세 조회(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @param boxNum
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/getBoxDetail")
	public ResponseEntity<Map<String, Object>> getBoxDetailInfo(@RequestParam(value = "boxNum", required = true) Long boxNum) throws Exception {
		Map<String, Object> map = erpStoreMoveService.getBoxDetailInfo(boxNum);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 작업 저장...erp_store_move_tag 테이블에 해당 정보 insert(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @param erpReturnWorkBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveWork")
	public ResponseEntity<Map<String, Object>> erpReturnWorkSave(@RequestBody List<ErpWorkBean> erpWorkBean) throws Exception{

		Map<String, Object> map = erpStoreMoveService.saveWork(erpWorkBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 작업 삭제...erp_store_move_tag 테이블에 해당 정보 delete(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @param workBoxNum(박스번호의 리스트)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteWork")
	public ResponseEntity<Map<String, Object>> deleteWork(@RequestBody List<ErpWorkBean> erpWorkBean) throws Exception{

		Map<String, Object> map = erpStoreMoveService.DeleteWork(erpWorkBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장 이동 작업 확정...erp_store_move_tag 테이블의 complete_yn 업데이트(이동지시, 매장등록 동일 API호출, 첫 진입 파라미터만 다름)
	 * @param workBoxNum(박스번호 하나만)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/completeWork")
	public ResponseEntity<Map<String, Object>> completeWork(@RequestBody ErpWorkBean erpWorkBean) throws Exception{

		Map<String, Object> map = erpStoreMoveService.completeWork(erpWorkBean);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 매장간 이동 - 매장등록 - 신규 매장등록
	 * @Param StoreMoveSendBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveOrder")
	public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody StoreMoveSendBean moveBean) throws Exception{
		
		Map<String, Object> map = erpStoreMoveService.saveOrder(moveBean);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	/**
	 * 매장간 이동 - 매장등록 - 매장등록 삭제
	 * @Param List<ErpWorkBean>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteOrder")
	public ResponseEntity<Map<String, Object>> deleteOrder(@RequestBody List<ErpWorkBean> erpWorkBean) throws Exception{
		
		Map<String, Object> map = erpStoreMoveService.deleteOrder(erpWorkBean);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	/**
	 * 매장간 이동 - 매장등록 - 매장등록 확정
	 * @Param moveSeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/completeOrder")
	public ResponseEntity<Map<String, Object>> completeOrder(@RequestBody ErpWorkBean erpWorkBean) throws Exception{
		
		Map<String, Object> map = erpStoreMoveService.completeOrder(erpWorkBean);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
}
