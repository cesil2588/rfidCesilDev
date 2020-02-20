package com.systemk.spyder.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.systemk.spyder.Entity.Main.MailLog;

public interface MailLogService {

	public Page<MailLog> findAll(String type, String stat, String startDate, String endDate, String search, String option, Pageable pageable) throws Exception;
}
