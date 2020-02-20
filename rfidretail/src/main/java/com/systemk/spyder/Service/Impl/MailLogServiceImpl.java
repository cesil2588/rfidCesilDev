package com.systemk.spyder.Service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.MailLog;
import com.systemk.spyder.Repository.Main.MailLogRepository;
import com.systemk.spyder.Repository.Main.Specification.MailLogSpecification;
import com.systemk.spyder.Service.BatchLogService;
import com.systemk.spyder.Service.MailLogService;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class MailLogServiceImpl implements MailLogService{
	
	@Autowired
	private MailLogRepository mailLogRepository;
	
	@Autowired
	private BatchLogService batchLogService;

	@Transactional(readOnly = true)
	@Override
	public Page<MailLog> findAll(String type, String stat, String startDate, String endDate, String search, String option, Pageable pageable) throws Exception {
		String startDt = startDate + " 00:00:00";
		String endDt = endDate + " 23:59:59";
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd kk:mm:ss");
		Date start = new Date();
		Date end = new Date();
		if(!startDate.equals("")) {
			start = dt.parse(startDt);
		}
		if(!endDate.equals("")) {
			end = dt.parse(endDt);
		}
		if("".equals(startDate)) {
			String defaltDate = CalendarUtil.getCustomDate() + " 00:00:00";
			stat = "1";
			type = "all";
			start = dt.parse(defaltDate);
			end = new Date();
		}
		
		Page<MailLog> page = null;
		Specifications<MailLog> spec = Specifications.where(MailLogSpecification.sendDateBetween(start, end));
		
		if(!type.equals("all")) {
			spec = spec.and(MailLogSpecification.typeEqual(type));
		}
		if(!stat.equals("")) {
			spec = spec.and(MailLogSpecification.statEqual(stat));
		}
		if(!search.equals("") && option.equals("mailFrom")){
			spec = spec.and(MailLogSpecification.mailFromContain(search));
		} else if(!search.equals("") && option.equals("mailSend")){
			spec = spec.and(MailLogSpecification.mailSendContain(search));
		} 
		
		page = mailLogRepository.findAll(spec, pageable);
		
		return page;
	}

}
