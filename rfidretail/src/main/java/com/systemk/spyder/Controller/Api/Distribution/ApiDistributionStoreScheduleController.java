package com.systemk.spyder.Controller.Api.Distribution;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.Request.BoxTagListBean;
import com.systemk.spyder.Dto.Response.ApiStorecheduleCompleteResult;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.CustomBean.StoreScheduleModel;
import com.systemk.spyder.Util.CalendarUtil;

@RestController
@RequestMapping("/api/distributionRfidTag")
public class ApiDistributionStoreScheduleController {

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;

	/**
	 * 물류 입고 예정 정보 전달 (컨베이어, PDA)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeScheduleList")
	public ResponseEntity<StoreScheduleModel> storeScheduleList(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode) throws Exception{

		StoreScheduleModel storeScheduleModel = storageScheduleLogService.boxStoreScheduleList(barcode);

		return new ResponseEntity<StoreScheduleModel>(storeScheduleModel, HttpStatus.OK);
	}

	/**
	 * 물류 입고 진척율 전달 (컨베이어)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeScheduleBatch")
	public ResponseEntity<Map<String, Object>> storeScheduleBatch() throws Exception{

		String nowDate = CalendarUtil.convertFormat("yyyyMMdd");

		return new ResponseEntity<Map<String, Object>>(storageScheduleLogService.storeScheduleBatch(nowDate, nowDate, Long.valueOf(0)), HttpStatus.OK);
	}


	/**
	 * 물류 입고 완료 정보 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeScheduleComplete")
	public ResponseEntity<ApiStorecheduleCompleteResult> storeScheduleCompleteGet(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
																				  @RequestParam(value = "userSeq", required = false, defaultValue = "1") Long userSeq,
																				  @RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{

		ApiStorecheduleCompleteResult result = storageScheduleLogService.storeScheduleComplete(barcode, userSeq, type);

		return new ResponseEntity<ApiStorecheduleCompleteResult>(result, HttpStatus.OK);
	}

	/**
	 * 반품 입고 예정 번호 검색
	 * @param referenceBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/storeReturnSchedule")
	public ResponseEntity<Map<String, Object>> releaseSchedulePost(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode) throws Exception{
		return new ResponseEntity<Map<String, Object>>(erpStoreScheduleService.findByStoreReturnSchedule(barcode), HttpStatus.OK);
	}

	/**
	 * 물류 반품 입고 완료 정보 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception

	@RequestMapping(method = RequestMethod.GET, value = "/storeReturnScheduleComplete")
	public ResponseEntity<ApiStorecheduleCompleteResult> storeReturnScheduleComplete(@RequestBody List<Map<String,object>> T) throws Exception{

		ApiStorecheduleCompleteResult result = storageScheduleLogService.storeScheduleComplete(barcode, userSeq, type);

		return new ResponseEntity<ApiStorecheduleCompleteResult>(result, HttpStatus.OK);
	}*/

	/**
	 * 반품 입고 태그 정보 업데이트
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/updateReturnRfidTag")
	public ResponseEntity<Map<String, Object>> updateReturnRfidTagList(@RequestBody BoxTagListBean tagBean) throws Exception{

		Map<String, Object> obj = erpStoreScheduleService.updateReturnRfidTag(tagBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
}
