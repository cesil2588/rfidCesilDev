package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.DistributionStorage;

public interface DistributionStorageRepository extends JpaRepository<DistributionStorage, Long>, JpaSpecificationExecutor<DistributionStorage>{
	
	public DistributionStorage findByProductionStorageProductionStorageSeq(Long productionStorageSeq);

}
