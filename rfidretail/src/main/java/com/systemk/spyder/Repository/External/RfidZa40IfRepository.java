package com.systemk.spyder.Repository.External;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidZa40If;
import com.systemk.spyder.Entity.External.Key.RfidZa40IfKey;

public interface RfidZa40IfRepository extends JpaRepository<RfidZa40If, RfidZa40IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_za40_if SET za40_tryn = 'Y', za40_trdt = :za40Trdt WHERE za40_gras = :za40Gras AND za40_corn = :za40Corn", nativeQuery = true)
	public void updateTryn(@Param("za40Trdt") Date za40Trdt, @Param("za40Gras") String za40Gras, @Param("za40Corn") String za40Corn);
}
