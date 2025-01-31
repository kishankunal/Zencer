package com.mrkunal.zencer.dto.request.product;


import com.mrkunal.zencer.model.enums.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddProductRequest {

    @NotNull(message = "Shop ID is required")
    @Size(max = 256, message = "Shop id must be at most 64 characters")
    private String shopId;

    @NotBlank(message = "Product name is required")
    @Size(max = 64, message = "Product name must be at most 64 characters")
    private String productName;

    @NotBlank(message = "Description is required")
    @Size(max = 256, message = "Description must be at most 256 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    private double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater then 1")
    private int quantity;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    // Getters and Setters
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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
}
