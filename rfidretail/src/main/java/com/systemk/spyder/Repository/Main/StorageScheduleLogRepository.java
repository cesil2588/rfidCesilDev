package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;

public interface StorageScheduleLogRepository extends JpaRepository<StorageScheduleLog, Long>, JpaSpecificationExecutor<StorageScheduleLog>{
	
	public List<StorageScheduleLog> findByCompleteYn(String completeYn);
	
	public List<StorageScheduleLog> findByBoxInfoBarcodeAndCompleteYn(String barcode, String completeYn);
	
	public List<StorageScheduleLog> findByBoxInfoBarcodeAndConfirmYn(String barcode, String confirmYn);
	
	public StorageScheduleLog findByBoxInfoBarcodeAndDisuseYn(String barcode, String disuseYn);
	
	public StorageScheduleLog findByBoxInfoBarcode(String barcode);
	
	public StorageScheduleLog findByBoxInfoBoxSeq(Long boxSeq);

	public StorageScheduleLog findTop1ByCreateDateAndOrderTypeAndBoxInfoStartCompanyInfoCompanySeqOrderByWorkLineDesc(String createDate, String orderType, Long companySeq);
	
	public List<StorageScheduleLog> findByCreateDateAndWorkLineAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndDisuseYn(String createDate, Long workLine, Long companySeq, String confirmYn, String disuseYn);
	
	public List<StorageScheduleLog> findByArrivalDateAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndCompleteYnAndDisuseYn(String arrivalDate, Long companySeq, String confirmYn, String completeYn, String disuseYn);
	
	public List<StorageScheduleLog> findByBoxInfoBarcodeLike(String barcode);
	
	@Query(value = "SELECT COUNT(*) " +
	        		 "FROM production_storage_rfid_tag psrt " +
	        		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
	        		        "FROM storage_schedule_detail_log ssdl " + 
	        		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
	        		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
	        		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
	        		        "AND sssdl.rfid_tag = psrt.rfid_tag ) " +
	        		        "AND stat = :stat ", nativeQuery = true)
	public Long validProductionRfidTag(@Param("seq") Long seq, @Param("stat") String stat);
	
	@Query(value = "SELECT COUNT(*) " +
   		 "FROM production_storage_rfid_tag psrt " +
   		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
   		        "FROM storage_schedule_detail_log ssdl " + 
   		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
   		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
   		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
   		        "AND sssdl.rfid_tag = psrt.rfid_tag ) ", nativeQuery = true)
	public Long validProductionRfidTag(@Param("seq") Long seq);
	
	@Query(value = "SELECT COUNT(*) " +
	   		 "FROM distribution_storage_rfid_tag bsrt " +
	   		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
	   		        "FROM storage_schedule_detail_log ssdl " + 
	   		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
	   		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
	   		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
	   		        "AND sssdl.rfid_tag = bsrt.rfid_tag ) " +
	   		        "AND stat = :stat ", nativeQuery = true)
	public Long validDistributionRfidTag(@Param("seq") Long seq, @Param("stat") String stat) ;
	
	@Query(value = "SELECT COUNT(*) " +
	   		 "FROM distribution_storage_rfid_tag bsrt " +
	   		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
	   		        "FROM storage_schedule_detail_log ssdl " + 
	   		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
	   		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
	   		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
	   		        "AND sssdl.rfid_tag = bsrt.rfid_tag )", nativeQuery = true)
	public Long validDistributionRfidTag(@Param("seq") Long seq) ;
	
	@Query(value = "SELECT COUNT(*) " +
	   		 "FROM store_storage_rfid_tag ssrt " +
	   		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
	   		        "FROM storage_schedule_detail_log ssdl " + 
	   		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
	   		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
	   		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
	   		        "AND sssdl.rfid_tag = ssrt.rfid_tag ) " + 
	   		        "AND stat = :stat ", nativeQuery = true)
	public Long validStoreRfidTag(@Param("seq") Long seq, @Param("stat") String stat);
	
	@Query(value = "SELECT COUNT(*) " +
			   		 "FROM store_storage_rfid_tag ssrt " +
			   		"WHERE EXISTS (SELECT sssdl.rfid_tag " +
			   		        "FROM storage_schedule_detail_log ssdl " + 
			   		        "INNER JOIN storage_schedule_sub_detail_log sssdl " +
			   		           "ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq " +
			   		        "WHERE ssdl.storage_schedule_log_seq = :seq " +
			   		        "AND sssdl.rfid_tag = ssrt.rfid_tag ) ", nativeQuery = true)
	public Long validStoreRfidTag(@Param("seq") Long seq);
	
	
}
