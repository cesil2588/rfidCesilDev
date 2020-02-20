package com.systemk.spyder.Service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface DistributionStorageService {

	public Page<DistributionStorage> findAll(String startDate, String endDate, Long companySeq, String productSeason, String search, String option, Pageable pageable) throws Exception;
	
	public Page<DistributionStorage> findAll(String startDate, String endDate, Long companySeq, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;
	
	public JSONObject countAll(String startDate, String endDate, Long companySeq, String productSeason, String search, String option) throws Exception;
	
	public CountModel count(String startDate, String endDate, Long companySeq, String productSeason, String search, String option) throws Exception;
	
	public List<SelectBartagModel> selectBartagYy(Long companySeq) throws Exception;
	
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy) throws Exception;
	
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason) throws Exception;
	
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style) throws Exception;
	
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception;
	
	public List<SelectBartagModel> findStyleList() throws Exception;
	
}