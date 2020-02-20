package com.systemk.spyder.Config.Jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * JWT 인증 로직을 위한 인터셉터
 * registry.addInterceptor에 URL를 추가시 JWT 인증 로직을 태운다
 * @author youdozi
 *
 */
@Configuration
public class JwtConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		if(true) {

			// store 인증 처리 
			// registry.addInterceptor(createOpenApiTokenInterceptor()).addPathPatterns("/api/store/**");
		}
	}
	
	@Bean
	public OpenApiTokenInterceptor createOpenApiTokenInterceptor() {
		return new OpenApiTokenInterceptor();
	}
	
}
