package com.systemk.spyder.Service;

import com.systemk.spyder.Dto.Response.FunctionResult;
import com.systemk.spyder.Entity.External.*;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;

import java.util.List;
import java.util.Map;

public interface ErpService {

	RfidIb10If saveStoreSchedule(BoxInfo boxInfo, Long lineNo, StorageScheduleDetailLogModel detailLog, UserInfo userInfo, String nowDate, String invoiceNum, String flag) throws Exception;

	List<RfidIb01If> saveStorageListComplete(List<StorageScheduleLog> storageScheduleLogList) throws Exception;

	List<RfidIb01If> saveStorageListComplete(StorageScheduleLog storageScheduleLog) throws Exception;

	List<RfidIb01If> saveStorageListRollback(StorageScheduleLog storageScheduleLog) throws Exception;

	int updateRfidTagStatUseYn(String rfidTag, String stat, String useYn) throws Exception;

	int disuseErpStorageSchedule(StorageScheduleLog storageScheduleLog) throws Exception;

	List<RfidSd02If> saveReturnSchedule(StorageScheduleLog storageScheduleLog) throws Exception;

	boolean storeMoveProccess(List<StoreMoveLog> storeMoveLogList) throws Exception;

	boolean completeStoreMove(List<StoreMoveLog> storeMoveLogList) throws Exception;

	boolean disuseStoreMove(List<StoreMoveLog> storeMoveLogList) throws Exception;

	RfidLe01If saveReleaseComplete(ErpStoreSchedule erpStoreSchedule, Long releaseAmount, Long sortingAmount) throws Exception;

	boolean saveBartag(List<BartagMaster> bartagList) throws Exception;

	List<RfidLf01If> saveStorageListReturnComplete(StorageScheduleLog scheduleLog) throws Exception;

	Map<String, Object> storeStorageFunctionCall(String erpCreateDate, Long releaseSeq, CompanyInfo companyInfo, String barcode) throws Exception;

	List<RfidMd14RtIf> saveStoreInventory(List<InventoryScheduleHeader> headerList, UserInfo userInfo) throws Exception;

	Map<String, Object> functionTestCall() throws Exception;

	Map<String, Object> functionTestCallJdbc(String name) throws Exception;
}
