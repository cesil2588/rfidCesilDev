package com.systemk.spyder.Config.Jwt;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.systemk.spyder.Service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * JWT 인증을 위한 핸들러
 * @author youdozi
 *
 */
public class OpenApiTokenInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		final String token = request.getHeader("Authorization");
		
		if(StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
		
			try {
				return jwtService.checkToken(token);
			} catch (ExpiredJwtException e) {
				//토큰 기한 만료시 자동 로그아웃
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				new SecurityContextLogoutHandler().logout(request, response, auth);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 권한 만료");
			} catch (SignatureException se) {
				//토큰 유효성 검사 에러시 자동 로그아웃
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				new SecurityContextLogoutHandler().logout(request, response, auth);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 유효성 에러");
			}
			
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			
			//토큰 존재하지 않을 경우 자동 로그아웃
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout(request, response, auth);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token이 존재하지 않습니다");
		}
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}
	
}
