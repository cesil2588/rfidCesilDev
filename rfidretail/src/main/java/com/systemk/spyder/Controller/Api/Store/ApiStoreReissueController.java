package com.systemk.spyder.Controller.Api.Store;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.StoreReissueBean;
import com.systemk.spyder.Dto.Request.StoreReissueListBean;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.RfidTagReissueRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store/reissue")
public class ApiStoreReissueController {

	private final RfidTagReissueRequestService rfidTagReissueRequestService;

	@Autowired
	public ApiStoreReissueController(RfidTagReissueRequestService rfidTagReissueRequestService) {
		this.rfidTagReissueRequestService = rfidTagReissueRequestService;
	}

	/**
	 * 태그 재발행 내역 목록 조회
	 * @param startDate
	 * @param endDate
	 * @param customerCode
	 * @param confirmYn
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> reissueList(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			  											  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
														  @RequestParam(value = "customerCode", required = false, defaultValue = "") String customerCode,
														  @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.findAll(startDate, endDate, customerCode, confirmYn), HttpStatus.OK);
	}

	/**
	 * 태그 재발행 내역 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<Map<String, Object>> reissueDetail(@PathVariable(value = "seq", required = true) Long seq) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.storeDetail(seq), HttpStatus.OK);
	}

	/**
	 * 태그 재발행 작업 완료
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> reissueSave(@RequestBody StoreReissueBean req) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.storeSave(req), HttpStatus.OK);
	}

	/**
	 * 태그 재발행 삭제
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> reissueDelete(@RequestBody StoreReissueListBean req) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.storeDelete(req), HttpStatus.OK);
	}

	/**
	 * 태그 재발행 확정
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> reissueComplete(@RequestBody StoreReissueListBean req) throws Exception{
		return new ResponseEntity<Map<String, Object>>(rfidTagReissueRequestService.storeComplete(req), HttpStatus.OK);
	}

}
