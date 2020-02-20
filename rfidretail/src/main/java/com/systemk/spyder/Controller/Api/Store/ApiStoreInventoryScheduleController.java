package com.systemk.spyder.Controller.Api.Store;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.InventoryScheduleDetailBean;
import com.systemk.spyder.Dto.Request.InventoryScheduleHeaderBean;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.InventoryScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store/inventorySchedule")
public class ApiStoreInventoryScheduleController {

	private final InventoryScheduleService inventoryScheduleService;

	@Autowired
	public ApiStoreInventoryScheduleController(InventoryScheduleService inventoryScheduleService){
		this.inventoryScheduleService = inventoryScheduleService;
	}

	/**
	 * 재고실사 내역 목록 조회
	 * @param startDate
	 * @param endDate
	 * @param customerCode
	 * @param confirmYn
	 * @param completeYn
	 * @param disuseYn
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> inventoryScheduleList(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																	 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																	 @RequestParam(value = "customerCode", required = false, defaultValue = "") String customerCode,
																	 @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn,
																	 @RequestParam(value = "completeYn", required = false, defaultValue = "all") String completeYn,
																	 @RequestParam(value = "disuseYn", required = false, defaultValue = "all") String disuseYn,
																	 @RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.findAll(startDate, endDate, customerCode, confirmYn, completeYn, disuseYn, type), HttpStatus.OK);
	}

	/**
	 * 재고실사 확정
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> inventoryScheduleConfirm(@RequestBody InventoryScheduleHeaderBean req) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.confirm(req), HttpStatus.OK);
	}

	/**
	 * 재고실사 초기화
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> inventoryScheduleInit(@RequestBody InventoryScheduleHeaderBean req) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.init(req), HttpStatus.OK);
	}

	/**
	 * 재고실사 다운로드
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/download")
	public ResponseEntity<Map<String, Object>> inventoryScheduleDownload(@RequestBody InventoryScheduleHeaderBean req) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.download(req), HttpStatus.OK);
	}

	/**
	 * 재고조사 상세
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<Map<String, Object>> storeInventoryScheduleDetail(@PathVariable(value = "seq", required = true) Long seq) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.detail(seq), HttpStatus.OK);
	}

	/**
	 * 재고실사 상세 완료
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/detail")
	public ResponseEntity<Map<String, Object>> inventoryScheduleDetailSave(@RequestBody InventoryScheduleDetailBean req) throws Exception{
		return new ResponseEntity<>(inventoryScheduleService.save(req), HttpStatus.OK);
	}
}
