package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.StoreStorage;

public interface StoreStorageRepository extends JpaRepository<StoreStorage, Long>, JpaSpecificationExecutor<StoreStorage>{

	public List<StoreStorage> findByCompanyInfoCompanySeq(Long companySeq); 
	
	public List<StoreStorage> findByDistributionStorageDistributionStorageSeq(Long distributionStorageSeq);
}
