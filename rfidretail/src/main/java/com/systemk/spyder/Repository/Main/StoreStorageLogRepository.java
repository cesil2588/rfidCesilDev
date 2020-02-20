package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.StoreStorageLog;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface StoreStorageLogRepository extends JpaRepository<StoreStorageLog, Long>{
	
	@Query(nativeQuery = true, name = "storeLogCountAll")
	public CountModel findCountAll(@Param("seq") Long seq);
}