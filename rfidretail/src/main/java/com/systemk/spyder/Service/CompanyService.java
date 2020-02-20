package com.systemk.spyder.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Service.CustomBean.Select.SelectCompanyModel;

public interface CompanyService {
	
	public Page<CompanyInfo> findAll(Long companySeq, String type, String search, String option, Pageable pageable) throws Exception;
	
	public String findAll();
	
	public void saveAll();
	
	public List<SelectCompanyModel> selectApiCompanyHeader(String roleType) throws Exception;
	
	public List<SelectCompanyModel> selectApiCompanyDetail(String type) throws Exception;
}
