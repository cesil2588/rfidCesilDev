package com.systemk.spyder.Repository.External;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.External.RfidLd02If;
import com.systemk.spyder.Entity.External.Key.RfidLd02IfKey;

public interface RfidLd02IfRepository extends JpaRepository<RfidLd02If, RfidLd02IfKey>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE rfid_ld02_if SET ld02_tryn = 'Y', ld02_trdt = :ld02Trdt " +
				    "WHERE ld02_mgdt = :ld02Mgdt AND ld02_mggb = :ld02Mggb AND ld02_mgsq = :ld02Mgsq " +
				      "AND ld02_mjcd = :ld02Mjcd AND ld02_mjco = :ld02Mjco AND ld02_cgcd = :ld02Cgcd " +
				      "AND ld02_cgco = :ld02Cgco AND ld02_styl = :ld02Styl AND ld02_stcd = :ld02Stcd " +
				      "AND ld02_bncd = :ld02Bncd AND ld02_seqn = :ld02Seqn ", nativeQuery = true)
	public void updateTryn(@Param("ld02Trdt") String ld02Trdt,
						   @Param("ld02Mgdt") String ld02Mgdt,
						   @Param("ld02Mggb") String ld02Mggb,
						   @Param("ld02Mgsq") String ld02Mgsq,
						   @Param("ld02Mjcd") String ld02Mjcd,
						   @Param("ld02Mjco") String ld02Mjco,
						   @Param("ld02Cgcd") String ld02Cgcd,
						   @Param("ld02Cgco") String ld02Cgco,
						   @Param("ld02Styl") String ld02Styl,
						   @Param("ld02Stcd") String ld02Stcd,
						   @Param("ld02Bncd") String ld02Bncd,
						   @Param("ld02Seqn") String ld02Seqn);
}
