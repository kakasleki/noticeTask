package com.myapp.task.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.task.common.filter.HtmlCharacterEscapes;
import com.myapp.task.common.interceptor.LoginInterceptor;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.*;

import javax.servlet.Filter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "com.myapp.task")
public class WebConfig implements WebMvcConfigurer {
	private static final String[] RESOURCES_LOCATIONS;
	private static final String[] RESOURCES_PATTERNS;
	private static final String[] EXCLUDE_PATH_PATTERNS;

	static {
		EXCLUDE_PATH_PATTERNS = new String[] {
				"/log**",
				"/images/**",
				"/css/**",
				"/font/**",
				"/js/**",
				"/upload/**"
		};

		RESOURCES_LOCATIONS = new String[] {
				"classpath:/static/css/",
				"classpath:/static/font/",
				"classpath:/static/images/",
				"classpath:/static/js/common/",
				"classpath:/static/js/views/",
				"classpath:/upload/"
		};

		RESOURCES_PATTERNS = new String[] {
				"/css/**",
				"/font/**",
				"/images/**",
				"/js/common/**",
				"/js/views/**"
		};
	}

	@Bean
	public FilterRegistrationBean<Filter> getFilterRegistrationBean() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new XssEscapeServletFilter());
		registrationBean.setOrder(2);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(this.htmlEscapingConverter());
	}

	private HttpMessageConverter<?> htmlEscapingConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());

		MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
		htmlEscapingConverter.setObjectMapper(objectMapper);

		return htmlEscapingConverter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns(EXCLUDE_PATH_PATTERNS);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler(RESOURCES_PATTERNS)
				.addResourceLocations(RESOURCES_LOCATIONS)
				.setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePublic())
				.resourceChain(false)
				.addResolver(new EncodedResourceResolver())
				.addResolver(new VersionResourceResolver().addVersionStrategy(new ContentVersionStrategy(), "/**"))
				.addTransformer(new CssLinkResourceTransformer());
	}

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				.addMapping("/**")
				.allowedOrigins("https://localhost:8443")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
				.maxAge(3000);
	}
}