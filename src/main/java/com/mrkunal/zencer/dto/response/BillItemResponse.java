package com.mrkunal.zencer.dto.response;

public class BillItemResponse {

    private String itemId;
    private String productId;
    private String productName;  // If you need to include product name or other details
    private int quantity;
    private double priceAtPurchase;

    // Private constructor to enforce use of Builder
    private BillItemResponse(Builder builder) {
        this.itemId = builder.itemId;
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.quantity = builder.quantity;
        this.priceAtPurchase = builder.priceAtPurchase;
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    // Builder Class for creating instances of BillItemResponse
    public static class Builder {

        private String itemId;
        private String productId;
        private String productName;
        private int quantity;
        private double priceAtPurchase;

        // Builder methods
        public Builder itemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
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

        // Build method to return the final BillItemResponse
        public BillItemResponse build() {
            return new BillItemResponse(this);
        }
    }

    // Optional: toString, equals, and hashCode methods for easy debugging and comparison
    @Override
    public String toString() {
        return "BillItemResponse{" +
                "itemId='" + itemId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", priceAtPurchase=" + priceAtPurchase +
                '}';
    }
}
