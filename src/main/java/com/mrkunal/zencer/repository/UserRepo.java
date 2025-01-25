package com.mrkunal.zencer.repository;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import com.mrkunal.zencer.model.Entity.User;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.mrkunal.zencer.util.HashUtil.encryptPassword;

@Repository
public class UserRepo {
    private final SessionFactory sessionFactory;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String MOBILE_NO = "mobileNumber";

    @Inject
    public UserRepo(SessionFactory sessionFactory, BCryptPasswordEncoder passwordEncoder) {
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(@NonNull final SignUpRequest request){
        User user = getUser(request);
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }




    public User findByMobileNumber(final String mobileNumber) {
        Session session = sessionFactory.getCurrentSession();
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

            // Define the root of the query (i.e., the User entity)
            Root<User> root = criteriaQuery.from(User.class);

            // Apply the condition on mobileNumber
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(MOBILE_NO), mobileNumber));

            // Execute the query
            TypedQuery<User> query = session.createQuery(criteriaQuery);
            User user = query.getResultList().stream().findFirst().orElse(null);

            // Detach the user to prevent automatic persistence
            if (user != null) {
                session.evict(user);  // Detach the entity from session
            }

            return user;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to fetch user by mobile number - " +e.getMessage() , e );
        }
    }

    @PreDestroy
    public void onShutdown() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            session.flush(); // Ensures all pending operations are flushed to DB
            session.clear(); // Clears session cache
        }
    }

    private User getUser(SignUpRequest request) {
        return new User.UserBuilder()
                .setUserType(request.getUserType())
                .setLocale(request.getLocale())
                .setName(request.getName())
                .setMobileNumber(request.getMobileNumber())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
