package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.AppInfo;

public interface AppInfoRepository extends JpaRepository<AppInfo, Long>, JpaSpecificationExecutor<AppInfo>{
	
	public AppInfo findByRepresentYn(String representYn);
	
	public AppInfo findByRepresentYnAndType(String representYn, String type);
	
	public Long countByRepresentYn(String representYn);
	
	public Long countByRepresentYnAndType(String representYn, String type);
	
	public Long countByRepresentYnAndTypeAndAppSeqNotIn(String representYn, String type, Long appSeq);
	
}
