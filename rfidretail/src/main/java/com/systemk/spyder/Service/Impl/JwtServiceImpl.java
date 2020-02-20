package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtServiceImpl implements JwtService{
	
	@Value("${spring.jwt.key}")
	private String jwtSigningKey;
	
	//token 발급
	@Override
	public String createToken(UserInfo userInfo) throws Exception {
		
		//jwt 만료 시간 : 24시간
		Date expiration = new Date(System.currentTimeMillis() + 86400000L);
		
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("alg", "HS256")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setClaims(Jwts.claims().setExpiration(expiration))
				.claim("role", userInfo.getCompanyInfo().getRoleInfo().getRole())
				.signWith(SignatureAlgorithm.HS256, jwtSigningKey)
				.compact();
		return jwt;
	}
	
	//token 유효성 check
	@Override
	public Boolean checkToken(String jwt) throws ExpiredJwtException,SignatureException,Exception {
		String token = jwt.substring(7);
		Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token);
		return true;
	}

	//token payload claim값 가져오기
	@Override
	public Map<String, Object> getTokenClaim(String jwt, String claim) throws Exception, ExpiredJwtException {
		
		Map<String, Object> tokenBody = new LinkedHashMap<String, Object>();
		
		if(claim == null || claim.equals("")) {
			tokenBody.put("error", "요청 토큰 값이 없습니다");
			return tokenBody;
		}
		
		try {
			Jws<Claims> claims = Jwts.parser()
			          .setSigningKey(jwtSigningKey)
			          .parseClaimsJws(jwt);
			
	        tokenBody.put(claim, claims.getBody().get(claim));
	        
		} catch (ExpiredJwtException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tokenBody;
	}
}
