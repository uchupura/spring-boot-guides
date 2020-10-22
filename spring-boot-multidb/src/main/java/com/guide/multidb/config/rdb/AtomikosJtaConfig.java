package com.guide.multidb.config.rdb;

import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

@Configuration
@EnableTransactionManagement
public class AtomikosJtaConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws SystemException {

        UserTransactionManager userTransactionManager = new UserTransactionManager();

        userTransactionManager.setTransactionTimeout(300);
        userTransactionManager.setForceShutdown(true);

        return userTransactionManager;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws SystemException {

        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();

        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionManager());

        return jtaTransactionManager;
    }
}
