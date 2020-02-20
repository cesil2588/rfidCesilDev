package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface SearchService {

	public Map<String, Object> searchAll(String search, String searchType, Long companySeq, String style, String color, String size) throws Exception;
	
	public Map<String, Object> searchRfidTag(RfidTagMaster rfidTag) throws Exception;
	
	public List<SelectBartagModel> selectBartagStyle(Long companySeq) throws Exception;
	
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String style) throws Exception;
	
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String style, String color) throws Exception;
	
	public SelectBartagModel selectBartagErpkey(String style, String color, String size) throws Exception;
	
}
