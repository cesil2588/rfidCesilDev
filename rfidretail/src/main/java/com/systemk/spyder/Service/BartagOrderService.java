package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Service.CustomBean.BartagSubCompany;
import com.systemk.spyder.Service.CustomBean.Group.BartagOrderGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface BartagOrderService {

	public List<BartagOrderGroupModel> findBartagOrderGroupList(String startDate, String endDate, Long companySeq, String searchDate, String search, String option, Pageable pageable, String style) throws Exception;
	
	public Long CountBartagOrderGroupList(String startDate, String endDate, Long companySeq, String searchDate, String search, String option, String style) throws Exception;
	
	public Page<BartagOrder> findAll(String date, Long companySeq, String subCompanyName, String stat, String productYy, String productSeason, String style, String color, String size, String searchDate, String search, String option, Pageable pageable) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderYy(Long companySeq) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderSeason(Long companySeq, String productYy) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderStyle(Long companySeq, String productYy, String productSeason) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderColor(Long companySeq, String productYy, String productSeason, String style) throws Exception;
	
	public List<SelectBartagModel> selectBartagOrderSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception;
	
	public Map<String, Object> bartagOrderAmount(List<BartagOrder> bartagOrderList) throws Exception;
	
	public Map<String, Object> bartagOrderComplete(List<BartagOrder> bartagOrderList) throws Exception;
	
	public void bartagOrderEndUpdate(String erpKey, String orderDegree) throws Exception;
	
	public String maxOrderDegree(String erpKey) throws Exception; 
	
	public String checkOrderDegree(String erpKey, String orderDegree) throws Exception;
	
	public Map<String, Object> findBartagOrderDetail(Long seq, Pageable pageable) throws Exception;
	
	public List<BartagSubCompany> findSubCompanyList(Long companySeq) throws Exception;

	public List<SelectBartagModel> selectBartagOrderStylePerCom(Long companySeq) throws Exception;
}
