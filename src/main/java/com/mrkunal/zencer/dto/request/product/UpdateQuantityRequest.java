package com.mrkunal.zencer.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateQuantityRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "shouldIncrease flag is required")
    private Boolean shouldIncrease;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getShouldIncrease() {
        return shouldIncrease;
    }

    public void setShouldIncrease(Boolean shouldIncrease) {
        this.shouldIncrease = shouldIncrease;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
