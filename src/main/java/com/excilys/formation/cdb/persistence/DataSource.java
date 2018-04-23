package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	private static HikariConfig config;
    private static HikariDataSource ds;
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataSource.class);
    
    static {
    	Properties prop = new Properties();
		InputStream pathDB = DataSource.class.getClassLoader().getResourceAsStream("datasource.properties");

		try {
			prop.load(pathDB);
			Class.forName(prop.getProperty("dataSource.driver"));			
			config = new HikariConfig(prop);
			config.addDataSourceProperty( "cachePrepStmts" , "true" );
			config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
			config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
			ds = new HikariDataSource(config);        
		} catch (ClassNotFoundException | IOException e) {
			logger.error("DataSourceError: {}", e.getMessage(), e);
		}
    }
 
    private DataSource() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
