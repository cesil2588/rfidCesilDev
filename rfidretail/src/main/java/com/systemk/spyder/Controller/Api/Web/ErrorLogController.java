package com.systemk.spyder.Controller.Api.Web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.ErrorLog;
import com.systemk.spyder.Repository.Main.ErrorLogRepository;
import com.systemk.spyder.Service.ErrorLogService;

@RestController
@RequestMapping("/errorLog")
public class ErrorLogController {
	
	@Autowired
	private ErrorLogRepository errorLogRepository;
	
	@Autowired
	private ErrorLogService errorLogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ErrorLog>> findAll(@PageableDefault(sort = {"errorLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
								  @RequestParam(value = "startDate", required = false, defaultValue = "") String startTime,
								  @RequestParam(value = "endDate", required = false, defaultValue = "") String endTime,
								  @RequestParam(value = "errorCode", required = false, defaultValue = "") String errorCode,
								  @RequestParam(value = "requestUrl", required = false, defaultValue = "") String requestUrl) throws Exception {
		return new ResponseEntity<Page<ErrorLog>>(errorLogService.findAll(startTime, endTime, errorCode, requestUrl, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/selectAllUrl")
	public ResponseEntity<List<Map<String, Object>>> selectAllUrl() throws Exception {
		return new ResponseEntity<List<Map<String, Object>>>(errorLogService.selectAllUrl(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ErrorLog findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return errorLogRepository.findOne(seq); 
	}

}
