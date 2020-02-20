package com.systemk.spyder.Repository.External;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Entity.External.Key.RfidIb01IfKey;

public interface RfidIb01IfRepository extends JpaRepository<RfidIb01If, RfidIb01IfKey> {

	@Query(value = "SELECT rli.* " +
		 		 "FROM rfid_lb01_if rli " +
		 		"WHERE rli.lb01_bxno = :barcode", nativeQuery = true)
	public List<RfidIb01If> findRfidIb01If(@Param("barcode") String barcode);
	
	/*@Query(value = "SELECT lb01_ipdt, lb01_bxno, lb01_ipsno, lb01_emgb, "
					+ "lb01_prod, lb01_cgcd, "
					+ "lb01_styl, lb01_it06, lb01_it07, "
					+ "lb01_jjch, lb01_ipqt, lb01_endt "
					+ "lb01_cgco, lb01_prod, lb01_prco, lb01_stcd, lb01_tryn, lb01_stat, lb01_time, lb01_ipsq, lb01_ipsr, lb01_bigo, lb01_blno, lb01_endt "
					+ "FROM rfid_lb01_if "
					+ "WHERE lb01_ipdt BETWEEN :startDate AND :endDate", nativeQuery = true)*/
	
		@Query(value = "SELECT rli.lb01_ipdt AS lb01_ipdt, rli.lb01_bxno AS lb01_bxno, rli.lb01_ipsno AS lb01_ipsno, rli.lb01_emgb AS lb01_emgb, "
						+ "srzi.za11_grnm AS lb01_prod, erzi.za11_grnm AS lb01_cgcd, rli.lb01_styl AS lb01_styl, rli.lb01_it06 AS lb01_it06, "
						+ "rli.lb01_it07 AS lb01_it07, rli.lb01_jjch AS lb01_jjch, rli.lb01_ipqt AS lb01_ipqt, rli.lb01_endt AS lb01_endt, "
						+ "lb01_cgco, lb01_prod, lb01_prco, lb01_stcd, lb01_tryn, lb01_stat, lb01_time, lb01_ipsq, lb01_ipsr, lb01_bigo, lb01_blno, lb01_endt "
						+ "FROM rfid_lb01_if rli "
						+ "INNER JOIN rfid_za11_if srzi "
						+ "ON rli.lb01_cgcd = srzi.za11_gras "
						+ "INNER JOIN rfid_za11_if erzi "
						+ "ON rli.lb01_prod = erzi.za11_gras "
						+ "WHERE rli.lb01_ipdt BETWEEN :startDate AND :endDate", nativeQuery = true)
	public List<RfidIb01If> findStorageCompleteBoxInfo(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	
}
