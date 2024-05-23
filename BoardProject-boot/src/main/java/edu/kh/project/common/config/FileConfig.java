package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig implements WebMvcConfigurer{

	

	
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;
	
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize;
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize;
	
	@Value("${spring.servlet.multipart.location}")
	private String location;
	
//	profile image
	
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler;
	
	@Value("${my.profile.resource-location}")
	private String profileResourceLocation;
	
//	board image
	@Value("${my.board.resource-handler}")
	private String boardResourceHandler;
	
	@Value("${my.board.resource-location}")
	private String boardResourceLocation;
	
//	web request path
//	-> server path
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/myPage/file/**").addResourceLocations("file:///C:/uploadFiles/temp/");
		
//		file:///C: -> file system root directory
//		file:// -> URL Scheme, file system resource
		
		registry.addResourceHandler(profileResourceHandler).addResourceLocations(profileResourceLocation);
		
		
		registry.addResourceHandler(boardResourceHandler).addResourceLocations(boardResourceLocation);
	}
	
//	MultipartResolver config
	@Bean
	public MultipartConfigElement configElement() {
//		file upload => option
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		
		factory.setLocation(location);
		
		return factory.createMultipartConfig();
	}

	@Bean
	public MultipartResolver multipartResolver() {
//		MultipartFile -> data transfered from client translate into other types 
		
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		
		return multipartResolver;
		
	}
	
}
