package com.systemk.spyder.Controller.Api.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.systemk.spyder.Service.RfidTagHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Service.ReissueTagService;
import com.systemk.spyder.Service.StoreStorageService;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

@RestController
@RequestMapping("/api/store/history")
public class ApiStoreHistoryController {

	private final RfidTagHistoryService rfidTagHistoryService;

	@Autowired
	public ApiStoreHistoryController(RfidTagHistoryService rfidTagHistoryService) {
		this.rfidTagHistoryService = rfidTagHistoryService;
	}

	/**
	 * RFID 태그 이력 조회
	 * @param value
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{value}")
	public ResponseEntity<Map<String, Object>> findHistory(@PathVariable(value = "value", required = true) String value,
														   @RequestParam(value = "flag", required = false, defaultValue = "") String flag) throws Exception {
		return new ResponseEntity<Map<String, Object>>(rfidTagHistoryService.findByHistory(value, flag), HttpStatus.OK);
	}
}
