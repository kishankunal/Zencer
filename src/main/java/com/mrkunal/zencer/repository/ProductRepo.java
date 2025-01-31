package com.mrkunal.zencer.repository;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.product.AddProductRequest;
import com.mrkunal.zencer.dto.response.product.ProductDetailsResponse;
import com.mrkunal.zencer.model.Entity.Product;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.mrkunal.zencer.constant.DatabaseConstant.*;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.FAILED_TO_FETCH_PRODUCT;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.FAILED_TO_FETCH_USER;


@Repository
public class ProductRepo {
    private final SessionFactory sessionFactory;

    @Inject
    public ProductRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(AddProductRequest request, Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        Product product = getProduct(request, shop);
        session.save(product);
    }

    private Product getProduct(AddProductRequest request, Shop shop) {
        return new Product.Builder()
                .productName(request.getProductName())
                .category(request.getCategory())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .shop(shop)
                .build();
    }

    public Product getProductFromId(String productId) {
        Session session = sessionFactory.getCurrentSession();
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

            Root<Product> root = criteriaQuery.from(Product.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(PRODUCT_ID), productId));

            TypedQuery<Product> query = session.createQuery(criteriaQuery);
            Product product = query.getResultList().stream().findFirst().orElse(null);

            if (product != null) {
                session.evict(product);
            }
            return product;
        }
        catch(Exception e){
            throw new RuntimeException(FAILED_TO_FETCH_PRODUCT + e.getMessage(), e );
        }
    }

    public List<ProductDetailsResponse> getAllProducts(Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get(SHOP), shop)); // Use "shop" as the field name

            TypedQuery<Product> query = session.createQuery(criteriaQuery);
            List<Product> products = query.getResultList(); // Removed unnecessary casting and stream

            // Transform List<Product> to List<ProductDetailsResponse>
            List<ProductDetailsResponse> productDetailsResponses = products.stream()
                    .map(ProductDetailsResponse::fromEntity)
                    .collect(Collectors.toList());

            if (products != null) {
                products.forEach(session::evict); // Evict each product to detach from session
            }

            return productDetailsResponses;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product: " + e.getMessage(), e);
        }
    }
}
