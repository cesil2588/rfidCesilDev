package com.systemk.spyder.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("customUserDetailService")
	private UserDetailsService customUserDetailService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
    	
    	// static resources
    	http
    		.headers().frameOptions().disable()
    	.and()
			.sessionManagement()
				.invalidSessionUrl("/")
    	.and()
    		.addFilterBefore(filter,CsrfFilter.class)
    		.authorizeRequests()
    		.antMatchers("/resources/css/**", "/resources/js/**", "/resources/img/**", "/resources/**", "/member/**", "/ws/**").permitAll()
    	.and()
    		.httpBasic().realmName("SYSTEMK_REALM").authenticationEntryPoint(getBasicAuthEntryPoint())
    	/*
        .and()
        	.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/member/login")
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
			.failureUrl("/")
			.defaultSuccessUrl("/#/main/home", true)
		.and()
			.logout()
			.logoutSuccessUrl("/")
		*/
		.and()
			.csrf().disable();
//			.csrf().csrfTokenRepository(csrfTokenRepository())
//		.and()
//			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }
    
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
    
    
    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailService;
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}
    
    @Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}
    
    
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
