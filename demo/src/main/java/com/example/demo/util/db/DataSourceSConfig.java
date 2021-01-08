package com.example.demo.util.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "slaveEntityManager", 
        transactionManagerRef = "slaveTransactionManager",
        basePackages = "com.example.demo.table.slave"
)
public class DataSourceSConfig {
	
	@Autowired
	private Environment env;
	
    @Bean
    public DataSource dataSourceS() {

    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setDriverClassName(env.getProperty("spring.datasource-s.driverClassName"));
    	dataSource.setUrl(env.getProperty("spring.datasource-s.url"));
    	dataSource.setUsername(env.getProperty("spring.datasource-s.username"));
    	dataSource.setPassword(env.getProperty("spring.datasource-s.password"));
    	
    	return dataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean slaveEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceS());
        
        //Entity 패키지 경로
        em.setPackagesToScan(new String[] { "com.example.demo.table" });
    
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        //Hibernate 설정
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }
    
    @Bean
    protected JpaTransactionManager slaveTransactionManager() {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(slaveEntityManager().getObject());
        return transactionManager;
    }
    
}