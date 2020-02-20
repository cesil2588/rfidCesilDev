package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;

public interface TempProductionReleaseHeaderRepository extends JpaRepository<TempProductionReleaseHeader, Long>{

	public List<TempProductionReleaseHeader> findByUserSeq(Long userSeq);
} 
