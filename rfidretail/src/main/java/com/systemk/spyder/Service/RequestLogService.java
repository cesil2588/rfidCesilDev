package com.systemk.spyder.Service;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.RequestLog;

public interface RequestLogService {

	public void save(RequestLog requestLog) throws Exception;

	public Page<RequestLog> findAll(String startDate, String endDate, String remoteIp, Long userSeq, String requestUrl, String status, Pageable pageable) throws Exception;

	public void saveRequestLog(Map<String, Object> requestMap, Map<String, Object> responseMap, Date startDate) throws Exception;
}
