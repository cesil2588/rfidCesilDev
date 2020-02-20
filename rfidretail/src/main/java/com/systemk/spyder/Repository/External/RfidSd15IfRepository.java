package com.systemk.spyder.Repository.External;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.External.RfidSd15If;
import com.systemk.spyder.Entity.External.Key.RfidSd15IfKey;

public interface RfidSd15IfRepository extends JpaRepository<RfidSd15If, RfidSd15IfKey>{
	
	@Transactional
	@Procedure(procedureName="UP_RFID_PDA_SD16")
	/*public List<?> callProc(@Param("@asJsdt") String jsdt, @Param("@anJssq") Long jssq, @Param("@asMggb") Long mggb, @Param("@asFrcd") String frcd, 
			@Param("@asFrco") String frco, @Param("@asBxno") Long bxno, @Param("@asMgdt") String mgdt, @Param("@mgSq") Long mgsq, 
			@Param("@al_ecod") Long ercod, @Param("@as_emsg") String esmsg);*/
	
	public int callProc(String jsdt, Long jssq, String mggb,  String frcd,  String frco, Long bxno);
	
	
}
