package com.chentu.mika.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.chentu.mika.interceptor.JWTInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	
	
	@Autowired
	JWTInterceptor jwtInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		log.info("into mvc config ");
		registry.addInterceptor(jwtInterceptor)
				.excludePathPatterns("/register")
				.excludePathPatterns("/login");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:9090","http://localhost:5173")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowCredentials(true);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("file:D:/img/");
	}
	
	@Bean
	public PaginationInnerInterceptor interceptor(){
		PaginationInnerInterceptor page = new PaginationInnerInterceptor();
		page.setDbType(DbType.MYSQL);
		return page;
	}
}
