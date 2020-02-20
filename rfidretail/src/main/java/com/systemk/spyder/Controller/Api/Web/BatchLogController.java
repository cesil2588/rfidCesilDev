package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

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

import com.systemk.spyder.Entity.Main.BatchJob;
import com.systemk.spyder.Entity.Main.BatchLog;
import com.systemk.spyder.Repository.Main.BatchLogRepository;
import com.systemk.spyder.Service.BatchLogService;

@RestController
@RequestMapping("/batchLog")
public class BatchLogController {
	
	@Autowired
	private BatchLogRepository batchLogRepository;
	
	@Autowired
	private BatchLogService batchLogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<BatchLog>> findAll(@PageableDefault(sort = {"batchLogSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
								  @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
								  @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
								  @RequestParam(value = "command", required = false, defaultValue = "") String command,
								  @RequestParam(value = "status", required = false, defaultValue = "") String status,
								  @RequestParam(value = "search", required = false, defaultValue = "") String search,
								  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return new ResponseEntity<Page<BatchLog>>(batchLogService.findAll(startTime, endTime, command, status, search, option, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/selectAllJobName")
	public ResponseEntity<List<BatchJob>> selectAllJobName() throws Exception {
		return new ResponseEntity<List<BatchJob>>(batchLogService.selectAllJobName(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public BatchLog findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return batchLogRepository.findOne(seq); 
	}
}
