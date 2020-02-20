package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.RfidTagMaster;

public interface RfidTagMasterRepository extends JpaRepository<RfidTagMaster, Long>, JpaSpecificationExecutor<RfidTagMaster>{

	public RfidTagMaster findTopByBartagSeq(long seq);

	public RfidTagMaster findByRfidTag(String rfidTag);

	public List<RfidTagMaster> findByRfidTagLike(String rfidTag);

	public RfidTagMaster findTopByErpKeyAndSeasonAndOrderDegreeAndCustomerCdAndRfidSeq(String erpKey, String season, String orderDegree, String customerCd, String rfidSeq);

	public List<RfidTagMaster> findByBartagSeq(long seq);

	public List<RfidTagMaster> findByBartagSeqAndStat(long bartagSeq, String stat);

	public Page<RfidTagMaster> findByBartagSeqAndStat(long bartagSeq, String stat, Pageable pageable);

	public Page<RfidTagMaster> findByBartagSeq(Long bartagSeq, Pageable pageable);

	public Page<RfidTagMaster> findByBartagSeqAndPublishLocationContaining(Long bartagSeq, String search, Pageable pageable);

	public Page<RfidTagMaster> findByBartagSeqAndPublishRegDateContaining(Long bartagSeq, String search, Pageable pageable);

	public Page<RfidTagMaster> findByBartagSeqAndPublishDegreeContaining(Long bartagSeq, String search, Pageable pageable);

	public Page<RfidTagMaster> findByBartagSeqAndRfidSeqContaining(Long bartagSeq, String search,Pageable pageable);

	public Long countByBartagSeqAndStat(Long bartagSeq, String Stat);

	public Long countByBartagSeq(Long bartagSeq);

	public RfidTagMaster findByCreateDateAndSeqAndLineSeqAndRfidSeqAndStatAndRfidTagIsNull(String createDate, Long seq, Long lineSeq, String rfidSeq, String stat);

	public RfidTagMaster findByBartagSeqAndRfidSeq(String bartagSeq, String rfidSeq);

	public void deleteByBartagSeq(Long bartagSeq);

	public List<RfidTagMaster> findByErpKeyAndRfidSeqLike(String erpKey, String rfidSeq);

	@Query(value="SELECT bartag_seq FROM rfid_tag_master WHERE rfid_tag = :rfidTag", nativeQuery = true)
	public Long findBartagSeqByRfidTag(@Param("rfidTag") String rfidTag);

	@Query(value="SELECT rfid_tag FROM rfid_tag_master WHERE bartag_seq "
				+ "IN (SELECT bartag_seq FROM bartag_master WHERE style = :style AND "
				+ "color = :color AND size = :size AND production_company_seq = :companySeq)", nativeQuery = true)
	public List<String> findRfidTagList(@Param("companySeq") Long companySeq, @Param("style") String style, @Param("color") String color, @Param("size") String size);

	@Query(value="SELECT erp_key FROM rfid_tag_master WHERE rfid_tag = :rfidTag", nativeQuery = true)
	public String findErpkeyByRfidTag(@Param("rfidTag") String rfidTag);

	public RfidTagMaster findTopByErpKeyAndRfidSeq(String erpKey, String rfidSeq);
}