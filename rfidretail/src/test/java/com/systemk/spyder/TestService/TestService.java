package com.systemk.spyder.TestService;

import java.util.List;
import java.util.Map;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.TestModel.DuplicationBartagModel;

public interface TestService {

	void initTestDate(Long seq);

	void transactionalTest(String data) throws Exception;

	void reissueTagList() throws Exception;

	void reissueTagRequestUpdate() throws Exception;

	void reissueTagUpdate() throws Exception;

	void tempStorageInit() throws Exception;

	void tempReleaseInit() throws Exception;

	void tempReleaseInitTest() throws Exception;

	Map<String, Object> parseRfidTag(String rfidTag) throws Exception;

	void storageInit(StorageScheduleLog storageScheduleLog) throws Exception;

	void releaseInit() throws Exception;

	void deleteStoreRelease(Long seq) throws Exception;

	void erpTestTagUpload() throws Exception;

	void erpTestTagClean() throws Exception;

	List<String> erpTagDuplicationList() throws Exception;

	void bartagOrderInit() throws Exception;

	void boxWorkGroupCreateDateInit() throws Exception;

	void checkCustomerCd() throws Exception;

	void checkBartagNullCustomerCd() throws Exception;

	void updateBartagCustomerCd() throws Exception;

	void productRedisInit() throws Exception;

	void rfidTagCustomerCdInit() throws Exception;

	void distributionStorageScheduleDelete() throws Exception;

	List<DuplicationBartagModel> duplicateBartag(String createDate) throws Exception;

	void duplicationBartagInit() throws Exception;

	void jpqlCountTest(Long seq) throws Exception;

	void storageScheduleBatchUpdate() throws Exception;

	void storageLogInit(String createDate, Long companySeq) throws Exception;

	void releaseErp() throws Exception;

	void productionReleaseUpdate(String boxBarcode, String targetBoxBarcode, Long userSeq) throws Exception;

	void reissueTagRollback(String rfidTag) throws Exception;

	void distributionStorageInspection() throws Exception;

	void storageScheduleRfidTagInit(List<Long> scheduleSeqList) throws Exception;

	void rfidAndErpStorageCompleteMatch() throws Exception;

	void bartagOrderGet(String createDate) throws Exception;

	void allRequestMappingUrl() throws Exception;

	void bartagOrderCreateNoInit() throws Exception;

	void storageLogRollback() throws Exception;

	void storageErpComplete() throws Exception;

	void storageCompleteRfidStatInit() throws Exception;

	void readLogParse(String fileName, String format) throws Exception;

	void readLogSqlParse(String fileName, String format) throws Exception;

	void compulsionRelease(Long seq) throws Exception;

	void compulsionReleaseChack(Long userSeq) throws Exception;

	void storageRollBack(String startBarcode, String endBarcode) throws Exception;

	void storageRollBack(String barcode) throws Exception;

	void storageRollBack(List<String> barcodeList, Long userSeq) throws Exception;

	void mailTest(String email) throws Exception;

	void tempDataCheck(Long seq) throws Exception;

	void errorComplete() throws Exception;

	void bartagOrderProductionNullCheck() throws Exception;

	void getResultProcedure(String testNo, int testPw) throws Exception;

	void deleteTestData() throws Exception;

	void forceReleaseBoxToJson() throws Exception;

	void deleteForceReleaseBox(String createDate) throws Exception;

	void erpStoreStorageComplete() throws Exception;

	void erpStoreStorageRepository() throws Exception;

	void forceInventory() throws Exception;

	void deleteInventory() throws Exception;

	void forceErpInventory() throws Exception;

	void transactionTest() throws Exception;
}