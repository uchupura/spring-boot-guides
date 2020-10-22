package com.guide.multidb.config.rdb;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
public abstract class RdbmsConfig {

    @Autowired
    protected Environment env;

    public abstract AtomikosDataSourceBean dataSource();

    protected AtomikosDataSourceBean getDataSource(RdbmsProperties properties) {

        Properties xaProperties = new Properties();
        xaProperties.put("url", properties.getJdbcUrl());
        xaProperties.put("user", properties.getUserName());
        xaProperties.put("password", properties.getPassword());

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        // dataSource.setLocalTransactionMode(true);
        dataSource.setUniqueResourceName(properties.getResourceName());
        dataSource.setXaDataSourceClassName(properties.getDriverClassName());
        dataSource.setXaProperties(xaProperties);
        dataSource.setMinPoolSize(properties.getMinimumIdle());
        dataSource.setMaxPoolSize(properties.getMaximumPoolSize());

        return dataSource;
    }

    protected EntityManagerFactory getEntityManagerFactory(AtomikosDataSourceBean dataSource, RdbmsProperties properties) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(properties.getPackageToScan());
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName(properties.getResourceName());
        factory.setJpaProperties(getJpaProperties());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    protected Properties getJpaProperties() {

        Properties properties = new Properties();

        properties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));

        properties.put("hibernate.connection.CharSet", env.getRequiredProperty("spring.jpa.properties.hibernate.connection.CharSet"));
        properties.put("hibernate.connection.characterEncoding", env.getRequiredProperty("spring.jpa.properties.hibernate.connection.characterEncoding"));
        properties.put("hibernate.connection.useUnicode", env.getRequiredProperty("spring.jpa.properties.hibernate.connection.useUnicode"));

        properties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        properties.put("hibernate.use_sql_comments", env.getRequiredProperty("spring.jpa.properties.hibernate.use_sql_comments"));

        properties.put("hibernate.temp.use_jdbc_metadata_defaults", env.getRequiredProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults"));
        properties.put("hibernate.jdbc.time_zone", env.getRequiredProperty("spring.jpa.properties.hibernate.jdbc.time_zone"));
        properties.put("hibernate.default_batch_fetch_size", env.getRequiredProperty("spring.jpa.properties.hibernate.default_batch_fetch_size"));

        properties.put("hibernate.id.new_generator_mappings", Boolean.FALSE.toString());    // 키 생성 전략
        properties.put("hibernate.current_session_context_class", "jta");
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");    // 물리 테이블 및 필드 네이밍 전략
        properties.put("hibernate.implicit_naming_strategy" , "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");   // 논리 테이블 및 필드 네이밍 전략
        properties.put("javax.persistence.transactionType", "jta");
        properties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");

        return properties;
    }
}
