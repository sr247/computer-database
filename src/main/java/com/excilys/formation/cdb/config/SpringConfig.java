package com.excilys.formation.cdb.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.cdb.mapper", 
		"com.excilys.formation.cdb.persistence", 
		"com.excilys.formation.cdb.service", 
		"com.excilys.formation.cdb.pages"})
public class SpringConfig implements WebApplicationInitializer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringConfig.class);

		// Créer un DatabaseConfig pour générer la datasource  
	    @Bean
	    public DataSource datasource() {
	    	HikariConfig config = null;
	        HikariDataSource ds = null;
	    	Properties prop = new Properties();
	    	InputStream pathDB = SpringConfig.class.getClassLoader().getResourceAsStream("datasource.properties");
	    	try {
				prop.load(pathDB);
				Class.forName(prop.getProperty("dataSource.driver"));
				config = new HikariConfig(prop);
				config.addDataSourceProperty( "cachePrepStmts" , "true" );
				config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
				config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
				ds = new HikariDataSource(config);
	    	} catch (ClassNotFoundException | IOException e) {
				// Gérer les loggers et les Exceptions
			} catch (Exception e) {
	    		logger.error(String.format("%s: {%s, %s, %s}", e.getClass().getSimpleName(), config, ds, prop));
	    	}
	    	logger.info(String.format("OK: {%s, %s, %s}", config, ds, prop));
	    	return ds;
	    }

		@Override
		public void onStartup(ServletContext container) throws ServletException {
			AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
			springContext.register(SpringConfig.class);
			springContext.setServletContext(container);
			
//		      // Create the dispatcher servlet's Spring application context
//			AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
//		    dispatcherContext.register(DispatcherConfig.class);
			
			ServletRegistration.Dynamic dispatcher = container.addServlet("dashboard", new DispatcherServlet(springContext));
	        dispatcher.setLoadOnStartup(1);
	       	dispatcher.addMapping("/dashboard");
		}
}
