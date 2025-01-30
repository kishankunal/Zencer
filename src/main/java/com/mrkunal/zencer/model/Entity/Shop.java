package com.mrkunal.zencer.model.Entity;

import com.mrkunal.zencer.model.enums.Status;
import jakarta.persistence.*;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "shops", uniqueConstraints = {@UniqueConstraint(columnNames = {"agent_id", "shop_name"})},
        indexes = {@Index(name = "idx_shop_id", columnList = "shop_id")})
@ToString
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", unique = true)
    private Long rowId;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shop_id", unique = true, updatable = false)
    private String shopId;

    @OneToOne
    @JoinColumn(name = "agent_id",  referencedColumnName = "member_id", nullable = false)
    private User user;

    @JsonProperty
    @Column(name = "shop_name", nullable = false, length = 200)
    private String shopName;

    @JsonProperty
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @JsonProperty
    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;

    @JsonProperty
    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @JsonProperty
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @JsonProperty
    @Column(name = "gstin", unique = true, length = 15)
    private String gstin;

    @Enumerated(EnumType.STRING) // Stores the ENUM as a String in DB
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        if (createdAt == null) {
            createdAt = now;
        }
        if (shopId == null) {
            shopId = UUID.randomUUID().toString();
        }
        updatedAt = now; // Set the current timestamp for the updatedAt column
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

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date(); // Update the timestamp on every update
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class ShopBuilder {
        private User user;
        private String shopName;
        private String city;
        private String pincode;
        private String state;
        private String country;
        private String gstin;
        private Status status;

        public ShopBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public ShopBuilder setShopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public ShopBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public ShopBuilder setPincode(String pincode) {
            this.pincode = pincode;
            return this;
        }

        public ShopBuilder setState(String state) {
            this.state = state;
            return this;
        }

        public ShopBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public ShopBuilder setGstin(String gstin) {
            this.gstin = gstin;
            return this;
        }

        public ShopBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Shop build() {
            Shop shop = new Shop();
            shop.setUser(this.user);
            shop.setShopName(this.shopName);
            shop.setCity(this.city);
            shop.setPincode(this.pincode);
            shop.setState(this.state);
            shop.setCountry(this.country);
            shop.setGstin(this.gstin);
            shop.setStatus(this.status);
            return shop;
        }
    }
}

