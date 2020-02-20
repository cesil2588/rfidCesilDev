package com.systemk.spyder.Repository.External;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidZa11If;

public interface RfidZa11IfRepository extends JpaRepository<RfidZa11If, Long>{

	public List<RfidZa11If> findByZa11TrynAndZa11TrdtIsNull(String stat);

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_za11_if SET za11_tryn = 'Y', za11_trdt = :za11Trdt WHERE za11_crdt = :za11Crdt AND za11_crno = :za11Crno", nativeQuery = true)
	public void updateTryn(@Param("za11Trdt") Date za11Trdt, @Param("za11Crdt") String za11Crdt, @Param("za11Crno") BigDecimal za11Crno);
}
