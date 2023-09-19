package com.testtask.clinic.database.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConnection {
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();

	    dataSource.setUsername("postgres");
	    dataSource.setPassword("postgres");
	    
	    dataSource.setUrl(
	      "jdbc:postgresql://localhost:5432/shedule"); 
	    
	    return dataSource;
	}

}
