package com.excilys.formation.cdb.webapp.config;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@EnableJpaRepositories("com.excilys.formation.cdb.persistence.repositories")
@PropertySource(value = "classpath:datasource.properties")
@ComponentScan(basePackages = { 
		"com.excilys.formation.cdb.binding",
		"com.excilys.formation.cdb.binding.row",
		"com.excilys.formation.cdb.persistence",
		"com.excilys.formation.cdb.service",
		"com.excilys.formation.cdb.webapp.controller",
		"com.excilys.formation.cdb.webapp.servlets"})
public class PersistenceJPAConfig implements WebMvcConfigurer {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PersistenceJPAConfig.class);
	private static final String INIT_LESSAGE = "Initiate...";
	private static final String ACK_MESSAGE = "OK";
		
	@Resource
	private Environment environment;	
	
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource: {}", INIT_LESSAGE);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		try {
			dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
			dataSource.setUrl(environment.getProperty("jdbcUrl"));
			dataSource.setUsername(environment.getProperty("dataSource.user"));
			dataSource.setPassword(environment.getProperty("dataSource.password"));
		} catch (Exception e) {
			logger.info("dataSource: {driver={}, url={}, user={}, password={}}", 
					environment.getProperty("dataSource.driver"), 
					environment.getProperty("jdbcUrl"),
					environment.getProperty("dataSource.user"),
					environment.getProperty("dataSource.password"));
			throw e;
		}
		logger.info("dataSource: {}", ACK_MESSAGE);
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("EntityManagedFactory: {}", INIT_LESSAGE);
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("com.excilys.formation.cdb");
		logger.info("EntityManagedFactory: {}", ACK_MESSAGE);
		return lemfb;
	}
		
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		logger.info("jpaVendorAdapter: {}", INIT_LESSAGE);
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		logger.info("jpaVendorAdapter: {}", ACK_MESSAGE);
		return jpaVendorAdapter;
	}
	
	@Bean
    public JpaTransactionManager transactionManager() {
		logger.info("transactionManager: {}", INIT_LESSAGE);
		JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
        logger.info("transactionManager: {}", ACK_MESSAGE);
        return txnMgr;
    }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		logger.info("exceptionTranslation: {}", INIT_LESSAGE);
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
    @Bean
    public ViewResolver viewResolver() {
    	logger.info("viewResolver: {}", INIT_LESSAGE);
    	InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/");
        bean.setSuffix(".jsp");
        logger.info("viewResolver: {}", ACK_MESSAGE);
        return bean;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Specifying static resource location for themes related files(css etc)
    	logger.info("viewResolver: {}", INIT_LESSAGE);
    	registry.addResourceHandler("/static/**")
    		.addResourceLocations("/static/");
    	logger.info("viewResolver: {}", ACK_MESSAGE);
    }

}
