package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.UserInfo;


public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
	
	public UserInfo findByUserId(String userId);
	
	public UserInfo findByUserIdAndUseYn(String userId, String useYn);
	
	public UserInfo findByUserIdAndUseYnAndCheckYn(String userId, String useYn, String checkYn);
	
	public UserInfo findByUserEmailInfoEmail(String email);
	
	public UserInfo findByUserIdAndUserEmailInfoEmail(String userId, String email);

	public Long countByUserId(String userId);
	
	public Long countByUserEmailInfoEmail(String email);
	
	public Long countByUserSeqNotAndUserEmailInfoEmail(Long userSeq, String email);
	
	public Page<UserInfo> findByUserIdContaining(String search, Pageable pageable);
	
//	public Page<UserInfo> findByEmailContaining(String search, Pageable pageable);
	
	public Page<UserInfo> findByCompanyInfoRoleInfoRoleContaining(String search, Pageable pageable);
	
	public Page<UserInfo> findByCompanyInfoNameContaining(String search, Pageable pageable);
	
	public List<UserInfo> findByCompanyInfoRoleInfoRole(String role);
	
	public List<UserInfo> findByCompanyInfoCustomerCode(String customerCode);
	
	public UserInfo findTop1ByCompanyInfoCustomerCode(String customerCode);
	
	public UserInfo findTop1ByCompanyInfoRoleInfoRole(String role);
	
	public List<UserInfo> findByCompanyInfoCompanySeq(Long companySeq);
}
