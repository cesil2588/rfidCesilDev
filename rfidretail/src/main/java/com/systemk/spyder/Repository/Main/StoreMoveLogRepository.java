package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.StoreMoveLog;

public interface StoreMoveLogRepository extends JpaRepository<StoreMoveLog, Long>, JpaSpecificationExecutor<StoreMoveLog>{
	
	public StoreMoveLog findTop1ByCreateDateAndBoxInfoStartCompanyInfoCompanySeqOrderByWorkLineDesc(String createDate, Long companySeq);
	
	public StoreMoveLog findByBoxInfoBarcode(String barcode);
	
	public boolean existsByBoxInfoBarcode(String barcode);

}
