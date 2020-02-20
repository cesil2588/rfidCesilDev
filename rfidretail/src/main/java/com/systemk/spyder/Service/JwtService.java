package com.systemk.spyder.Service;

import java.util.Map;

import com.systemk.spyder.Entity.Main.UserInfo;

public interface JwtService {
	
	public String createToken(UserInfo userInfo) throws Exception;
	
	public Boolean checkToken(String jwt) throws Exception;
	
	public Map<String, Object> getTokenClaim(String jwt, String claim) throws Exception;
}
