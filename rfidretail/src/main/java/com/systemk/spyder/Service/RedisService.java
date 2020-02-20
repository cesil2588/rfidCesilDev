package com.systemk.spyder.Service;

import java.util.Map;

public interface RedisService {

	public Map<Long, String> findAll(String name) throws Exception;
	
	public String find(String key, Long seq) throws Exception;
	
	public String find(String key, String seq) throws Exception;
	
	public void save(String key, Long seq, String value) throws Exception;
	
	public void save(String key, String seq, String value) throws Exception;
	
	public void delete(String key, Long seq) throws Exception;
	
	public String getRedisTemplateValue(String name) throws Exception;
	
	/*
	public boolean setCodeList();
	
	public boolean setCompanyList();
	
	public String getRedisTemplateValue(String name) throws Exception;
	
	public void setHashCodeList() throws Exception;
	
	public String getTestRedis(String name) throws Exception;
	
	public void setTestRedis(String name, String value) throws Exception;
	
	*/
}
