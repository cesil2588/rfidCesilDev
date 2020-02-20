package com.systemk.spyder.Repository.External;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.Response.ProcedureResult;
import com.systemk.spyder.Entity.External.RfidSd01If;
import com.systemk.spyder.Entity.External.Key.RfidSd01IfKey;

public interface RfidSd01IfRepository extends JpaRepository<RfidSd01If, RfidSd01IfKey>{

	@Transactional
	@Procedure(procedureName="UP_RFID_PDA_SD01")
	public ProcedureResult callProc(@Param("@as_endt") String endt, @Param("@an_ensq") Long ensq, @Param("@as_frcd") String frcd,
			@Param("@as_frco")  String frco);
}
