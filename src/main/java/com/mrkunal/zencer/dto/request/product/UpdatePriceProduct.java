package com.mrkunal.zencer.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdatePriceProduct {
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be non-negative")
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
