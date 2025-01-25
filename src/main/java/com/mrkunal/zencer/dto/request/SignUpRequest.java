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
    private final String locale;

    @NotNull(message = "User type cannot be null")
    private final UserType userType;

    // Constructor, getters, setters, etc.
    public SignUpRequest(String name, String mobileNo,  String mobileNumber, String locale, UserType userType) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.locale = locale;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getLocale() {
        return locale;
    }

    public UserType getUserType() {
        return userType;
    }
}
