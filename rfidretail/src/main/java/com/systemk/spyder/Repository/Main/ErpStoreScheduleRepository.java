package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;

public interface ErpStoreScheduleRepository extends JpaRepository<ErpStoreSchedule, Long>, JpaSpecificationExecutor<ErpStoreSchedule>{
	
	public void deleteByEndCompanyInfoCompanySeq(Long companySeq);
	
	@Query(value = "SELECT ISNULL(MAX(CONVERT(decimal(5), ess.complete_if_seq)), 0) + 1 " +
	   		 		 "FROM erp_store_schedule ess " +
	   		 		"WHERE ess.complete_date = :completeDate " +
	   		 		  "AND ess.end_company_seq = :endCompanySeq " + 
	   		 		  "AND ess.complete_seq = :completeSeq ", nativeQuery = true)
	public Long maxCompleteIf(@Param("completeDate") String completeDate, @Param("endCompanySeq") Long endCompanySeq, @Param("completeSeq") String completeSeq);

	public void save(List<? extends ErpStoreReturnSchedule> items);
}
