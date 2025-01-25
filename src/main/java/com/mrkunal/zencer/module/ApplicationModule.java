package com.mrkunal.zencer.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mrkunal.zencer.controller.contract.UserResource;
import com.mrkunal.zencer.controller.impl.UserResourceImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserResource.class).to(UserResourceImpl.class).in(Singleton.class);
    }

    @Provides
    public SessionFactory provideSessionFactory() {
        // Provide Hibernate SessionFactory instance
        return new Configuration().configure().buildSessionFactory();
    }
}
