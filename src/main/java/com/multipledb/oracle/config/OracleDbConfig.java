package com.multipledb.oracle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        transactionManagerRef = "transactionManager",
        basePackages = {"com.multipledb.oracle.repo"},
        entityManagerFactoryRef = "entityManagerFactoryBean")
public class OracleDbConfig {

    @Autowired
    private Environment environment;

    //creating data source for oracle
    @Bean
    @Primary
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));

        return dataSource;
    }

    //entityManager for oracle
    @Bean(name = "entityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(dataSource());

        bean.setPackagesToScan("com.multipledb.oracle.entities");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        bean.setJpaVendorAdapter(adapter);

        Map props = Map.of(
                "hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect",
                "hibernate.show_sql", "true",
                "hibernate.hbm2ddl.auto", "update"
        );

        bean.setJpaPropertyMap(props);

        return bean;
    }

    //transaction manager for oracle
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager manager = new JpaTransactionManager();

        manager.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());

        return manager;
    }
}
