package com.systemk.spyder.Repository.Main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.MailLog;

public interface MailLogRepository extends JpaRepository<MailLog, Long>, JpaSpecificationExecutor<MailLog>{
	
	public Page<MailLog> findByMailFromContaining(String search, Pageable pageable);
	
	public Page<MailLog> findByMailSendContaining(String search, Pageable pageable);
	
}
