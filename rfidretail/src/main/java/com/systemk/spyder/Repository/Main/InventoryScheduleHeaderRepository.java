package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;

public interface InventoryScheduleHeaderRepository extends JpaRepository<InventoryScheduleHeader, Long>, JpaSpecificationExecutor<InventoryScheduleHeader>{

	public InventoryScheduleHeader findTop1ByCreateDateAndCompanyInfoCompanySeqOrderByWorkLineDesc(String createDate, Long companySeq);

}
