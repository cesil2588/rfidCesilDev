package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.systemk.spyder.Entity.Main.UserNoti;

public interface UserNotiRepository extends JpaRepository<UserNoti, Long>, JpaSpecificationExecutor<UserNoti> {
	
	public List<UserNoti> findByNotiUserInfoUserSeqAndCheckYnOrderByUserNotiSeqDesc(Long userSeq, String checkYn);
	
	public List<UserNoti> findTop10ByNotiUserInfoUserSeqAndCheckYnOrderByUserNotiSeqDesc(Long userSeq, String checkYn);

}
