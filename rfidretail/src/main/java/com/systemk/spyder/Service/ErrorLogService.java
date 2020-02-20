package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;

import com.systemk.spyder.Entity.Main.ErrorLog;

public interface ErrorLogService {

	public void save(HttpServletRequest request, Exception ex, String resultCode, Device device) throws Exception;

	public Page<ErrorLog> findAll(String startDate, String endDate, String errorCode, String requestUrl, Pageable pageable) throws Exception;

	public List<Map<String, Object>> selectAllUrl() throws Exception;
}