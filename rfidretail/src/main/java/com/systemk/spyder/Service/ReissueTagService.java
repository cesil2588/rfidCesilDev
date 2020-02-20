package com.systemk.spyder.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Service.CustomBean.Select.SelectApiBartagModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface ReissueTagService {
	
	public Page<RfidTagReissueRequest> findAll(String startDate, String endDate, Long companySeq, String reissueYn, String search, String option, Pageable pageable) throws Exception;
	
	public List<SelectBartagModel> selectBartagYy(Long companySeq, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagColorContain(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagSizeContain(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagRfidTagContain(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderDegree(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception;
	
	public List<SelectBartagModel> selectBartagAdditionOrderDegree(Long companySeq, String productYy, String productSeason, String style, String color, String size, String orderDegree, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagYy(Long companySeq, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagSeason(Long companySeq, String productYy, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagStyle(Long companySeq, String productYy, String productSeason, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagColorContain(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagSizeContain(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception;
	
	public List<SelectBartagModel> selectApiBartagRfidTagContain(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception;
	
	public List<SelectApiBartagModel> generateApiBartag(List<SelectBartagModel> selectBartagList) throws Exception;
}
