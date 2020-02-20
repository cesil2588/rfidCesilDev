package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.BoxInfo;

public interface BoxInfoRepository extends JpaRepository<BoxInfo, Long>, JpaSpecificationExecutor<BoxInfo>{
	
	public Long countByBarcode(String barcode);
	
	public BoxInfo findByBarcode(String barcode);
	
	public List<BoxInfo> findByBarcodeLike(String barcode);
	
	public BoxInfo findByBoxSeqAndStat(Long boxSeq, String stat);
	
	public BoxInfo findByBarcodeAndStat(String barcode, String stat);
	
	public Page<BoxInfo> findByBoxNumContaining(String search, Pageable pageable);

	public List<BoxInfo> findByBoxWorkGroupSeq(Long boxWorkSeq);
	
	public List<BoxInfo> findByStat(String stat);
	
	public List<BoxInfo> findByStartCompanyInfoCompanySeq(Long companySeq);
	
	public List<BoxInfo> findByEndCompanyInfoCompanySeq(Long companySeq);
	
	public List<BoxInfo> findByCreateDateAndStartCompanyInfoCompanySeq(String createDate, Long companySeq);
	
	public List<BoxInfo> findTop1000ByCreateDateAndStartCompanyInfoCompanySeq(String createDate, Long companySeq);
	
	public void deleteByBarcode(String barcode);
	
	public boolean existsByBarcode(String barcode);
	
	public boolean existsByBarcodeAndBoxNum(String barcode, String boxNum);
}
