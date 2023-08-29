package com.multipledb.postgres.config;

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
        entityManagerFactoryRef = "secondEntityManagerFactory",
        basePackages = {"com.multipledb.postgres.repo"},
        transactionManagerRef = "secondTransactionManager"
)
public class PostgresDbConfig {

    @Autowired
    private Environment environment;


    //data source for PostgreSQL
    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(environment.getProperty("second.datasource.url"));
        dataSource.setUsername(environment.getProperty("second.datasource.username"));
        dataSource.setPassword(environment.getProperty("second.datasource.password"));

        return dataSource;
    }

    //entity manager for postgreSQL
    @Bean(name = "secondEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(this.dataSource());

        bean.setPackagesToScan("com.multipledb.postgres.entities");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        bean.setJpaVendorAdapter(adapter);

        Map properties = Map.of(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                "hibernate.hbm2ddl.auto", "update",
                "hibernate.show_sql", "true"
        );

        bean.setJpaPropertyMap(properties);

        return bean;
    }

    //transaction manager for PostgreSQL
    @Bean(name = "secondTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());

        return transactionManager;
    }
}
