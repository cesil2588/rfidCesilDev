package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;

public interface RfidTagReissueRequestRepository extends JpaRepository<RfidTagReissueRequest, Long>, JpaSpecificationExecutor<RfidTagReissueRequest>{

	public RfidTagReissueRequest findTop1ByCreateDateAndCompanyInfoCompanySeqAndTypeOrderByWorkLineDesc(String createDate, Long companySeq, String type);
	
	public List<RfidTagReissueRequest> findByCreateDateAndCompanyInfoCompanySeqAndConfirmYn(String createDate, Long companySeq, String confirmYn);
	
	public List<RfidTagReissueRequest> findByCreateDateAndCompanyInfoCompanySeq(String createDate, Long companySeq);
}
