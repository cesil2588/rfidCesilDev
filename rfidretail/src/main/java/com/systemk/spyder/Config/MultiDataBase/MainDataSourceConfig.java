package com.systemk.spyder.Config.MultiDataBase;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Main DB datasource
 * @author 최의선
 *
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
	basePackages = "com.systemk.spyder.Repository.Main",
	entityManagerFactoryRef = "mainEntityManager",
	transactionManagerRef = "mainTransactionManager"
)
public class MainDataSourceConfig {
	
	@Autowired 
    private Environment env;
     	
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mainDataSource());
        em.setPackagesToScan(new String[] { "com.systemk.spyder.Entity.Main" });
 
        HibernateJpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        properties.put("hibernate.enable_lazy_load_no_trans", true);
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Bean
    @Primary
    public DataSource mainDataSource() {
  
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("main.datasources.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("main.datasources.url"));
        dataSource.setUsername(env.getProperty("main.datasources.username"));
        dataSource.setPassword(env.getProperty("main.datasources.password"));
        dataSource.setMinimumIdle(100);
        dataSource.setMaximumPoolSize(100);
        dataSource.setIdleTimeout(25000);
        dataSource.setMaxLifetime(29000);
        dataSource.setValidationTimeout(10000);
       
//        return new HikariDataSource(dataSource);
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean
    public PlatformTransactionManager mainTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mainEntityManager().getObject());
        return transactionManager;
    }
}
