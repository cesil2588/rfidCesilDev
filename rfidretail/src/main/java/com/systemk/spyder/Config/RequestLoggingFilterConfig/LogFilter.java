package com.systemk.spyder.Config.RequestLoggingFilterConfig;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import com.systemk.spyder.Service.RequestLogService;
import com.systemk.spyder.Util.LoggingUtil;

/**
 * RequestLog에 API 요청 및 응답 데이터를 저장 및 필터링 하기 위한 파일
 * RequestLog에 어마어마한 데이터가 쌓이기에 안정화되면 응답코드 200을 제외한 나머지만 넣는 작업이 필요
 * @author youdozi
 *
 */
@Configuration
@ComponentScan({"com.systemk.retail"})
public class LogFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(LogFilter.class);

    @Autowired
    private RequestLogService requestLogService;

    @Value("${requestLog.enabled}")
	boolean requestLogEnabled;

    @Override
    public void destroy() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

    	Date startDate = new Date();
        final HttpServletRequest request = new RequestWrapper(req);
        final HttpServletResponse response = new ResponseWrapper(res);

        Map<String, Object> requestMap = LoggingUtil.makeLoggingRequestMap(request);

        filterChain.doFilter(request, response);

        Map<String, Object> responseMap = LoggingUtil.makeLoggingResponseMap(response);

        // 로그 저장
        try {

        	boolean check = false;

        	if(requestMap.get("requestUrl") != null && (requestMap.get("requestUrl").toString().contains(".jsp") ||
        												requestMap.get("requestUrl").toString().contains(".ico") ||
        												requestMap.get("requestUrl").toString().contains("/userAuth") ||
        												requestMap.get("requestUrl").toString().contains("/hello/") ||
        												requestMap.get("requestUrl").toString().contains("/resources/") ||
        												requestMap.get("requestUrl").toString().contains("/errorLog") ||
        												requestMap.get("requestUrl").toString().contains("/requestLog") ||
        												requestMap.get("requestUrl").toString().contains("/batchLog") ||
        												requestMap.get("requestUrl").toString().contains("/mailLog"))) {

        		check = true;
        	}

        	if(!check) {
        		requestLogService.saveRequestLog(requestMap, responseMap, startDate);
        	}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ((ResponseWrapper) response).copyBodyToResponse();
    }

}
