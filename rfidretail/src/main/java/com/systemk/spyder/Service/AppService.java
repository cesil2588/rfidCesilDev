package com.systemk.spyder.Service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.AppInfo;

public interface AppService {

	public Page<AppInfo> findAll(String startDate, String endDate, String type, String representYn, String search, String option, Pageable pageable) throws Exception;
	
	public AppInfo save(AppInfo appInfo) throws Exception;
	
	public void updateRepresentYn(AppInfo appInfo) throws Exception;
	
	public Long countRepresentYn(String appType) throws Exception;
	
	public Long countByRepresentYnAndAppSeqNotIn(Long appSeq, String appType) throws Exception;
	
	public void saveLog(Long appSeq) throws Exception;
	
	public AppInfo currentRepresentApp(String appType) throws Exception;
	
	public Map<String, Object> versionCheck(String version, String appType) throws Exception;
}
