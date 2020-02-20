package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidAa06If;
import com.systemk.spyder.Entity.External.Key.RfidAa06IfKey;

public interface RfidAa06IfRepository extends JpaRepository<RfidAa06If, RfidAa06IfKey>{

	@Query(value = "SELECT rai.* " +
	 		 		"FROM rfid_aa06_if rai " +
	 		 	   "WHERE rai.aa06_styl = :style AND rai.aa06_it06 = :color AND rai.aa06_it07 = :size AND rai.aa06_jjch = :orderDegree", nativeQuery = true)
	public RfidAa06If findData(@Param("style") String style, @Param("color") String color, @Param("size") String size, @Param("orderDegree") String orderDegree);

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_aa06_if SET aa06_tryn = 'Y', aa06_trdt = :aa06Trdt WHERE aa06_crdt = :aa06Crdt AND aa06_crsq = :aa06Crsq AND aa06_crno = :aa06Crno", nativeQuery = true)
	public void updateTryn(@Param("aa06Trdt") Date aa06Trdt, @Param("aa06Crdt") String aa06Crdt, @Param("aa06Crsq") BigDecimal aa06Crsq, @Param("aa06Crno") BigDecimal aa06Crno);
}
