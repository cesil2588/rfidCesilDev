package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidLa12If;
import com.systemk.spyder.Entity.External.Key.RfidLa12IfKey;

public interface RfidLa12IfRepository extends JpaRepository<RfidLa12If, RfidLa12IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_la12_if SET la12_tryn = 'Y', la12_trdt = :la12Trdt WHERE la12_jmdt = :la12Jmdt AND la12_jmid = :la12Jmid AND la12_jmsr = :la12Jmsr ", nativeQuery = true)
	public void updateTryn(@Param("la12Trdt") Date la12Trdt, @Param("la12Jmdt") String la12Jmdt, @Param("la12Jmid") String la12Jmid, @Param("la12Jmsr") BigDecimal la12Jmsr);
}
