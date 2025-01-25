package com.mrkunal.zencer.repository;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import com.mrkunal.zencer.model.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import static com.mrkunal.zencer.util.HashUtil.encryptPassword;

@Repository
public class UserRepo {
    private final SessionFactory sessionFactory;

    @Inject
    public UserRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createUser(SignUpRequest request){
        User user = getUser(request);
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    private User getUser(SignUpRequest request) {
        return new User.UserBuilder()
                .setUserType(request.getUserType())
                .setLocale(request.getLocale())
                .setName(request.getName())
                .setMobileNumber(request.getMobileNumber())
                .setPassword(encryptPassword(request.getPassword()))
                .build();
    }
}
