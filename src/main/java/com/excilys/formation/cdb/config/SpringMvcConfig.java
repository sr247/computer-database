package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.excilys.formation.cdb.mapper", 
		"com.excilys.formation.cdb.persistence", 
		"com.excilys.formation.cdb.service", 
		"com.excilys.formation.cdb.pages",
		"com.excilys.formation.cdb.controller"})
public class SpringMvcConfig implements WebMvcConfigurer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringMvcConfig.class);
	
	    @Bean
	    public ViewResolver viewResolver() {
	        InternalResourceViewResolver bean = new InternalResourceViewResolver();
	        bean.setViewClass(JstlView.class);
	        bean.setPrefix("/WEB-INF/");
	        bean.setSuffix(".jsp");
	        return bean;
	    }
	    
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        // Specifying static resource location for themes related files(css etc)
	    	registry.addResourceHandler("/static/**")
	    		.addResourceLocations("/static/");
	    }

}
