package com.mrkunal.zencer.dto.response.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.enums.Status;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopDetailsResponse {

    private String shopId;

    private String agentId;

    private String shopName;

    private String city;

    private String pincode;

    private String state;

    private String country;

    private String gstin;

    private Status status;

    private Date createdAt;

    public ShopDetailsResponse(String shopId, String agentId, String shopName, String city, String pincode,
                        String state, String country, String gstin, Status status, Date createdAt) {
        this.shopId = shopId;
        this.agentId = agentId;
        this.shopName = shopName;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.country = country;
        this.gstin = gstin;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static ShopDetailsResponse fromEntity(Shop shop) {
        return new ShopDetailsResponse(
                shop.getShopId(),
                shop.getUser().getUserId(),
                shop.getShopName(),
                shop.getCity(),
                shop.getPincode(),
                shop.getState(),
                shop.getCountry(),
                shop.getGstin(),
                shop.getStatus(),
                shop.getCreatedAt()
        );
    }

    // Getters & Setters
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}

