package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;

public interface RfidAc18IfRepository extends JpaRepository<RfidAc18If, RfidAc18IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_ac18_if SET ac18_tryn = 'Y', ac18_trdt = :ac18Trdt WHERE ac18_crdt = :ac18Crdt AND ac18_crsq = :ac18Crsq AND ac18_crno = :ac18Crno ", nativeQuery = true)
	public void updateTryn(@Param("ac18Trdt") Date ac18Trdt, @Param("ac18Crdt") String ac18Crdt, @Param("ac18Crsq") BigDecimal ac18Crsq, @Param("ac18Crno") BigDecimal ac18Crno);
}
