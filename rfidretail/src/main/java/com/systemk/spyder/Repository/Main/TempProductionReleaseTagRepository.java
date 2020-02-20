package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.TempProductionReleaseTag;

public interface TempProductionReleaseTagRepository extends JpaRepository<TempProductionReleaseTag, String>{

	public List<TempProductionReleaseTag> findByTempHeaderSeq(Long tempHeaderSeq);
}
