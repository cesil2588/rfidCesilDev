package com.systemk.spyder.Service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.systemk.spyder.Entity.Main.ErrorLog;
import com.systemk.spyder.Repository.Main.ErrorLogRepository;
import com.systemk.spyder.Repository.Main.Specification.ErrorLogSpecification;
import com.systemk.spyder.Service.ErrorLogService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.IpUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class ErrorLogServiceImpl implements ErrorLogService{

	@Autowired
	private ErrorLogRepository errorLogRepository;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@Transactional
	@Override
	public void save(HttpServletRequest request, Exception ex, String resultCode, Device device) throws Exception {

		ErrorLog errorLog = new ErrorLog();

		errorLog.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		errorLog.setRegDate(new Date());
		errorLog.setErrorCode(resultCode);
		errorLog.setErrorMessage(StringUtil.getPrintStackTrace(ex));
		if(device != null) {
			errorLog.setDeviceType(!device.isMobile() ? "1" : "2");
		} else {
			errorLog.setDeviceType("0");
		}
		errorLog.setRemoteIp(IpUtil.getUserIP(request));
		errorLog.setRequestUrl(request.getRequestURI());
		errorLog.setRequestMethod(request.getMethod());

		if(request.getMethod().equals("GET")){
			errorLog.setRequestQuery(request.getQueryString());
		} else {
			errorLog.setRequestQuery(StringUtil.getBody(request));
		}

		boolean check = false;

		if(errorLog.getRequestUrl().equals("/") ||
		   errorLog.getRequestUrl().contains(".jsp") ||
		   errorLog.getRequestUrl().contains(".ico") ||
		   errorLog.getRequestUrl().contains("/userAuth") ||
		   errorLog.getRequestUrl().contains("/hello/") ||
		   errorLog.getRequestUrl().contains("/resources/") ||
		   errorLog.getRequestUrl().contains(".php")){
			check = true;
		}

		if(!check){
			errorLogRepository.save(errorLog);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ErrorLog> findAll(String startDate, String endDate, String errorCode, String requestUrl, Pageable pageable) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Page<ErrorLog> page = null;
		Specifications<ErrorLog> spec = Specifications.where(ErrorLogSpecification.createTimeBetween(startDate, endDate));

		if(!errorCode.equals("")){
			spec = spec.and(ErrorLogSpecification.errorCodeContain(errorCode));
		}

		if(!requestUrl.equals("")){
			spec = spec.and(ErrorLogSpecification.requestUrlContain(requestUrl));
		}

		page = errorLogRepository.findAll(spec, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> selectAllUrl() throws Exception {

		Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> keys = mappings.keySet();
		Iterator<RequestMappingInfo> iterator = keys.iterator();

		List<Map<String, Object>> tempUrlList = new ArrayList<Map<String, Object>>();
		while (iterator.hasNext()) {

			Map<String, Object> obj = new LinkedHashMap<String, Object>();

			RequestMappingInfo key = iterator.next();
			String tempStr = key.getPatternsCondition().toString().replaceAll("[\\[\\]]", "");

			if(tempStr.equals("/")) {
				continue;
			}

			obj.put("value", tempStr.replaceAll("[\\[\\]]", ""));

			tempUrlList.add(obj);
		}

		HashSet<Map<String, Object>> tempHashSet = new HashSet<Map<String, Object>>(tempUrlList);
		List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>(tempHashSet);

		return urlList;
	}
}
