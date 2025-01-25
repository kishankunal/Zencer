package com.mrkunal.zencer.service;

import com.mrkunal.zencer.model.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

@Service
public class UserService {

    private final SessionFactory sessionFactory;
    @Inject
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }
}

// remove this autowired and use google juice for injection
// resolve created at and updated at issue
// standard response class

