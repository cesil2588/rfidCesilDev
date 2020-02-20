package com.systemk.spyder.Repository.External;

import com.systemk.spyder.Entity.External.Key.RfidLe10IfKey;
import com.systemk.spyder.Entity.External.RfidLe10If;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

public interface RfidLe10IfRepository extends JpaRepository<RfidLe10If, RfidLe10IfKey> {

	@Modifying
	@Transactional
	@Query(value = "update rfid_Le10_if set le10_tryn = 'Y', le10_trdt = :updDate " +
					"where le10_chdt = :erpCreateDate " +
					"and le10_chsq = :erpReleaseSeq " +
					"and le10_gras = :customerCode " +
					"and le10_grco = :cornerCode " +
					"and le10_styl = :style " +
					"and le10_stcd = :anotherStyle", nativeQuery = true)
	public int batchUpdate(@Param("updDate") Date updDate,
						   @Param("erpCreateDate") String erpCreateDate,
						   @Param("erpReleaseSeq") BigDecimal erpReleaseSeq,
						   @Param("customerCode") String customerCode,
						   @Param("cornerCode") String cornerCode,
						   @Param("style") String style,
						   @Param("anotherStyle") String anotherStyle);
}
