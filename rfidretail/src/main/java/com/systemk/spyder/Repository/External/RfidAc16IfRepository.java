package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidAc16If;

public interface RfidAc16IfRepository extends JpaRepository<RfidAc16If, Long>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_ac16_if SET ac16_tryn = 'Y', ac16_trdt = :ac16Trdt WHERE ac16_crdt = :ac16Crdt AND ac16_crsq = :ac16Crsq AND ac16_crno = :ac16Crno", nativeQuery = true)
	public void updateTryn(@Param("ac16Trdt") Date ac16Trdt, @Param("ac16Crdt") String ac16Crdt, @Param("ac16Crsq") BigDecimal ac16Crsq, @Param("ac16Crno") BigDecimal ac16Crno);
}
