package com.systemk.spyder.Security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Entity.Main.MenuMapping;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.UserInfoRepository;

@Transactional
@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		
		UserInfo user = userInfoRepository.findByUserIdAndUseYn(userId, "Y");
		
		if(user == null){
			throw new BadCredentialsException("3000");
		}
		
		if(user.getUseYn().equals("N")){
			throw new BadCredentialsException("3001");
		}
		
		if(user.getCheckYn().equals("N")){
			throw new BadCredentialsException("3002");
		}
		
		user.getCompanyInfo().getRoleInfo().getMenuMapping().size();
		
		for(MenuMapping menu : user.getCompanyInfo().getRoleInfo().getMenuMapping()) {
			menu.getParentMenu().getChildMenu().size();
		}

		user.setLastLoginDate(new Date());
		userInfoRepository.save(user);
		
		List<GrantedAuthority> authorities = buildUserAuthority(user);
		
		return buildUserForAuthentication(user, authorities);
	}
	
	private User buildUserForAuthentication(UserInfo user,
		List<GrantedAuthority> authorities) {
		
		return new LoginUser(user.getUserId(), user.getPassword(), true, true, true, true, authorities, user);
	}
	
	private List<GrantedAuthority> buildUserAuthority(UserInfo user) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		authorities.add(new SimpleGrantedAuthority(user.getCompanyInfo().getRoleInfo().getRole()));
		
		return authorities;
	}
 
}