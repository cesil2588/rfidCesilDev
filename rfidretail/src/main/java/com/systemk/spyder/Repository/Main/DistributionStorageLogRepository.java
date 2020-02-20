package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.DistributionStorageLog;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface DistributionStorageLogRepository extends JpaRepository<DistributionStorageLog, Long>{

	@Query(nativeQuery = true, name = "distributionLogCountAll")
	public CountModel findCountAll(@Param("seq") Long seq);
}
