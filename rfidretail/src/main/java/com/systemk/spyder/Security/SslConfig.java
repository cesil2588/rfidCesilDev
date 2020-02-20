package com.systemk.spyder.Security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SslConfig {
	
	@Value("${ssl.enabled}") 
	boolean sslEnabled;
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		
		TomcatEmbeddedServletContainerFactory tomcat = null;
		
		if(sslEnabled){
			
			tomcat = new TomcatEmbeddedServletContainerFactory() {
				
				@Override
				protected void postProcessContext(Context context) {
					SecurityConstraint securityConstraint = new SecurityConstraint();
					securityConstraint.setUserConstraint("CONFIDENTIAL");
					SecurityCollection collection = new SecurityCollection();
					collection.addPattern("/*");
					securityConstraint.addCollection(collection);
					context.addConstraint(securityConstraint);
				}
			};
			tomcat.addAdditionalTomcatConnectors(getHttpConnector());
			return tomcat;
			
		}
		
		tomcat = new TomcatEmbeddedServletContainerFactory();
		
		return tomcat;
	}
	
	private Connector getHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}
}
