package com.systemk.spyder.Repository.External;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.External.RfidSd14If;
import com.systemk.spyder.Entity.External.Key.RfidSd14IfKey;


public interface RfidSd14IfRepository extends JpaRepository<RfidSd14If, RfidSd14IfKey>{

	@Modifying
	@Transactional
	@Query(value="UPDATE rfid_sd14_if SET sd14_tryn='Y', sd14_trdt = :trdt WHERE sd14_jsdt = :regDate AND sd14_jssq = :erpReturnNo AND sd14_mggb = :returnType "
				+ "AND sd14_frcd = :fromCustomerCode AND sd14_frco = :fromCornerCode AND sd14_styl = :returnStyle AND sd14_stcd = :anotherCode", nativeQuery = true)
	public void updateTryn(@Param("trdt") Date trdt, @Param("regDate") String regDate, @Param("erpReturnNo") Long erpReturnNo, @Param("returnType") String returnType, @Param("fromCustomerCode") String fromCustomerCode, 
							@Param("fromCornerCode") String fromCornerCode, @Param("returnStyle") String returnStyle, @Param("anotherCode") String anotherCode);
}
