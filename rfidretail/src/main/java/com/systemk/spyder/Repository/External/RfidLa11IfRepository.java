package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidLa11If;
import com.systemk.spyder.Entity.External.Key.RfidLa11IfKey;

public interface RfidLa11IfRepository extends JpaRepository<RfidLa11If, RfidLa11IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_la11_if SET la11_tryn = 'Y', la11_trdt = :la11Trdt WHERE la11_jmdt = :la11Jmdt AND la11_jmid = :la11Jmid AND la11_jmsr = :la11Jmsr", nativeQuery = true)
	public void updateTryn(@Param("la11Trdt") Date la11Trdt, @Param("la11Jmdt") String la11Jmdt, @Param("la11Jmid") String la11Jmid, @Param("la11Jmsr") BigDecimal la11Jmsr);
}
