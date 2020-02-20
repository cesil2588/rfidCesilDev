package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.BoxTagListBean;
import com.systemk.spyder.Dto.Request.ReferenceBean;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Service.CustomBean.Group.StoreScheduleGroupModel;

public interface ErpStoreScheduleService {

	public Map<String, Object> findByReleaseSchedule(String referenceNo) throws Exception;

	public Map<String, Object> findByReleaseSchedulePost(ReferenceBean referenceBean) throws Exception;

	public List<ErpStoreSchedule> findByReleaseScheduleList(String referenceNo, String style, String color, String size) throws Exception;

	public ErpStoreSchedule findByReleaseSchedule(String referenceNo, String style, String color, String size) throws Exception;

	public List<StoreScheduleGroupModel> findStoreScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception;

	public Long CountStoreScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception;

	public Page<ErpStoreSchedule> findAll(String completeDate, String completeSeq, String completeType, String style, String color, String size, String search, String option, Pageable pageable) throws Exception;

	public Map<String, Object> save(ErpStoreSchedule erpStoreSchedule) throws Exception;

	public Map<String, Object> delete(List<ErpStoreSchedule> storeScheduleList) throws Exception;

	public boolean releaseCompleteAfterErpBatch(ErpStoreSchedule storeSchedule) throws Exception;

	public void StorageReturnScheduleLog(ErpStoreReturnSchedule erpStoreReturnSchedule);

	public Map<String, Object> findByStoreReturnSchedule(String barcode) throws Exception;

	public Map<String, Object> updateReturnRfidTag(BoxTagListBean tagBean);

}
