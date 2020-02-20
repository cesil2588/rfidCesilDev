package com.systemk.spyder.Config.MultiDataBase;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

/**
 * External DB datasource
 * @author 최의선
 *
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
	basePackages = "com.systemk.spyder.Repository.External",
	entityManagerFactoryRef = "externalEntityManager",
	transactionManagerRef = "externalTransactionManager"
)
public class ExternalDataSourceConfig {

	@Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean externalEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(externalDataSource());
        em.setPackagesToScan(
          new String[] { "com.systemk.spyder.Entity.External" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto",env.getProperty("external.datasources.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource externalDataSource() {

		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(env.getProperty("external.datasources.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("external.datasources.url"));
		dataSource.setUsername(env.getProperty("external.datasources.username"));
		dataSource.setPassword(env.getProperty("external.datasources.password"));
		dataSource.setMinimumIdle(20);
        dataSource.setMaximumPoolSize(20);
        dataSource.setIdleTimeout(25000);
        dataSource.setMaxLifetime(29000);
        dataSource.setValidationTimeout(10000);

	//	return new HikariDataSource(dataSource);
       return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean
    public PlatformTransactionManager externalTransactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(externalEntityManager().getObject());
        return transactionManager;
    }
}
