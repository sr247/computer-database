package com.excilys.formation.cdb.webapp.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class DataBaseConfig {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataBaseConfig.class);

	@Resource
	private Environment environment;
	
//	@Bean
	public DataSource dataSource() {
//		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource("jdbcUrl", "dataSource.username",
//				"dataSource.password");
//		driverManagerDataSource.setDriverClassName("dataSource.driver");
//		return driverManagerDataSource;
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
        dataSource.setUrl(environment.getProperty("jdbcUrl"));
        dataSource.setUsername(environment.getProperty("dataSource.user"));
        dataSource.setPassword(environment.getProperty("dataSource.password"));
        return dataSource;
	}

//	@Bean
	public DataSourceTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}

}
