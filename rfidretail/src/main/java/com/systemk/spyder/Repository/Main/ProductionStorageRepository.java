package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.ProductionStorage;

public interface ProductionStorageRepository extends JpaRepository<ProductionStorage, Long>, JpaSpecificationExecutor<ProductionStorage>{

	public Page<ProductionStorage> findByBartagMasterProductYyContaining(String productYy, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterProductSeasonContaining(String productSeason, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterStyleContaining(String style, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterProductYyContaining(String CustomerCd, String productYy, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterProductSeasonContaining(String CustomerCd, String productSeason, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterStyleContaining(String CustomerCd, String style, Pageable pageable);
	
	public Page<ProductionStorage> findByBartagMasterProductionCompanyInfoCustomerCode(String CustomerCd, Pageable pageable);
	
	public List<ProductionStorage> findByBartagMasterProductionCompanyInfoCustomerCode(String CustomerCd);
	
	public ProductionStorage findByBartagMasterStyleAndBartagMasterColorAndBartagMasterSize(String style, String color, String size);
	
	public ProductionStorage findByBartagMasterBartagSeq(Long bartagSeq);
	
	public List<ProductionStorage> findByCompanyInfoCompanySeq(Long companySeq); 
	
}
