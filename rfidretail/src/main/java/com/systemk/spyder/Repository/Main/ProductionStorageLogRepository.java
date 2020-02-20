package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageLog;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface ProductionStorageLogRepository extends JpaRepository<ProductionStorageLog, Long>{

	public void deleteByProductionStorage(ProductionStorage productionStorage);
	
	public List<ProductionStorageLog> findByProductionStorage(ProductionStorage productionStorage);
	
	@Query(nativeQuery = true, name = "productionLogCountAll")
	public CountModel findCountAll(@Param("seq") Long seq);
}
