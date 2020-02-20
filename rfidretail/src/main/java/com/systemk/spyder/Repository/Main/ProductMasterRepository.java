package com.systemk.spyder.Repository.Main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.ProductMaster;

public interface ProductMasterRepository extends JpaRepository<ProductMaster, Long>, JpaSpecificationExecutor<ProductMaster> {

	public ProductMaster findByCreateDateAndCreateSeqAndCreateNo(String createDate, long createSeq, long createNo);
	
	public ProductMaster findByStyle(String style);
	
	public ProductMaster findByStyleAndColorAndSize(String style, String color, String size);
	
	public ProductMaster findByStyleAndColorAndSizeAndStatNot(String style, String color, String size, String stat);
	
	public ProductMaster findByErpKey(String erpKey);
	
	public Page<ProductMaster> findByErpKeyContaining(String erpKey, Pageable pageable);
	
	public Page<ProductMaster> findByStyleContaining(String style, Pageable pageable);
	
	public Page<ProductMaster> findByColorContaining(String color, Pageable pageable);
	
	public Page<ProductMaster> findBySizeContaining(String size, Pageable pageable);
	
}
