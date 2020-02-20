package com.systemk.spyder.Security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.UserInfo;

public class LoginUser extends User{
	
	private static final long serialVersionUID = 1L;
	
	private long userSeq;
    private String userId;
    private Date regDate;
    private Date updDate;
    private CompanyInfo companyInfo;
    
    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userSeq = userInfo.getUserSeq();
		this.userId = userInfo.getUserId();
		this.regDate = userInfo.getRegDate();
		this.updDate = userInfo.getUpdDate();
		this.companyInfo = userInfo.getCompanyInfo();
	} 

	public long getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(long userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
		
	
}
