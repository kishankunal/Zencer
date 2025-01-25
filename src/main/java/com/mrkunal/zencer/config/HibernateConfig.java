package com.mrkunal.zencer.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // Hibernate settings
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        settings.put(Environment.HBM2DDL_AUTO, "update"); // Change to `validate` or `none` for production
        settings.put(Environment.SHOW_SQL, "true");

        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(settings);
        sessionFactory.setPackagesToScan("com.mrkunal.zencer.model.Entity");


        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
