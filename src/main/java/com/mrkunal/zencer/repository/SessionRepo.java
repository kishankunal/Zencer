package com.mrkunal.zencer.repository;

import com.google.inject.Inject;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.model.Entity.UserSession;
import com.mrkunal.zencer.model.enums.TokenStatus;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class SessionRepo {

    private final SessionFactory sessionFactory;
    private final String USER = "user";
    private final String TOKEN = "token";
    private final String TOKEN_STATUS = "tokenStatus";

    @Inject
    public SessionRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(UserSession userSession) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userSession);
    }

    public UserSession findByUserIdAndTokenStatus(User user, TokenStatus tokenStatus) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserSession> criteriaQuery = criteriaBuilder.createQuery(UserSession.class);

            Root<UserSession> root = criteriaQuery.from(UserSession.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(USER), user),
                            criteriaBuilder.equal(root.get(TOKEN_STATUS), tokenStatus)
                    ));

            TypedQuery<UserSession> query = session.createQuery(criteriaQuery);
            UserSession userSession = query.getResultList().stream().findFirst().orElse(null);

            if (userSession != null) {
                session.evict(userSession);
            }

            return userSession;
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch users-sessions- " +e.getMessage() , e );
        }
    }

    public UserSession findByTokenAndTokenStatus(final String token,final TokenStatus tokenStatus) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserSession> criteriaQuery = criteriaBuilder.createQuery(UserSession.class);

            Root<UserSession> root = criteriaQuery.from(UserSession.class);

            criteriaQuery.select(root)
                    .where(
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get(TOKEN), token),  // Token condition
                                    criteriaBuilder.equal(root.get(TOKEN_STATUS), tokenStatus) // Token status condition
                            )
                    );

            TypedQuery<UserSession> query = session.createQuery(criteriaQuery);
            UserSession userSession = query.getResultList().stream().findFirst().orElse(null);

            if (userSession != null) {
                session.evict(userSession);  // Detach the entity from session
            }
            return userSession;
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch users-sessions- " + e.getMessage() , e );
        }
    }

    public boolean isTokenBlackListed(String token){
        UserSession session = findByTokenAndTokenStatus(token, TokenStatus.VALID);
        return session == null;
    }

    public void invalidateSession(UserSession userSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        userSession.setTokenStatus(TokenStatus.INVALID);
        session.update(userSession);
        transaction.commit();
        session.evict(userSession);
    }
}
