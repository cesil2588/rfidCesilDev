package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.ParentCodeInfo;

public interface ParentCodeInfoRepository extends JpaRepository<ParentCodeInfo, Long>{

	public List<ParentCodeInfo> findAllByOrderBySortAsc();
	
	public ParentCodeInfo findByCodeValue(String codeValue);
}
