package com.systemk.spyder.Repository.Main;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Entity.Main.BartagMaster;

public interface BartagMasterRepository extends JpaRepository<BartagMaster, Long>, JpaSpecificationExecutor<BartagMaster>{
	
	public List<BartagMaster> findByGenerateSeqYnAndStat(String generateSeqYn, String stat);
	
	public BartagMaster findByBartagSeq(Long bartagSeq);
	
	public Page<BartagMaster> findByCreateDateBetween(String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findByProductYyContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findByProductYyContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findByProductSeasonContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findByProductSeasonContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findByErpKeyContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findByErpKeyContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findByStyleContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findByStyleContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findByProductionCompanyInfoNameContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findByProductionCompanyInfoNameContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public Page<BartagMaster> findBySizeContaining(String search, Pageable pageable);
	
	public Page<BartagMaster> findBySizeContainingAndCreateDateBetween(String search, String startDate, String endDate, Pageable pageable);
	
	public List<BartagMaster> findByCreateDate(String createDate);
	
	public List<BartagMaster> findByErpKey(String erpKey);
	
	public List<BartagMaster> findByErpKeyAndBartagSeqNot(String erpKey, Long bartagSeq);
	
	public Page<BartagMaster> findByErpKeyAndOrderDegree(String erpKey, String orderDegree, Pageable pageable);
	
	public List<BartagMaster> findByErpKeyAndOrderDegree(String erpKey, String orderDegree);
	
	public List<BartagMaster> findByErpKeyAndOrderDegreeAndStat(String erpKey, String orderDegree, String stat);
	
	public BartagMaster findTopByErpKeyAndOrderDegreeOrderByAdditionOrderDegreeDesc(String erpKey, String orderDegree);
	
	public BartagMaster findTopByErpKey(String erpKey);
	
	public BartagMaster findByCreateDateAndSeqAndLineSeq(String createDate, Long seq, Long lineSeq);
	
	public List<BartagMaster> findByStat(String stat);
	
	public List<BartagMaster> findByOrderByStartRfidSeq(Specifications<BartagMaster> spec);
	
	public List<BartagMaster> findByRegDateBetweenAndProductionCompanyInfoCompanySeq(Date startDate, Date endDate, Long companySeq);
	
	public List<BartagMaster> findByProductionCompanyInfoIsNull();
	
	public List<BartagMaster> findByStyleAndColorAndSize(String style, String color, String size);
	
	@Query(value = "SELECT ISNULL(MAX(bm.end_rfid_seq), 0) FROM bartag_master bm WHERE bm.erp_key = :erpKey AND bm.bartag_seq != :bartagSeq ", nativeQuery = true)
	public Long findMaxEndRfidSeq(@Param("erpKey") String erpkey, @Param("bartagSeq") Long bartagSeq);
	
}
