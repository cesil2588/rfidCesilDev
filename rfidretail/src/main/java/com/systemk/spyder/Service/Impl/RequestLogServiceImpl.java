package com.systemk.spyder.Service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.RequestLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.RequestLogRepository;
import com.systemk.spyder.Repository.Main.Specification.RequestLogSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.RequestLogService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class RequestLogServiceImpl implements RequestLogService{

	@Autowired
	private RequestLogRepository requestLogRepository;

	@Transactional
	@Override
	public void save(RequestLog requestLog) throws Exception {

		boolean check = false;

		if(requestLog.getRequestUrl().equals("/") ||
		   requestLog.getRequestUrl().contains(".jsp") ||
		   requestLog.getRequestUrl().contains(".ico") ||
		   requestLog.getRequestUrl().contains("/userAuth") ||
		   requestLog.getRequestUrl().contains("/hello/") ||
		   requestLog.getRequestUrl().contains("/resources/") ||
		   requestLog.getRequestUrl().contains("/errorLog") ||
		   requestLog.getRequestUrl().contains("/requestLog") ||
		   requestLog.getRequestUrl().contains("/batchLog") ||
		   requestLog.getRequestUrl().contains("/mailLog")){

			check = true;
		}

		if(!check){

			if(requestLog.getSession() == null){
				requestLog.setSession("");
			}
			requestLogRepository.save(requestLog);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<RequestLog> findAll(String startDate, String endDate, String remoteIp, Long userSeq, String requestUrl, String status, Pageable pageable) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Page<RequestLog> page = null;
		Specifications<RequestLog> spec = Specifications.where(RequestLogSpecification.createTimeBetween(startDate, endDate));

		if(!remoteIp.equals("")){
			spec = spec.and(RequestLogSpecification.remoteIpContain(remoteIp));
		}

		if(userSeq != 0){
			spec = spec.and(RequestLogSpecification.regUserEqual(userSeq));
		}

		if(!requestUrl.equals("")){
			spec = spec.and(RequestLogSpecification.requestUrlContain(requestUrl));
		}

		if(!status.equals("")) {
			spec = spec.and(RequestLogSpecification.statusEqual(status));
		}

		page = requestLogRepository.findAll(spec, pageable);

		return page;
	}

	@Transactional
	@Override
	public void saveRequestLog(Map<String, Object> requestMap, Map<String, Object> responseMap, Date startDate) {

    	RequestLog requestLog = new RequestLog();

    	requestLog.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        requestLog.setRequestUrl(requestMap.get("requestUrl").toString());
        requestLog.setRequestMethod(requestMap.get("method").toString());
        requestLog.setRemoteIp(requestMap.get("remoteAddr").toString());
        requestLog.setHeader(requestMap.get("header").toString());

        requestLog.setSession(requestMap.get("session") != null ? requestMap.get("session").toString() : "");
        requestLog.setUserAgent(requestMap.get("userAgent") != null ? requestMap.get("userAgent").toString() : "");

        LoginUser loginUser = SecurityUtil.getCustomUser();

        if(loginUser != null){
        	UserInfo userInfo = new UserInfo();
        	userInfo.setUserSeq(loginUser.getUserSeq());

        	requestLog.setRegUserInfo(userInfo);
        }

        if(requestMap.get("method").toString().equals("GET")){
        	requestLog.setRequestQuery(requestMap.get("queryString") != null ? requestMap.get("queryString").toString() : "");
        } else {
        	requestLog.setRequestQuery(requestMap.get("body") != null ? requestMap.get("body").toString() : "");
        }

        requestLog.setResponseBody(responseMap.get("body") != null ? responseMap.get("body").toString() : "");
        requestLog.setStatus(responseMap.get("status") != null ? responseMap.get("status").toString() : "");

        requestLog.setRegDate(startDate);
        requestLog.setUpdDate(new Date());

        try {
			save(requestLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
    }
}
