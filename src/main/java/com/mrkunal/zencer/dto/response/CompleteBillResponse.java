package com.mrkunal.zencer.dto.response;

import java.util.List;

public class CompleteBillResponse {

    private List<BillItemResponse> billItems; // List of BillItemResponse objects
    private String userId;
    private String shopId;
    private String userName;  // Username of the user
    private String shopName;  // Shop name
    private String shopAddress; // Shop address
    private String userMobileNumber; // User's mobile number
    private String createdAt; // Timestamp of when the bill was created
    private Long totalPrice;  // Total price of the bill

    // Private constructor to enforce the use of Builder
    private CompleteBillResponse(Builder builder) {
        this.billItems = builder.billItems;
        this.userId = builder.userId;
        this.shopId = builder.shopId;
        this.userName = builder.userName;
        this.shopName = builder.shopName;
        this.shopAddress = builder.shopAddress;
        this.userMobileNumber = builder.userMobileNumber;
        this.createdAt = builder.createdAt;
        this.totalPrice = builder.totalPrice;
    }

    // Getters
    public List<BillItemResponse> getBillItems() {
        return billItems;
    }

    public String getUserId() {
        return userId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getUserName() {
        return userName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    // Builder class for constructing CompleteBillResponse objects
    public static class Builder {

        private List<BillItemResponse> billItems;
        private String userId;
        private String shopId;
        private String userName;
        private String shopName;
        private String shopAddress;
        private String userMobileNumber;
        private String createdAt;
        private Long totalPrice;

        // Builder methods for setting fields
        public Builder billItems(List<BillItemResponse> billItems) {
            this.billItems = billItems;
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

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder shopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public Builder shopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
            return this;
        }

        public Builder userMobileNumber(String userMobileNumber) {
            this.userMobileNumber = userMobileNumber;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder totalPrice(Long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        // Build method to create the final CompleteBillResponse object
        public CompleteBillResponse build() {
            return new CompleteBillResponse(this);
        }
    }

    // Optional: toString, equals, and hashCode methods for easy debugging and comparison
    @Override
    public String toString() {
        return "CompleteBillResponse{" +
                "billItems=" + billItems +
                ", userId='" + userId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", userName='" + userName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", userMobileNumber='" + userMobileNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
