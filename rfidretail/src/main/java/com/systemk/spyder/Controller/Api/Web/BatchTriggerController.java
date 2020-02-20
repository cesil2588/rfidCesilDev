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

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.BatchTriggerService;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/batchTrigger")
public class BatchTriggerController {
	
	@Autowired
	private BatchTriggerService batchTriggerService;
	
	@Autowired
	private BatchTriggerRepository batchTriggerRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<BatchTrigger>> findAll(@PageableDefault(sort = {"batchTriggerSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
								  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								  @RequestParam(value = "status", required = false, defaultValue = "") String status,
								  @RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								  @RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(!SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("admin")){
			userSeq = SecurityUtil.getCustomUser().getUserSeq();
		}
		
		return new ResponseEntity<Page<BatchTrigger>>(batchTriggerService.findAll(startDate, endDate, userSeq, status, type, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<BatchTrigger> findOne(@PathVariable(value = "seq", required = true) Long seq) throws Exception {
		return new ResponseEntity<BatchTrigger>(batchTriggerRepository.findOne(seq), HttpStatus.OK); 
	}
}
