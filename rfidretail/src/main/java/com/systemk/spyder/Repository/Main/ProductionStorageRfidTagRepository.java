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

import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;

public interface ProductionStorageRfidTagRepository extends JpaRepository<ProductionStorageRfidTag, Long>, JpaSpecificationExecutor<ProductionStorageRfidTag>{
	
	public Page<ProductionStorageRfidTag> findByProductionStorageSeq(Long productionStorageSeq, Pageable pageable);

	public Page<ProductionStorageRfidTag> findByProductionStorageSeqAndBarcodeContaining(Long productionStorageSeq, String search, Pageable pageable);
	
	public Page<ProductionStorageRfidTag> findByProductionStorageSeqAndRfidTagContaining(Long productionStorageSeq, String search, Pageable pageable);
	
	public Page<ProductionStorageRfidTag> findByProductionStorageSeqAndStat(Long productionStorageSeq, String stat, Pageable pageable);
	
	public List<ProductionStorageRfidTag> findByProductionStorageSeq(Long productionStorageSeq);
	
	public List<ProductionStorageRfidTag> findByProductionStorageSeqAndStat(Long productionStorageSeq, String stat);
	
	public List<ProductionStorageRfidTag> findTop1000ByProductionStorageSeqAndStat(Long productionStorageSeq, String stat);
	
	public List<ProductionStorageRfidTag> findByCustomerCdAndStat(String cutomerCd, String stat, Pageable pageable);
	
	public Long countByProductionStorageSeqAndStat(Long productionStorageSeq, String stat);
	
	public Long countByProductionStorageSeq(Long productionStorageSeq);
	
	public Long countByErpKeyAndStat(String erpKey, String stat);
	
	public ProductionStorageRfidTag findByRfidTag(String rfidTag);
	
	public ProductionStorageRfidTag findByRfidTagAndStat(String rfidTag, String stat);
	
	public Long countByRfidTagAndStat(String rfidTag, String stat);
	
	public Page<ProductionStorageRfidTag> findByBoxSeq(Long boxSeq, Pageable pageable);
	
	public Page<ProductionStorageRfidTag> findByStat(String stat, Pageable pageable);
	
	public Page<ProductionStorageRfidTag> findByStatAndCustomerCd(String stat, String customerCd, Pageable pageable);
	
	public List<ProductionStorageRfidTag> findByBoxSeq(Long boxSeq);
	
	public void deleteByProductionStorageSeq(Long productionStorageSeq);
	
	public List<ProductionStorageRfidTag> findByCustomerCdAndStat(String customerCd, String stat);
	
	public List<ProductionStorageRfidTag> findByProductionStorageSeqAndCustomerCdAndStat(Long seq, String customerCd, String stat);
	
	public ProductionStorageRfidTag findTopByBarcode(String barcode);
	
	public ProductionStorageRfidTag findByErpKeyAndRfidSeq(String erpKey, String rfidSeq);
	
    public List<ProductionStorageRfidTag> findByRfidTagIn(List<String> rfidTagList);
    
    @Modifying
	@Transactional
	@Query(value = "UPDATE production_storage_rfid_tag " +
		 		 	  "SET stat = '3', box_seq = :boxSeq, box_barcode = :boxBarcode, upd_user_seq = :updUserSeq, upd_date = getdate() " +
		 		    "WHERE rfid_tag IN (:rfidTagList)", nativeQuery = true)
	public void updateBoxMappingTag(@Param("rfidTagList") List<String> rfidTagList, @Param("boxSeq") Long boxSeq, @Param("boxBarcode") String boxBarcode, @Param("updUserSeq") Long updUserSeq);
}
