package com.mrkunal.zencer.dto.request;

import com.mrkunal.zencer.model.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.mrkunal.zencer.model.enums.Locale;


public class SignUpRequest {
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name cannot be null")
    private final String name;

    @NotNull(message = "Mobile number cannot be null")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile number must be numeric")
    private final String mobileNumber;

    @NotNull(message = "Locale cannot be null")
    private final Locale locale;

    @NotNull(message = "User type cannot be null")
    private final UserType userType;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must be alphanumeric")
    private final String password;

    // Constructor, getters, setters, etc.
    public SignUpRequest(String name, String mobileNo, String mobileNumber, Locale locale, UserType userType, @NotNull(message = "Password cannot be null") String password) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.locale = locale;
        this.userType = userType;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserType getUserType() {
        return userType;
    }
}
