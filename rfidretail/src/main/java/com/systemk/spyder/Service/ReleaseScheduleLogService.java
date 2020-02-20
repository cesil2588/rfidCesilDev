package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.StoreScheduleCompleteBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreReleaseGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface ReleaseScheduleLogService {

	public void save(TempDistributionReleaseBox tempReleaseBox, BoxInfo boxInfo, UserInfo userInfo, Long workLine, String orderType) throws Exception;

	public List<StoreReleaseGroupModel> findStoreReleaseGroupList(String startDate, String endDate, String search, String option, Pageable pageable) throws Exception;

	public List<StoreReleaseGroupModel> findStoreReleaseGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception;

	public Long CountReleaseGroupList(String startDate, String endDate, Long endCompanySeq, String search, String option) throws Exception;

	public Long maxWorkLine(String createDate, String orderType) throws Exception;

	public CountModel storeReleaseScheduleLogBoxCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception;

	public CountModel storeReleaseScheduleLogStyleCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception;

	public CountModel storeReleaseScheduleLogTagCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception;

	public CountModel storeReleaseScheduleLogBoxCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception;

	public CountModel storeReleaseScheduleLogStyleCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception;

	public CountModel storeReleaseScheduleLogTagCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception;

	public Page<ReleaseScheduleLog> findAll(String createDate, Long endCompanySeq, String search, String option, Pageable pageable) throws Exception;

	public Page<ReleaseScheduleLog> findAll(String createDate, Long endCompanySeq, String completeYn, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public List<ReleaseScheduleLog> findAll(String createDate, String confirmYn, String completeYn) throws Exception;

	public Map<String, Object> findStoreSchedule(String barcode) throws Exception;

	public Map<String, Object> storeScheduleComplete(List<Map<String, Object>> barcodeList, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeScheduleComplete(StoreScheduleCompleteBean req) throws Exception;

	public Map<String, Object> storeScheduleGroupComplete(List<Map<String, Object>> scheduleGroupList, Long userSeq, String type) throws Exception;

	public void storeScheduleCompleteBatch(ReleaseScheduleLog releaseScheduleLog) throws Exception;

	public List<SelectBartagModel> selectBartagStyle(String createDate, Long companySeq) throws Exception;

	public List<SelectBartagModel> selectBartagColor(String createDate, Long companySeq, String style) throws Exception;

	public List<SelectBartagModel> selectBartagSize(String createDate, Long companySeq, String style, String color) throws Exception;

}
