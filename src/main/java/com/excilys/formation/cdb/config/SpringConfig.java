package com.excilys.formation.cdb.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;

import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "{mapper, persistence, service, pages}")
public class AppConfiguration implements WebMvcConfigurer, WebApplicationInitializer {

	    @Bean
	    public DataSource getDatasource () {
	    	HikariConfig config;
	        HikariDataSource ds;
	    	Properties prop = new Properties();
			InputStream pathDB = DataSource.class.getClassLoader().getResourceAsStream("datasource.properties");

				try {
					prop.load(pathDB);
					Class.forName(prop.getProperty("dataSource.driver"));
				} catch (ClassNotFoundException | IOException e) {
					// Gérer les loggers et les Exceptions
				}			
				config = new HikariConfig(prop);
				config.addDataSourceProperty( "cachePrepStmts" , "true" );
				config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
				config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
				ds = new HikariDataSource(config);        
	    	return ds;
	    }

		@Override
		public void onStartup(ServletContext container) throws ServletException {
			AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
			webAppContext.setConfigLocation("com.excilys.formation.cdb.config");
			webAppContext.setServletContext(container);
			container.addListener(new ContextLoaderListener(webAppContext));
	        ServletRegistration.Dynamic dispatcher = container.addServlet("dashboard", new DispatcherServlet(webAppContext));
	        dispatcher.setLoadOnStartup(1);
	       	dispatcher.addMapping("/dashboard");
		}

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
			// Ommited
		}

		@Override
		public void addCorsMappings(CorsRegistry arg0) {
			// Ommited
		}

		@Override
		public void addFormatters(FormatterRegistry arg0) {
			// Ommited
		}

		@Override
		public void addInterceptors(InterceptorRegistry arg0) {
			// Ommited
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry arg0) {
			// Ommited
			
		}

		@Override
		public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
			// Ommited
			
		}

		@Override
		public void addViewControllers(ViewControllerRegistry arg0) {
			// Ommited
			
		}

		@Override
		public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
			// Ommited
			
		}

		@Override
		public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
			// Ommited
			
		}

		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer arg0) {
			// Ommited
			
		}

		@Override
		public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
			// Ommited
			
		}

		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
			// Ommited
			
		}

		@Override
		public void configurePathMatch(PathMatchConfigurer arg0) {
			// Ommited
			
		}

		@Override
		public void configureViewResolvers(ViewResolverRegistry arg0) {
			// Ommited
			
		}

		@Override
		public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
			// Ommited
			
		}

		@Override
		public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
			// Ommited
			
		}

		@Override
		public MessageCodesResolver getMessageCodesResolver() {
			// Ommited
			return null;
		}

		@Override
		public Validator getValidator() {
			// Ommited
			return null;
		}

}
