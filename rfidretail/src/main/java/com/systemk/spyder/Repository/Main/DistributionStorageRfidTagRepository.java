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

public interface DistributionStorageRfidTagRepository extends JpaRepository<DistributionStorageRfidTag, Long>, JpaSpecificationExecutor<DistributionStorageRfidTag>{

	public Long countByDistributionStorageSeqAndStat(Long distributionStorageSeq, String stat);

	public Long countByDistributionStorageSeq(Long distributionStorageSeq);

	public List<DistributionStorageRfidTag> findByDistributionStorageSeq(Long distributionStorageSeq);

	public Page<DistributionStorageRfidTag> findByDistributionStorageSeq(Long distributionStorageSeq, Pageable page);

	public Page<DistributionStorageRfidTag> findByDistributionStorageSeqAndRfidTagContaining(Long distributionStorageSeq, String rfidTag, Pageable page);

	public Page<DistributionStorageRfidTag> findByDistributionStorageSeqAndStat(Long distributionStorageSeq, String stat, Pageable page);

	public List<DistributionStorageRfidTag> findByBoxInfoBarcodeAndStat(String barcode, String stat);

	public List<DistributionStorageRfidTag> findByBoxInfoBarcode(String barcode);

	public List<DistributionStorageRfidTag> findByBoxInfoBoxSeq(Long boxSeq);

	public DistributionStorageRfidTag findByRfidTagAndStat(String rfidTag, String stat);

	public DistributionStorageRfidTag findByRfidTag(String rfidTag);

	public void deleteByBoxInfoBoxSeq(Long boxSeq);

	public DistributionStorageRfidTag findTopByErpKeyAndRfidSeq(String erpKey, String rfidSeq);

	public List<DistributionStorageRfidTag> findByErpKey(String erpKey, Pageable page);

	public List<DistributionStorageRfidTag> findByErpKeyAndStat(String erpKey, String stat, Pageable page);

	public List<DistributionStorageRfidTag> findByRfidTagIn(List<String> rfidTagList);

	@Modifying
	@Transactional
	@Query(value = "UPDATE distribution_storage_rfid_tag " +
		 		 	  "SET stat = :stat, upd_user_seq = :updUserSeq, upd_date = getDate() " +
		 		    "WHERE box_seq = :boxSeq", nativeQuery = true)
	public void updateBoxMappingTag(@Param("boxSeq") Long boxSeq, @Param("stat") String stat, @Param("updUserSeq") Long updUserSeq);

	@Query(value = "SELECT COUNT(*) " +
				     "FROM distribution_storage_rfid_tag " +
				    "WHERE box_seq = :boxSeq " +
				      "AND stat = :stat", nativeQuery = true)
	public Long countByBoxMappingTag(@Param("boxSeq") Long boxSeq, @Param("stat") String stat);

	@Query(value = "SELECT dsrt " +
					 "FROM DistributionStorageRfidTag dsrt " +
					"WHERE EXISTS ( " +
				   "SELECT tdrt.rfidTag " +
				     "FROM TempDistributionReleaseBox tdrb " +
				     "JOIN tdrb.styleList tdrs " +
				     "JOIN tdrs.tagList tdrt " +
			   		"WHERE dsrt.rfidTag = tdrt.rfidTag " +
					  "AND tdrb.tempBoxSeq = :tempBoxSeq)")
	public List<DistributionStorageRfidTag> existsReleaseTagList(@Param("tempBoxSeq") Long tempBoxSeq);

	@Modifying
	@Transactional
	@Query(value = "UPDATE distribution_storage_rfid_tag " +
		 	  "SET stat = 2, upd_date = getDate() " +
		    "WHERE rfid_tag = :rfidTag", nativeQuery = true)
	public void updateRfidStat(@Param("rfidTag") String rfidTag);

}
