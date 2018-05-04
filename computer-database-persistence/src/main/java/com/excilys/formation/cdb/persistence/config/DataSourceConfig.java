package com.excilys.formation.cdb.persistence.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/datasource.properties")
@ComponentScan(basePackages = {"com.excilys.formation.cdb.persistence.repositories"})
public class DataSourceConfig {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceConfig.class);
	private static final String INIT_MESSAGE = "Initiate...";
	private static final String ACK_MESSAGE = "OK";
	
	@Value("${dataSource.driver}")
	private String driver;
	
	@Value("${jdbcUrl}")
	private String url;
	
	@Value("${dataSource.user}")
	private String user;
	
	@Value("${dataSource.password}")
	private String password;
	
	@Resource
	private Environment environment;	
	
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource: {}", INIT_MESSAGE);
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
	public JpaVendorAdapter jpaVendorAdapter() {
		logger.info("jpaVendorAdapter: {}", INIT_MESSAGE);
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		logger.info("jpaVendorAdapter: {}", ACK_MESSAGE);
		return jpaVendorAdapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("EntityManagedFactory: {}", INIT_MESSAGE);
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("com.excilys.formation.cdb");
		logger.info("EntityManagedFactory: {}", ACK_MESSAGE);
		return lemfb;
	}
	
	@Bean
    public JpaTransactionManager transactionManager() {
		logger.info("transactionManager: {}", INIT_MESSAGE);
		JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
        logger.info("transactionManager: {}", ACK_MESSAGE);
        return txnMgr;
    }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		logger.info("exceptionTranslation: {}", INIT_MESSAGE);
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
