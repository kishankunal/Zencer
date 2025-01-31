package com.mrkunal.zencer.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillResponse {

    private String billId;
    private String userId;
    private String shopId;
    private Long totalPrice;
    private List<String> errorDetails;
    private Date createdAt;

    // Private constructor for Builder
    private BillResponse(Builder builder) {
        this.billId = builder.billId;
        this.userId = builder.userId;
        this.shopId = builder.shopId;
        this.totalPrice = builder.totalPrice;
        this.errorDetails = builder.errorDetails;
        this.createdAt = builder.createdAt;
    }

    // Getters
    public String getBillId() {
        return billId;
    }

    public String getUserId() {
        return userId;
    }

    public String getShopId() {
        return shopId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public List<String> getErrorDetails() {
        return errorDetails;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setErrorDetails(List<String> errorDetails) {
        this.errorDetails = errorDetails;
    }

    // Builder Class
    public static class Builder {
        private String billId;
        private String userId;
        private String shopId;
        private Long totalPrice;
        private List<String> errorDetails;
        private Date createdAt;

        public Builder billId(String billId) {
            this.billId = billId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder shopId(String shopId) {
            this.shopId = shopId;
            return this;
        }

        public Builder totalPrice(Long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder errorDetails(List<String> errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BillResponse build() {
            return new BillResponse(this);
        }
    }
}
