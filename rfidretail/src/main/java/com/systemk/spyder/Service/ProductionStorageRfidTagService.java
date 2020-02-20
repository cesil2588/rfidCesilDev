package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.InspectionBean;
import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Dto.Request.ReleaseUpdateBean;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface ProductionStorageRfidTagService {

	public Page<ProductionStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception;

	public List<ProductionStorageRfidTag> findAll(String style, String color, String size) throws Exception;

	public Page<ProductionStorageRfidTag> findAll(Long companySeq, Pageable pageable) throws Exception;

	public Page<ProductionStorageRfidTag> findStat(long seq, String stat, Pageable pageable) throws Exception;

	public Page<ProductionStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception;

	public void inspectionWeb(long seq) throws Exception;

	public Map<String, Object> inspectionMobile(InspectionBean inspectionBean) throws Exception;

	public boolean inspectionWebList(List<ProductionStorage> productionStorageList) throws Exception;

	public boolean inspectionBatch(BatchTrigger trigger) throws Exception;

	public Map<String, Object> releaseComplete(ReleaseBean releaseBean) throws Exception;

	public Map<String, Object> releaseUpdate(ReleaseUpdateBean releaseUpdateBean) throws Exception;

	public Map<String, Object> barcodeValid(String barcode, String type) throws Exception;

	public void update(long seq, long userSeq, String updateStat, String targetStat);

	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception;

	public CountModel count(long productionStorageSeq) throws Exception;

	public Map<String, Object> storageComplete(Long seq) throws Exception;

	public Map<String, Object> releaseComplete(Long seq) throws Exception;

	public List<Long> storageCompleteBatch(TempProductionStorageHeader header) throws Exception;

	public List<Long> releaseCompleteBatch(TempProductionReleaseHeader header) throws Exception;

	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception;

	public Map<String, Object> releaseMiddleWareTest(ReleaseBean releaseBean) throws Exception;

	public List<Long> findByBoxProductionSeq(Long boxSeq) throws Exception;

}
