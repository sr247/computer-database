package com.excilys.formation.cdb.webapp.config;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.excilys.formation.cdb.persistence.repositories")
public class PersistenceJPAConfig {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PersistenceJPAConfig.class);

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		System.out.println("EntityManagedFactory");
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("com.excilys.formation.cdb.core.entity", "com.excilys.formation.cdb.persistence",
				"com.excilys.formation.cdb.persistence.repositories", "com.excilys.formation.cdb.binding",
				"com.excilys.formation.cdb.binding.row", "com.excilys.formation.cdb.service");
		return lemfb;
	}

	@Resource
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		System.out.println("DataSourced");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
		dataSource.setUrl(environment.getProperty("jdbcUrl"));
		dataSource.setUsername(environment.getProperty("dataSource.user"));
		dataSource.setPassword(environment.getProperty("dataSource.password"));
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		System.out.println("TxManaged");
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		System.out.println("VendorAdaptated");
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
