package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.UserEmailInfo;

public interface UserEmailInfoRepository extends JpaRepository<UserEmailInfo, Long>{
	
	public Long countByEmail(String email);

}
