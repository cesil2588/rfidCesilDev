package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.StoreReturnBean;
import com.systemk.spyder.Dto.Request.StoreReturnListBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface StoreStorageRfidTagService {

	public Page<StoreStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception;

	public Page<StoreStorageRfidTag> findStat(Long seq, String stat, Pageable pageable) throws Exception;

	public Page<StoreStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception;

	public void inspectionAll(Long storeStorageSeq);

	public CountModel count(Long storeStorageSeq) throws Exception;

	public CountModel count(String startDate, String endDate, Long companySeq) throws Exception;

	public void inspectionBox(BoxInfo boxInfo) throws Exception;

	public Map<String, Object> inspectionBox(BoxInfo boxInfo, UserInfo userInfo, String type) throws Exception;

	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception;

	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception;

	public void rollbackNonCheck(Long userSeq, Long boxSeq) throws Exception;

	public void rollbackStoreMove(Long userSeq, String companyCode, Long startStorageSeq, Long endStorageSeq) throws Exception;

	public void rollbackReissueRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception;

	public Map<String, Object> returnSave(StoreReturnBean returnBean) throws Exception;

	public Map<String, Object> returnUpdate(StoreReturnBean returnBean) throws Exception;

	public Map<String, Object> returnDelete(StoreReturnListBean returnBeanList) throws Exception;

	public Map<String, Object> returnComplete(StoreReturnListBean returnBeanList) throws Exception;

	public Map<String, Object> returnDetail(String barcode) throws Exception;

	public StoreStorageRfidTag moveRfidTag(StoreStorageRfidTag rfidTag, StoreStorage storeStorage, UserInfo userInfo) throws Exception;

	public StoreStorageRfidTag updateRfidTag(StoreStorageRfidTag rfidTag, UserInfo userInfo) throws Exception;

	public List<String> existsNotStoreRfidTag(ReleaseScheduleLog releaseScheduleLog) throws Exception;
}
