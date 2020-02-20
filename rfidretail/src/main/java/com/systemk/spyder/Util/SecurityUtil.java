package com.systemk.spyder.Util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.systemk.spyder.Security.LoginUser;



public class SecurityUtil {

	public static LoginUser getCustomUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && !auth.getPrincipal().equals("anonymousUser")){
			return (LoginUser) auth.getPrincipal();
		} else {
			return null;
		}
	}
	
	public static boolean validCustomUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && !auth.getPrincipal().equals("anonymousUser")){
			return true;
		} else {
			return false;
		}
	}
}
