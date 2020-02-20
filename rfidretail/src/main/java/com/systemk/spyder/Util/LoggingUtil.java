package com.systemk.spyder.Util;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.systemk.spyder.Config.RequestLoggingFilterConfig.RequestWrapper;
import com.systemk.spyder.Config.RequestLoggingFilterConfig.ResponseWrapper;

public class LoggingUtil {

    public static Map<String, Object> makeLoggingRequestMap(final HttpServletRequest request) {
        // request info
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestUrl", request.getRequestURL().toString());
        requestMap.put("queryString", request.getQueryString());
        requestMap.put("method", request.getMethod());
        requestMap.put("remoteAddr", request.getRemoteAddr());
        requestMap.put("remoteHost", request.getRemoteHost());
        requestMap.put("remotePort", request.getRemotePort());
        requestMap.put("remoteUser", request.getRemoteUser());
        requestMap.put("encoding", request.getCharacterEncoding());

        // request header
        Map<String, Object> requestHeaderMap = new HashMap<>();
        Enumeration<String> requestHeaderNameList = request.getHeaderNames();
        while(requestHeaderNameList.hasMoreElements()) {
            String headerName = requestHeaderNameList.nextElement();
            requestHeaderMap.put(headerName, request.getHeader(headerName));

            String value = request.getHeader(headerName);

            if(headerName.equals("user-agent")){
            	requestMap.put("userAgent", value);
            }

            if(headerName.equals("cookie")){
            	String[] str = StringUtils.split(value, "=");

            	if(str.length > 0) {
            		requestMap.put("session", str[1]);
            	}
            }

        }

        requestMap.put("header", StringUtil.convertJsonString(requestHeaderMap));

        // request Body
        try {
            // 이부분 주목!!
            Object requestBody = ((RequestWrapper) request).convertToObject();
            requestMap.put("body", StringUtil.convertJsonString(requestBody));
        } catch (IOException ex) {
        }

        return requestMap;
    }

    public static Map<String, Object> makeLoggingResponseMap(final HttpServletResponse response) throws IOException {
        // response info
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", response.getStatus());
        responseMap.put("contentType", response.getContentType());

        // response header
        Map<String, Object> responseHeaderMap = new HashMap<>();
        Collection<String> responseHeaderNameList = response.getHeaderNames();
        responseHeaderNameList.forEach(v -> responseHeaderMap.put(v, response.getHeader(v)));
        responseMap.put("header", StringUtil.convertJsonString(responseHeaderMap));

        // response body
        try {
            // application/octet-stream 일때 Body to Json 작업 회피
        	if(responseMap.get("contentType") != null && !responseMap.get("contentType").toString().contains("application/octet-stream")) {
        		Object responseBody = ((ResponseWrapper) response).convertToObject();
                responseMap.put("body", StringUtil.convertJsonString(responseBody));
        	}
        } catch (IOException ex) {
        }

        return responseMap;
    }
}
