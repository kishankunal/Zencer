package com.mrkunal.zencer.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bill_items", indexes = {
        @Index(name = "idx_item_id", columnList = "item_id")
})
@ToString
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", unique = true, nullable = false)
    private Long row_id;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id", unique = true, nullable = false, updatable = false)
    private String itemId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_at_purchase", nullable = false)
    private double priceAtPurchase;

    // Private constructor for Builder pattern
    private BillItem(Builder builder) {
        this.itemId = builder.itemId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.priceAtPurchase = builder.priceAtPurchase;
    }

    public BillItem(Long row_id, String itemId, Product product, int quantity, double priceAtPurchase) {
        this.row_id = row_id;
        this.itemId = itemId;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public BillItem() {
    }

    public Long getRow_id() {
        return row_id;
    }

    public String getItemId() {
        return itemId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    // Builder Class
    public static class Builder {
        private String itemId;
        private Product product;
        private int quantity;
        private double priceAtPurchase;


        public Builder itemId(String itemId) {
            this.itemId = itemId;
            return this;
        }


        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder priceAtPurchase(double priceAtPurchase) {
            this.priceAtPurchase = priceAtPurchase;
            return this;
        }

        public BillItem build() {
            return new BillItem(this);
        }
    }
}
