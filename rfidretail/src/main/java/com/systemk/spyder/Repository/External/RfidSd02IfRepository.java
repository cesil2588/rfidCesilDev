package com.systemk.spyder.Repository.External;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidSd02If;
import com.systemk.spyder.Entity.External.Key.RfidSd02IfKey;

public interface RfidSd02IfRepository extends JpaRepository<RfidSd02If, RfidSd02IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_sd02_if SET sd02_tryn = 'Y', sd02_trdt = CONVERT(CHAR(19), getdate(), 120) WHERE sd02_orno = :sd02Orno AND sd02_lino = :sd02Lino", nativeQuery = true)
	public void updateTryn(@Param("sd02Orno") String sd02Orno, @Param("sd02Lino") String sd02Lino);
}
