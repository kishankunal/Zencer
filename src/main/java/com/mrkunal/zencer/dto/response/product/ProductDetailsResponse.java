package com.mrkunal.zencer.dto.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrkunal.zencer.model.Entity.Product;
import com.mrkunal.zencer.model.enums.ProductCategory;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsResponse {

    private Long productId;
    private String shopId;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private ProductCategory category;
    private Date createdAt;
    private Date updatedAt;

    public ProductDetailsResponse(Long productId, String shopId, String productName, String description,
                                  double price, int quantity, ProductCategory category) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public static ProductDetailsResponse fromEntity(Product product) {
        return new ProductDetailsResponse(
                product.getProductId(),
                product.getShop().getShopId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }

    // Getters & Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
