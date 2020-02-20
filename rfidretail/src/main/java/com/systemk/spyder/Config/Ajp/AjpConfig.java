package com.systemk.spyder.Config.Ajp;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AjpConfig {

	@Value("${tomcat.ajp.protocol}")
	String ajpProtocol;

	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Value("${tomcat.ajp.enabled}")
	boolean tomcatAjpEnabled;

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return container -> {
			TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
			if (tomcatAjpEnabled) {
				Connector ajpConnector = new Connector(ajpProtocol);
				ajpConnector.setPort(ajpPort);
				ajpConnector.setSecure(false);
				ajpConnector.setAllowTrace(false);
				ajpConnector.setScheme("http");
				tomcat.addAdditionalTomcatConnectors(ajpConnector);
			}
		};
	}
}
