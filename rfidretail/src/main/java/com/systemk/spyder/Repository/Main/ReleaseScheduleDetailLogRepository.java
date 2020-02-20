package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;

public interface ReleaseScheduleDetailLogRepository extends JpaRepository<ReleaseScheduleDetailLog, Long>{

	public List<ReleaseScheduleDetailLog> findByErpStoreScheduleSeq(Long seq);
}
