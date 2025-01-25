package com.mrkunal.zencer.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ExternalModule extends AbstractModule {
    @Provides
    @Singleton
    public BCryptPasswordEncoder providePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
