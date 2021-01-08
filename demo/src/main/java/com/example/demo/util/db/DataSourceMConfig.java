package com.example.demo.util.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
		entityManagerFactoryRef = "masterEntityManager", 
        transactionManagerRef = "masterTransactionManager",
        basePackages = "com.example.demo.table.master"
)
public class DataSourceMConfig {
	
	@Autowired
	private Environment env;
	
    @Bean
    @Primary
    public DataSource dataSourceM() {
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setDriverClassName(env.getProperty("spring.datasource-m.driverClassName"));
    	dataSource.setUrl(env.getProperty("spring.datasource-m.url"));
    	dataSource.setUsername(env.getProperty("spring.datasource-m.username"));
    	dataSource.setPassword(env.getProperty("spring.datasource-m.password"));
    	
    	return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean masterEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceM());
        
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
    
    @Primary
    @Bean
    protected JpaTransactionManager masterTransactionManager() {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(masterEntityManager().getObject());
        return transactionManager;
    }
 
}