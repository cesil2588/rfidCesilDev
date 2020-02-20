package com.systemk.spyder.Repository.External;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidLf01If;
import com.systemk.spyder.Entity.External.Key.RfidLf01IfKey;

public interface RfidLf01IfRepository extends JpaRepository<RfidLf01If, RfidLf01IfKey>{

	@Query(value = "SELECT rli.* " +
		 		 "FROM rfid_lf01_if rli " +
		 		"WHERE rli.lf01_orno = :barcode", nativeQuery = true)
	public List<RfidLf01If> findRfidLf01If(@Param("barcode") String barcode);
	
}
