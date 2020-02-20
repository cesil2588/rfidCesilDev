package com.systemk.spyder.Repository.External;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.External.RfidSd06If;
import com.systemk.spyder.Entity.External.Key.RfidSd06IfKey;

public interface RfidSd06IfRepository extends JpaRepository<RfidSd06If, RfidSd06IfKey>{

	@Modifying
	@Transactional
	@Query(value="UPDATE rfid_sd06_if SET sd06_tryn='Y', sd06_trdt = :trdt WHERE sd06_iddt = :regDate AND sd06_idsq = :orderSeq AND sd06_idsr = :orderSerial", nativeQuery = true)
	public void updateTryn(@Param("trdt") Date trdt, @Param("regDate") String regDate, @Param("orderSeq") Long orderSeq, @Param("orderSerial") Long orderSerial);
}
