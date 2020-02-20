package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.RoleInfo;

public interface RoleInfoRepository extends JpaRepository<RoleInfo, String>, JpaSpecificationExecutor<RoleInfo>{
	
}
