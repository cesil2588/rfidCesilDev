package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.BartagMasterLog;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface BartagMasterLogRepository extends JpaRepository<BartagMasterLog, Long>{

	public List<BartagMasterLog> findByBartagMasterBartagSeq(Long bartagSeq);
	
	public void deleteByBartagMasterBartagSeq(Long bartagSeq);
	
	@Query(nativeQuery = true, name = "bartagLogCountAll")
	public CountModel findCountAll(@Param("seq") Long seq);
}
		