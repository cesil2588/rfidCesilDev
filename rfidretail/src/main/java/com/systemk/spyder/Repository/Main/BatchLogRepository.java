package com.systemk.spyder.Repository.Main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.BatchLog;

public interface BatchLogRepository extends JpaRepository<BatchLog, Long>, JpaSpecificationExecutor<BatchLog>{

	public Page<BatchLog> findByJobNameContaining(String search, Pageable pageable);
	
	public Page<BatchLog> findByStatusContaining(String search, Pageable pageable);
}
