package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, JpaSpecificationExecutor<RequestLog>{

}