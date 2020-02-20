package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.BatchTrigger;

public interface BatchTriggerRepository extends JpaRepository<BatchTrigger, Long>, JpaSpecificationExecutor<BatchTrigger>{

	public BatchTrigger findByExplanatoryAndTypeAndStat(String explanatory, String type, String stat);

}
