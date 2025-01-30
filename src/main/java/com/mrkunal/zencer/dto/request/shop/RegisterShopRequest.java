package com.mrkunal.zencer.dto.request.shop;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterShopRequest {

    @NotNull(message = "Shop name cannot be null")
    @Size(min = 1, message = "Shop name cannot be null")
    private String shopName;

    @NotNull(message = "City cannot be null")
    @Size(min = 1, message = "City cannot be null")
    private String city;

    @NotNull(message = "State cannot be null")
    @Size(min = 1, message = "State cannot be null")
    private String state;

    @NotNull(message = "Country cannot be null")
    @Size(min = 1, message = "Country cannot be null")
    private String country;

    @NotNull(message = "Pincode cannot be null")
    @Size(min = 6, max = 6, message = "Pincode must be 6 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Pincode must be numeric")
    private String pincode;

    private String gstIn;

    // Default constructor required by Jackson
    public RegisterShopRequest() {}

    // Parameterized constructor
    public RegisterShopRequest(String shopName, String city, String state, String country, String pincode, String gstIn) {
        this.shopName = shopName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.gstIn = gstIn;
    }

    // Getters for Swagger to generate request schema
    public String getShopName() { return shopName; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getPincode() { return pincode; }
    public String getGstIn() { return gstIn; }
}
