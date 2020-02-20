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
import com.systemk.spyder.Entity.Main.MailLog;
import com.systemk.spyder.Repository.Main.MailLogRepository;
import com.systemk.spyder.Service.MailLogService;

@RestController
@RequestMapping("/mailLog")
public class MailLogController {
	
	@Autowired 
	private MailLogRepository mailLogRepository;
	
	@Autowired
	private MailLogService mailLogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<MailLog>> findAll(@PageableDefault(sort = {"mailSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
								  @RequestParam(value = "search", required = false, defaultValue = "") String search,
								  @RequestParam(value = "option", required = false, defaultValue = "") String option,
								  @RequestParam(value = "type", required = false, defaultValue = "") String type,
								  @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
								  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate) throws Exception {
		return new ResponseEntity<Page<MailLog>>(mailLogService.findAll(type, stat, startDate, endDate, search, option, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public MailLog findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return mailLogRepository.findOne(seq); 
	}
}
