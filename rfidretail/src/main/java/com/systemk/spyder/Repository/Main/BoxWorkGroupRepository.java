package com.systemk.spyder.Repository.Main;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.BoxWorkGroup;

public interface BoxWorkGroupRepository extends JpaRepository<BoxWorkGroup, Long>, JpaSpecificationExecutor<BoxWorkGroup>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE box_work_group " +
		 		 	  "SET stat = :stat, upd_user_seq = :updUserSeq, upd_date = getDate() " +
		 		    "WHERE box_work_group_seq = :boxWorkGroupSeq", nativeQuery = true)
	public void updateUpdUser(@Param("boxWorkGroupSeq") Long boxWorkGroupSeq, @Param("stat") String stat, @Param("updUserSeq") Long updUserSeq);
}
