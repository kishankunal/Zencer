package com.mrkunal.zencer.model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrkunal.zencer.model.enums.ProductCategory;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id"})},
        indexes ={
                @Index(name = "idx_product_id", columnList = "product_id"),
                @Index(name = "idx_product_category", columnList = "category")
        })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id", nullable = false, unique = true)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", nullable = false)
    private Shop shop;

    @JsonProperty
    @Column(name = "product_name", nullable = false, length = 64)
    private String productName;

    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ProductCategory category;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Lifecycle callback methods to automatically set timestamps
    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;  // Set both createdAt and updatedAt at insert time
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();  // Set updatedAt only on update
    }

    // Builder pattern
    public static class Builder {
        private Long productId;
        private Shop shop;
        private String productName;
        private String description;
        private double price;
        private int quantity;
        private ProductCategory category;
        private Date createdAt;
        private Date updatedAt;

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder shop(Shop shop) {
            this.shop = shop;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder category(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.productId = this.productId;
            product.shop = this.shop;
            product.productName = this.productName;
            product.description = this.description;
            product.price = this.price;
            product.quantity = this.quantity;
            product.category = this.category;
            product.createdAt = this.createdAt;
            product.updatedAt = this.updatedAt;
            return product;
        }
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

