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

import static com.mrkunal.zencer.constant.DatabaseConstant.USER;
import static com.mrkunal.zencer.constant.DatabaseConstant.TOKEN;
import static com.mrkunal.zencer.constant.DatabaseConstant.TOKEN_STATUS;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.FAILED_TO_FETCH_USER_SESSION;


@Repository
public class SessionRepo {

    private final SessionFactory sessionFactory;

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
                            criteriaBuilder.equal(root.get(TOKEN_STATUS), tokenStatus.name())
                    ));

            TypedQuery<UserSession> query = session.createQuery(criteriaQuery);
            UserSession userSession = query.getResultList().stream().findFirst().orElse(null);

            if (userSession != null) {
                session.evict(userSession);
            }

            return userSession;
        } catch (Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_USER_SESSION +e.getMessage(), e );
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
                                    criteriaBuilder.equal(root.get(TOKEN_STATUS), tokenStatus.name()) // Token status condition
                            )
                    );

            TypedQuery<UserSession> query = session.createQuery(criteriaQuery);
            UserSession userSession = query.getResultList().stream().findFirst().orElse(null);

            if (userSession != null) {
                session.evict(userSession);
            }
            return userSession;
        } catch (Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_USER_SESSION + e.getMessage(), e );
        }
    }

    public boolean isTokenBlackListed(String token){
        UserSession session = findByTokenAndTokenStatus(token, TokenStatus.VALID);
        return session == null;
    }

    public void invalidateSession(UserSession userSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        userSession.setTokenStatus(TokenStatus.INVALID.name());
        session.update(userSession);
        transaction.commit();
        session.evict(userSession);
    }
}
