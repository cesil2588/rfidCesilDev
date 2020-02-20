package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;

public interface StoreStorageRfidTagRepository extends JpaRepository<StoreStorageRfidTag, Long>, JpaSpecificationExecutor<StoreStorageRfidTag>{
	
	public Long countByStoreStorageSeqAndStat(Long storeStorageSeq, String stat);
	
	public Long countByStoreStorageSeq(Long storeStorageSeq); 
	
	public List<StoreStorageRfidTag> findByStoreStorageSeq(Long storeStorageSeq);
	
	public Page<StoreStorageRfidTag> findByStoreStorageSeq(Long storeStorageSeq, Pageable page);
	
	public Page<StoreStorageRfidTag> findByStoreStorageSeqAndRfidTagContaining(Long storeStorageSeq, String rfidTag, Pageable page);
	
	public Page<StoreStorageRfidTag> findByStoreStorageSeqAndStat(Long storeStorageSeq, String stat, Pageable page);
	
	public List<StoreStorageRfidTag> findByBoxInfoBarcodeAndStat(String barcode, String stat);
	
	public List<StoreStorageRfidTag> findByBoxInfoBoxSeq(Long boxSeq);
	
	public List<StoreStorageRfidTag> findByBoxInfoBarcode(String barcode);
	
	public StoreStorageRfidTag findByRfidTag(String rfidTag);
	
	public Long countByRfidTag(String rfidTag);
	
	public StoreStorageRfidTag findByRfidTagAndStat(String rfidTag, String stat);
	
	public StoreStorageRfidTag findTopByErpKeyAndRfidSeq(String erpKey, String rfidSeq);
	
	
	@Query(value = "SELECT ssrt " + 
					 "FROM StoreStorageRfidTag ssrt " + 
					"WHERE EXISTS ( " + 
				   "SELECT tdrt.rfidTag " + 
				     "FROM TempDistributionReleaseBox tdrb " + 
				     "JOIN tdrb.styleList tdrs " + 
				     "JOIN tdrs.tagList tdrt " + 
			   		"WHERE ssrt.rfidTag = tdrt.rfidTag " + 
					  "AND tdrb.tempBoxSeq = :tempBoxSeq)")
	public List<DistributionStorageRfidTag> validTagList(@Param("tempBoxSeq") Long tempBoxSeq);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE StoreStorageRfidTag SET stat = 1, upd_date = getDate()" + 
					" WHERE rfid_tag = :rfidTag")
	public void updateRfidStat(@Param("rfidTag") String rfidTag);
	
}
