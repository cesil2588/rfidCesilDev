package com.systemk.spyder.Repository.External;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.External.RfidTestIf;

public interface RfidTestIfRepository extends JpaRepository<RfidTestIf, Long>{
	
	@Transactional
	@Procedure(procedureName="test_realErp")
	void callProcedure(String testName, int testPw);
}
