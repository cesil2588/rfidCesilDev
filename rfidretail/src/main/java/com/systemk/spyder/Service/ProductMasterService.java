package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

public interface ProductMasterService {
	
	public Page<ProductMaster> findAll(String productYy, String productSeason, String style, String search, String option, Pageable pageable) throws Exception;
	
	public List<SelectGroupBy> findSelectGroupBy() throws Exception;

	public Map<String, Object> findByMasterList(String version, String appType) throws Exception;
}
