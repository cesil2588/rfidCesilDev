package com.systemk.spyder.Config.Jpa;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.systemk.spyder.Entity.Main.View.View;

@Configuration
public class JsonViewConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper() {
			private static final long serialVersionUID = 1L;

			@Override
			protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
				// replace the configuration with my modified configuration.
				// calling "withView" should keep previous config and just add
				// my changes.
				return super._serializerProvider(config.withView(View.MobileList.class)
													   .withView(View.MobileDetail.class)
													   .withView(View.ExternalView.class)
													   .withView(View.Public.class));
			}
		};
		
//		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(mapper);
		converters.add(converter);
	}
}
