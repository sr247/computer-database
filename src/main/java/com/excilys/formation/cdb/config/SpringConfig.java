package com.excilys.formation.cdb.config;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/datasource.properties")
@ComponentScan(basePackages = {"com.excilys.formation.cdb.mapper", 
		"com.excilys.formation.cdb.persistence", 
		"com.excilys.formation.cdb.service", 
		"com.excilys.formation.cdb.pages"})
public class SpringConfig implements WebApplicationInitializer, WebMvcConfigurer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringConfig.class);

//	    @Bean
//	    public DataSource dataSourceOld() {
//	    	HikariConfig config = null;
//	        HikariDataSource ds = null;
//	    	Properties prop = new Properties();
//	    	InputStream pathDB = SpringConfig.class.getClassLoader().getResourceAsStream("datasource.properties");
//	    	try {
//				prop.load(pathDB);
//				Class.forName(prop.getProperty("dataSource.driver"));
//				config = new HikariConfig(prop);
//				config.addDataSourceProperty( "cachePrepStmts" , "true" );
//				config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//				config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//				ds = new HikariDataSource(config);
//			} catch (Exception e) {
//	    		logger.error("{: {{}, {}, {}}", e.getClass().getSimpleName(), config, ds, prop);
//	    	}
//	    	if(ds != null)
//	    		logger.info("OK: {{}, {}, {}}", config, ds, prop);
//	    	return ds;
//	    }

		@Override
		public void onStartup(ServletContext container) throws ServletException {
			AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
			springContext.register(SpringConfig.class);
			springContext.setServletContext(container);
			
			ServletRegistration.Dynamic dispatcher = container.addServlet("dashboard", new DispatcherServlet(springContext));
	        dispatcher.setLoadOnStartup(1);
	       	dispatcher.addMapping("/dashboard");
		}
		
	    @Override
	    public void configureViewResolvers(ViewResolverRegistry registry) {
	        registry.jsp("/WEB-INF/", ".jsp");
	    }
	    
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
	    
	    @Bean
	    public DataSource datasource() {
	    	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource("jdbcUrl", "dataSource.user", "dataSource.password");
	    	driverManagerDataSource.setDriverClassName("dataSource.driver");
	    	return driverManagerDataSource;	    	
	    }
	    
	    @Bean
	    public DataSourceTransactionManager txManager() {
	        return new DataSourceTransactionManager(dataSource());
	    }
	    

}
