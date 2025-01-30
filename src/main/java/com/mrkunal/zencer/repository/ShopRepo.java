package com.mrkunal.zencer.repository;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.shop.RegisterShopRequest;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.model.enums.Status;
import com.mrkunal.zencer.model.enums.TokenStatus;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import static com.mrkunal.zencer.constant.DatabaseConstant.SHOP_ID;
import static com.mrkunal.zencer.constant.DatabaseConstant.USER_ID;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.*;

@Repository
public class ShopRepo {

    private final SessionFactory sessionFactory;


    @Inject
    public ShopRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public void addShop(@NonNull final RegisterShopRequest request, @NonNull User user){
        Shop shop = getShop(request, user);
        Session session = sessionFactory.getCurrentSession();
        session.save(shop);
        session.evict(shop);
    }

    private Shop getShop(RegisterShopRequest request, User user) {
        return new Shop.ShopBuilder()
                .setShopName(request.getShopName())
                .setCity(request.getCity())
                .setCountry(request.getCountry())
                .setPincode(request.getPincode())
                .setState(request.getState())
                .setUser(user)
                .setStatus(Status.INACTIVE) //initially shop would be inactive
                .build();
    }

    public void activateShop(String shopId) {
        Shop shop = getShopFromShopId(shopId);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        shop.setStatus(Status.ACTIVE);
        session.update(shop);
        transaction.commit();
        session.evict(shop);
    }

    public void deactivateShop(String shopId) {
        Shop shop = getShopFromShopId(shopId);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        shop.setStatus(Status.INACTIVE);
        session.update(shop);
        transaction.commit();
        session.evict(shop);
    }

    public Shop getShopFromShopId(String shopId) {
        Session session = sessionFactory.getCurrentSession();
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Shop> criteriaQuery = criteriaBuilder.createQuery(Shop.class);

            Root<Shop> root = criteriaQuery.from(Shop.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(SHOP_ID), shopId));

            TypedQuery<Shop> query = session.createQuery(criteriaQuery);
            Shop shop = query.getResultList().stream().findFirst().orElse(null);

            if (shop != null) {
                session.evict(shop);
            } else {
                throw new RuntimeException(INVALID_SHOP_ID);
            }
            return shop;
        }
        catch(Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_SHOP + e.getMessage(), e );
        }
    }
}
