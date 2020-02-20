package com.systemk.spyder.Repository.Main;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;

public interface ErpStoreReturnScheduleRepository extends JpaRepository<ErpStoreReturnSchedule, Long>, JpaSpecificationExecutor<ErpStoreReturnSchedule>{
	
	public boolean existsByReturnOrderNo(String returnOrderNo);
	
	public ErpStoreReturnSchedule findByReturnOrderNoAndReturnOrderLiNo(String returnOrderNo, String returnOrderLiNo);
	
	public List<ErpStoreReturnSchedule> findByReturnOrderNo(String returnOrderNo);

}
