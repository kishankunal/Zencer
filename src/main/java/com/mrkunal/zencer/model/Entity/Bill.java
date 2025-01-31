package com.mrkunal.zencer.model.Entity;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills", uniqueConstraints = {@UniqueConstraint(columnNames = {"bill_id"})},
        indexes = {
                @Index(name = "idx_bill_id", columnList = "bill_id")
        })
@ToString
public class Bill {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", unique = true)
    @Id
    private Long rowId;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bill_id", unique = true, nullable = false, updatable = false)
    private String billId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "member_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "itemIds")
    private List<String> billItems;

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

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
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

    public List<String> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<String> billItems) {
        this.billItems = billItems;
    }

    // Builder Class
    public static class Builder {
        private Long rowId;
        private String billId;
        private User user;
        private Shop shop;
        private Long totalPrice;
        private Date createdAt;
        private Date updatedAt;
        private List<String> billItems;

        public Builder rowId(Long rowId) {
            this.rowId = rowId;
            return this;
        }

        public Builder billId(String billId) {
            this.billId = billId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder shop(Shop shop) {
            this.shop = shop;
            return this;
        }

        public Builder totalPrice(Long totalPrice) {
            this.totalPrice = totalPrice;
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

        public Builder billItems(List<String> billItems) {
            this.billItems = billItems;
            return this;
        }

        public Bill build() {
            Bill bill = new Bill();
            bill.setRowId(this.rowId);
            bill.setBillId(this.billId);
            bill.setUser(this.user);
            bill.setShop(this.shop);
            bill.setTotalPrice(this.totalPrice);
            bill.setCreatedAt(this.createdAt);
            bill.setUpdatedAt(this.updatedAt);
            bill.setBillItems(this.billItems);
            return bill;
        }
    }
}
