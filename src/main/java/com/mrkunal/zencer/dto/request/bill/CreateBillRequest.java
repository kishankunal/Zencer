package com.mrkunal.zencer.dto.request.bill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateBillRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Shop ID is required")
    private String shopId;

    @NotEmpty(message = "At least one bill item is required")
    private List<@NotNull(message = "Bill item cannot be null") BillItemRequest> billItems;


    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<BillItemRequest> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemRequest> billItems) {
        this.billItems = billItems;
    }
}
