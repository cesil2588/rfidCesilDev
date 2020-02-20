package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.CompanyInfo;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, JpaSpecificationExecutor<CompanyInfo> {
	
	public Page<CompanyInfo> findByNameContaining(String search, Pageable pageable);

	public Page<CompanyInfo> findByTelNoContaining(String search, Pageable pageable);
	
	public CompanyInfo findByCustomerCodeAndCornerCode(String customerCode, String cornerCode);
	
	public CompanyInfo findByCustomerCode(String customerCode);
	
	public CompanyInfo findByCode(String code);
	
	public CompanyInfo findTopByOrderByCodeDesc();
	
	public CompanyInfo findTopByTypeOrderByCodeDesc(String type);
	
	public List<CompanyInfo> findByTypeOrderByNameAsc(String type);
	
	public List<CompanyInfo> findByRoleInfoRole(String role);
	
	public CompanyInfo findTopByRoleInfoRole(String role);
	
	public List<CompanyInfo> findAllByOrderByNameAsc();
	
	public CompanyInfo findByNameContaining(String name);
	
	public CompanyInfo findTopByCustomerCodeIsNullOrderByCodeDesc();
	
	@Query(value = "SELECT * FROM company_info WHERE company_seq = (SELECT company_seq FROM user_info WHERE user_id = :userId)", nativeQuery = true)
	public CompanyInfo findCustomerAndCornerCode(@Param("userId") String userId);
}
