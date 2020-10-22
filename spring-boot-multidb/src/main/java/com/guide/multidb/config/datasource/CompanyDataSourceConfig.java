package com.guide.multidb.config.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.guide.multidb.config.rdb.RdbmsConfig;
import com.guide.multidb.config.rdb.RdbmsProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.guide.multidb.repository.company",
        entityManagerFactoryRef = "companyEntityManager",
        transactionManagerRef = "transactionManager")
public class CompanyDataSourceConfig extends RdbmsConfig {

    @Autowired
    private DataSourceProperties properties;

    @Bean(name = "company", initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean dataSource() {

        return getDataSource(properties);
    }

    @Bean(name = "companyEntityManager")
    public EntityManagerFactory entityManager() {

        return getEntityManagerFactory(dataSource(), properties);
    }

    @Getter @Setter
    @Component
    @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
    protected class DataSourceProperties implements RdbmsProperties {

        public static final String PREFIX = "spring.datasource.company";
        private String resourceName;
        private String driverClassName;
        private String jdbcUrl;
        private String userName;
        private String password;
        private int minimumIdle;
        private int maximumPoolSize;
        private String packageToScan;
    }
}


