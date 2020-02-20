package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.CodeInfo;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, Long>{
	
}
