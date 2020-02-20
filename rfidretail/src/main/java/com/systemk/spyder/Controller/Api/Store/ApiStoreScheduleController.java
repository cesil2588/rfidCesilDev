package com.systemk.spyder.Controller.Api.Store;

import com.systemk.spyder.Dto.Request.StoreScheduleCompleteBean;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store/schedule")
public class ApiStoreScheduleController {

	private final ReleaseScheduleLogService releaseScheduleLogService;

	@Autowired
	public ApiStoreScheduleController(ReleaseScheduleLogService releaseScheduleLogService) {
		this.releaseScheduleLogService = releaseScheduleLogService;
	}

	/**
	 * 매장 입고 예정 정보 전달 (PDA)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> schedule(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode) throws Exception{
		return new ResponseEntity<Map<String, Object>>(releaseScheduleLogService.findStoreSchedule(barcode), HttpStatus.OK);
	}

	/**
	 * 매장 입고 완료 정보 처리
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> scheduleComplete(@RequestBody StoreScheduleCompleteBean req) throws Exception{
		return new ResponseEntity<Map<String, Object>>(releaseScheduleLogService.storeScheduleComplete(req), HttpStatus.OK);
	}
}
