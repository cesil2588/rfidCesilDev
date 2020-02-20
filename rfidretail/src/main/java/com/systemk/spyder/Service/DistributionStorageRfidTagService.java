package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.DistributionReleaseCompleteBean;
import com.systemk.spyder.Dto.Request.ReferenceBean;
import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface DistributionStorageRfidTagService {

	public Page<DistributionStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception;

	public Page<DistributionStorageRfidTag> findStat(Long seq, String stat, Pageable pageable) throws Exception;

	public Page<DistributionStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception;

	public void inspectionAll(Long distributionStorageSeq);

	public CountModel count(Long distributionStorageSeq) throws Exception;

	public CountModel count(String startDate, String endDate, Long companySeq) throws Exception;

	public Map<String, Object> inspectionBox(BoxInfo boxInfo, UserInfo userInfo, String type) throws Exception;

	public List<Long> inspectionBoxBatch(BoxInfo boxInfo, UserInfo userInfo) throws Exception;

	public List<Long> inspectionBoxBatchTest(BoxInfo boxInfo, UserInfo userInfo) throws Exception;

	public Map<String, Object> releaseComplete(DistributionReleaseCompleteBean releaseCompleteBean) throws Exception;

	public Map<String, Object> releaseCompleteAfter(String barcode) throws Exception;

	public void releaseCompleteAfterBatch(TempDistributionReleaseBox tempReleaseBox) throws Exception;

	public Map<String, Object> exceptionReleaseComplete(ReleaseBean releaseBean, String flag) throws Exception;

	public Map<String, Object> exceptionReleaseSchedule(String barcode) throws Exception;

	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception;

	public void deleteBoxInfo(Long boxSeq) throws Exception;

	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception;

	public DistributionStorageRfidTag moveRfidTag(DistributionStorageRfidTag rfidTag, DistributionStorage storage, UserInfo userInfo) throws Exception;

	public DistributionStorageRfidTag updateRfidTag(DistributionStorageRfidTag rfidTag, UserInfo userInfo) throws Exception;

	public List<DistributionStorageRfidTag> findAll(String customerCode, String style, String color, String size) throws Exception;

	public List<Long> findByBoxDistributionSeq(Long boxSeq) throws Exception;

	public List<String> existsStorageDistriubtionRfidTag(String barcode) throws Exception;

	public List<String> existsReleaseDistriubtionRfidTag(TempDistributionReleaseBox tempReleaseBox) throws Exception;

	public void productionRfidTagMove(TempDistributionReleaseBox tempReleaseBox) throws Exception;

	public Map<String, Object> findMasterNonCompleteTag(String rfidTag) throws Exception;

	public void releaseProcess(List<String> rfidList, UserInfo userInfo, BoxInfo boxInfo, Long seq, String type, String flag) throws Exception;

	public Map<String, Object> findByReleaseSchedulePost(ReferenceBean referenceBean) throws Exception;

	public Map<String, Object> releaseCompleteDummy(ReferenceBean referenceBean) throws Exception;
}
