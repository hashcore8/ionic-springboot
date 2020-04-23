package com.configurations;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = { "com.bank.Dao.UserInterface" })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class Persistentcontext {

	@Autowired
	Environment env;
	
	

	@Bean(destroyMethod = "close")
	DataSource getdatasource() {
		System.out.println(env.getProperty("url"));
		System.out.println(env.getProperty("driverClassName"));
		System.out.println(env.getProperty("user"));
		System.out.println(env.getProperty("password"));
		
	
		
		
		
		DataSource db = new DataSource();
		db.setUrl(env.getProperty("url"));
		db.setDriverClassName(env.getProperty("driverClassName"));
		db.setUsername(env.getProperty("user"));
		db.setPassword(env.getProperty("password"));
		return db;
	}

	
	@Bean
	LocalContainerEntityManagerFactoryBean  entityManagerFactory(Environment env,DataSource datasource)
	{
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(datasource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.bank.pojo");
		
		Properties jpa=new Properties();
		jpa.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpa.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		jpa.put("hibernate.default_schema", env.getProperty("hibernate.default_schema"));
		jpa.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	//	jpa.put("hibernate.dialect", env.getProperty("hibernate.dialect"))
		
		entityManagerFactoryBean.setJpaProperties(jpa);
		return entityManagerFactoryBean;
	}
	
	@Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean);
        return transactionManager;
    }
	
}
