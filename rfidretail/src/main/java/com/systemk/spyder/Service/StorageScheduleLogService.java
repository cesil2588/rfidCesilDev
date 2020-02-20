package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Response.ApiStorecheduleCompleteResult;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleModel;
import com.systemk.spyder.Service.CustomBean.Group.ReturnGroupModel;
import com.systemk.spyder.Service.CustomBean.Group.StorageScheduleGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public interface StorageScheduleLogService {

	public Page<StorageScheduleLog> findAll(String createDate, String workLine, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public Page<StorageScheduleLog> findAll(String arrivalDate, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public Page<StorageScheduleLog> findAll(String startDate, String endDate, Long startCompanySeq, String confirmYn, String completeYn, String orderType, Pageable pageable) throws Exception;

	public List<StorageScheduleLog> findAll(String arrivalDate, String confirmYn, String completeYn) throws Exception;

	public List<StorageScheduleLog> findAll(String createDate, String workLine, Long companySeq) throws Exception;

	public List<StorageScheduleModel> findReleaseGroupList(String startDate, String endDate, Long startCompanySeq, String confirmYn, String type, String search, String option, Pageable pageable) throws Exception;

	public List<StorageScheduleModel> findReleaseGroupList(String createDate, Long startCompanySeq, String confirmYn, String completeYn, String type) throws Exception;

	public Long CountReleaseGroupList(String startDate, String endDate, Long startCompanySeq, String confirmYn, String type, String search, String option) throws Exception;

	public List<StorageScheduleLog> save(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String flag, String returnType) throws Exception;

	public void saveTest(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String flag, String returnType) throws Exception;

	public StorageScheduleLog update(StorageScheduleLog storageScheduleLog, UserInfo userInfo, String flag) throws Exception;

	public StorageScheduleModel storageScheduleLogCount(String startDate, String endDate, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String option, String search, String type) throws Exception;

	public CountModel storageScheduleLogBoxCount(String startDate, String endDate, Long startCompanySeq, String flag, String type) throws Exception;

	public CountModel storageScheduleLogTagCount(String startDate, String endDate, Long startCompanySeq, String flag, String type) throws Exception;

	public CountModel storageScheduleLogBoxCount(String createDate, String confirmYn, String completeYn, Long startCompanySeq, String orderType) throws Exception;

	public CountModel storageScheduleLogTagCount(String createDate, String confirmYn, String completeYn, Long startCompanySeq, String orderType) throws Exception;

	public CountModel distributionStockStorageScheduleLogBoxCount(String startDate, String endDate, Long companySeq) throws Exception;

	public CountModel distributionStockStorageScheduleLogStyleCount(String startDate, String endDate, Long companySeq) throws Exception;

	public CountModel distributionStockStorageScheduleLogTagCount(String startDate, String endDate, Long companySeq) throws Exception;

	public List<StorageScheduleGroupModel> findStorageScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception;

	public Long CountStorageScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception;

	public Long maxWorkLine(String createDate, String orderType, Long companySeq) throws Exception;

	public Map<String, Object> storeScheduleBatch(String startDate, String endDate, Long companySeq) throws Exception;

	public Map<String, Object> updateReleaseGroup(List<StorageScheduleModel> groupList) throws Exception;

	public Map<String, Object> deleteReleaseGroup(List<StorageScheduleModel> groupList) throws Exception;

	public Map<String, Object> updateRelease(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public Map<String, Object> deleteRelease(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public Map<String, Object> deleteReleaseConfirm(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public Map<String, Object> updateReturnGroup(List<StorageScheduleModel> groupList) throws Exception;

	public Map<String, Object> deleteReturnGroup(List<StorageScheduleModel> groupList) throws Exception;

	public Map<String, Object> updateReturn(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public Map<String, Object> deleteReturn(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public ApiStorecheduleCompleteResult storeScheduleComplete(String barcode, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeScheduleComplete(List<Map<String, Object>> barcodeList, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeScheduleCompleteGroup(List<Map<String, Object>> scheduleGroupList, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeScheduleReturn(List<StorageScheduleLog> storageScheduleLogList, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeScheduleReturnGroup(List<StorageScheduleModel> groupList, Long userSeq, String type) throws Exception;

	public ApiStorecheduleCompleteResult updateStoreScheduleExceptionComplete(List<StorageScheduleLog> scheduleLogList, UserInfo userInfo, String type) throws Exception;

	public void storageInit(StorageScheduleLog storageScheduleLog) throws Exception;

	public boolean storageUpdate(StorageScheduleLog storageScheduleLog) throws Exception;

	public boolean storageUpdate(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	public List<ReturnGroupModel> findReturnGroupList(String startDate, String endDate, Long companySeq, String orderType, Pageable pageable) throws Exception;

	public Long CountReturnGroupList(String startDate, String endDate, Long companySeq, String orderType) throws Exception;

	public Page<StorageScheduleLog> findReturnAll(String createDate, Long startCompanySeq, String confirmYn, String completeYn, String orderType, String search, String option, Pageable pageable) throws Exception;

	public List<StorageScheduleModel> findReturnGroupDetailList(List<ReturnGroupModel> groupList) throws Exception;

	public List<SelectBartagModel> selectBartagStyle(String startDate, String endDate, Long companySeq, String flag) throws Exception;

	public List<SelectBartagModel> selectBartagColor(String startDate, String endDate, Long companySeq, String style, String flag) throws Exception;

	public List<SelectBartagModel> selectBartagSize(String startDate, String endDate, Long companySeq, String style, String color, String flag) throws Exception;

	public Set<StorageScheduleDetailLogModel> generateScheduleLog(StorageScheduleLog storageScheduleLog, String flag) throws Exception;

	public StoreScheduleModel boxStoreScheduleList(String barcode) throws Exception;

	public List<Long> storageScheduleLogConfirmBatch(StorageScheduleLog storageScheduleLog) throws Exception;

	public List<Long> storageScheduleLogCompleteBatch(StorageScheduleLog scheduleLog) throws Exception;

	public List<StorageScheduleGroupModel> findStorageReturnScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception;

	public Long CountStorageReturnScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception;

	public CountModel distributionStockStorageReturnScheduleLogBoxCount(String startDate, String endDate, Long companySeq) throws Exception;

	public CountModel distributionStockStorageReturnScheduleLogStyleCount(String startDate, String endDate, Long companySeq) throws Exception;

	public CountModel distributionStockStorageReturnScheduleLogTagCount(String startDate, String endDate, Long companySeq) throws Exception;

	public Page<StorageScheduleLog> findAll(String startDate, String endDate, Long startCompanySeq, String confirmYn,String completeYn, String style, String color, String size, String search, String option, Pageable pageable,
			String orderType) throws Exception;

	public List<StorageScheduleDetailLog> findReturnScheduleByBarcode(String barcode) throws Exception;

}