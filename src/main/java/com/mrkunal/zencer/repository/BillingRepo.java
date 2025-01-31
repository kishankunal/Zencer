package com.mrkunal.zencer.repository;

import com.mrkunal.zencer.model.Entity.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.mrkunal.zencer.constant.DatabaseConstant.*;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.FAILED_TO_FETCH_BILL;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.FAILED_TO_FETCH_PRODUCT;

@Repository
public class BillingRepo {
    private final SessionFactory sessionFactory;

    public BillingRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Bill createBill(List<String> billItems, Long totalPrice, User user, Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        Bill bill = new Bill.Builder().billId(UUID.randomUUID()
                .toString()).billItems(billItems)
                .shop(shop).user(user)
                .totalPrice(totalPrice)
                .build();
        session.save(bill);
        return bill;
    }

    public BillItem addBillItem(BillItem item) {
        Session session = sessionFactory.getCurrentSession();
        session.save(item);
        return item;
    }

    public Bill getBillById(String billId) {
        Session session = sessionFactory.getCurrentSession();
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);

            Root<Bill> root = criteriaQuery.from(Bill.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(BILL_ID), billId));

            TypedQuery<Bill> query = session.createQuery(criteriaQuery);
            Bill bill = query.getResultList().stream().findFirst().orElse(null);

            if (bill != null) {
                session.evict(bill);
            } else {
                throw new RuntimeException("Invalid bill id");
            }
            return bill;
        }
        catch(Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_BILL + e.getMessage(), e );
        }
    }

    public BillItem getBillItemByItemId(String itemId) {
        Session session = sessionFactory.getCurrentSession();
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BillItem> criteriaQuery = criteriaBuilder.createQuery(BillItem.class);

            Root<BillItem> root = criteriaQuery.from(BillItem.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(ITEM_ID), itemId));

            TypedQuery<BillItem> query = session.createQuery(criteriaQuery);
            BillItem billItem = query.getResultList().stream().findFirst().orElse(null);

            if (billItem != null) {
                session.evict(billItem);
            } else {
                throw new RuntimeException("Invalid bill item id");
            }
            return billItem;
        }
        catch(Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_BILL + e.getMessage(), e );
        }
    }
}
