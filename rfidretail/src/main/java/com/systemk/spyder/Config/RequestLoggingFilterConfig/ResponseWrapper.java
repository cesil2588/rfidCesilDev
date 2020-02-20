package com.systemk.spyder.Config.RequestLoggingFilterConfig;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HTTP Response to JSON 로직
 * @author youdozi
 *
 */
public class ResponseWrapper extends ContentCachingResponseWrapper {

	private ObjectMapper objectMapper;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        this.objectMapper = new ObjectMapper();
    }

    public Object convertToObject() throws IOException {
        return objectMapper.readValue(getContentAsByteArray(), Object.class);
    }
}