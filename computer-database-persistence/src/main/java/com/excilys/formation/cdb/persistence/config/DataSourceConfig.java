package com.excilys.formation.cdb.persistence.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:/datasource.properties")
//@ComponentScan(basePackages = {"com.excilys.formation.cdb"})
public class DataSourceConfig {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceConfig.class);
	private static final String INIT_LESSAGE = "Initiate...";
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
	
	
	public DataSource dataSource() {
		logger.info("dataSource: {}", INIT_LESSAGE);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		try {
			dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
			dataSource.setUrl(environment.getProperty("jdbcUrl"));
			dataSource.setUsername(environment.getProperty("dataSource.user"));
			dataSource.setPassword(environment.getProperty("dataSource.password"));
		} catch (Exception e) {
			logger.info("dataSource: {driver={}, url={}, user={}, password={}}", driver, url, user, password);
			throw e;
		}
		logger.info("dataSource: {}", ACK_MESSAGE);
		return dataSource;
	}

}
