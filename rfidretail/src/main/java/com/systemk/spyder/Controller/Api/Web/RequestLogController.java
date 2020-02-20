package com.systemk.spyder.Controller.Api.Web;

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

import com.systemk.spyder.Entity.Main.RequestLog;
import com.systemk.spyder.Repository.Main.RequestLogRepository;
import com.systemk.spyder.Service.RequestLogService;

@RestController
@RequestMapping("/requestLog")
public class RequestLogController {

	@Autowired
	private RequestLogRepository requestLogRepository;

	@Autowired
	private RequestLogService requestLogService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RequestLog>> findAll(@PageableDefault(sort = {"requestLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
								  @RequestParam(value = "startDate", required = false, defaultValue = "") String startTime,
								  @RequestParam(value = "endDate", required = false, defaultValue = "") String endTime,
								  @RequestParam(value = "remoteIp", required = false, defaultValue = "") String remoteIp,
								  @RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								  @RequestParam(value = "requestUrl", required = false, defaultValue = "") String requestUrl,
								  @RequestParam(value = "status", required = false, defaultValue = "") String status) throws Exception {
		return new ResponseEntity<Page<RequestLog>>(requestLogService.findAll(startTime, endTime, remoteIp, userSeq, requestUrl, status, pageable), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public RequestLog findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return requestLogRepository.findOne(seq);
	}

}
