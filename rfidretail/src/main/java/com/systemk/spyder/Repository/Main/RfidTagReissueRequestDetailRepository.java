package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;

public interface RfidTagReissueRequestDetailRepository extends JpaRepository<RfidTagReissueRequestDetail, Long>{

	public RfidTagReissueRequestDetail findByRfidTag(String rfidTag);
	
	public RfidTagReissueRequestDetail findByRfidTagAndStat(String rfidTag, String stat);
	
	public List<RfidTagReissueRequestDetail> findAllByRfidTag(String rfidTag);
	
	public List<RfidTagReissueRequestDetail> findByStat(String stat);
	
	public List<RfidTagReissueRequestDetail> findByProductionStorageBartagMasterBartagSeq(Long bartagSeq);
	
	public List<RfidTagReissueRequestDetail> findByProductionStorageProductionStorageSeq(Long productionStorageSeq);
	
	public void deleteByRfidTagReissueRequestRfidTagReissueRequestSeq(Long rfidTagReissueRequestSeq);
	
	public List<RfidTagReissueRequestDetail> findByColorIsNull();
}
