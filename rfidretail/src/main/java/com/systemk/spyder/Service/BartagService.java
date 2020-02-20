package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Response.BartagMinMaxResult;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.CustomBean.BartagSubCompany;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.BartagGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

public interface BartagService {

	public Page<BartagMaster> findAll(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option, Pageable pageable) throws Exception;

	public Page<BartagMaster> findAll(String regDate, Long companySeq, String subCompanyName, String completeYn, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public List<BartagMaster> findAll(String regDate, Long companySeq, String defaultDateType, String subCompanyName, String completeYn) throws Exception;

	public List<BartagMaster> findAll(String startDate, String endDate, Long companySeq) throws Exception;

	public List<BartagMaster> findAll(String startDate, String endDate, Long companySeq, String productYy, String productSeason) throws Exception;

	public JSONObject countAll(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option) throws Exception;

	public List<BartagGroupModel> findBartagGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception;

	public Long CountBartagGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception;

	public String bartagCompleteProcess(Long bartagSeq, Long userSeq) throws Exception;

	public String bartagCompleteProcessMod(BartagMaster bartag, UserInfo userInfo) throws Exception;

	public String bartagReissueProcess(BartagMaster bartag, UserInfo userInfo) throws Exception;

	public String bartagDownloadSeq(String bartagRegDate, LoginUser user) throws Exception;

	public List<BartagSubCompany> findSubCompanyList(Long companySeq) throws Exception;

	public CountModel count(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option) throws Exception;

	public Long bartagMaxSeq() throws Exception;

	public BartagMaster bartagSerialGenerate(BartagMaster bartagMaster) throws Exception;

	public BartagMaster bartagSerialGenerate(BartagMaster bartagMaster, String additionOrderDegree) throws Exception;

	public Map<String, Object> delete(List<BartagMaster> bartagList) throws Exception;

	public Map<String, Object> update(List<BartagMaster> bartagList) throws Exception;

	public Map<String, Object> complete(List<BartagMaster> bartagList) throws Exception;

	public List<SelectBartagModel> selectBartagYy(Long companySeq) throws Exception;

	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy) throws Exception;

	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason) throws Exception;

	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style) throws Exception;

	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception;

	public Map<String, Object> apiFindOne(Long bartagSeq) throws Exception;

	public Map<String, Object> apiFindAll(String startDate, String endDate, Long companySeq, String subCompanyName, String completeYn, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public Map<String, Object> getBartagHistory(String rfidTag) throws Exception;

	public Map<String, Object> getBartagList(Long companySeq, String style, String color, String size) throws Exception;

	public List<SelectGroupBy> findSkuPerComany(Long companySeq) throws Exception;

	public BartagMinMaxResult findByMinMax(String style, String color, String size) throws Exception;

}
