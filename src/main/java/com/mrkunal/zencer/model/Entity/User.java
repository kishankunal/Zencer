package com.mrkunal.zencer.model.Entity;

import com.mrkunal.zencer.model.enums.UserType;
import com.mrkunal.zencer.model.enums.Locale;
import lombok.ToString;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.EnumType;
import jakarta.persistence.TemporalType;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Index;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_no"})},
        indexes = {
                @Index(name = "idx_mobile_no", columnList = "mobile_no"),
                @Index(name = "idx_user_id", columnList = "member_id")
        })
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", unique = true)
    @Id
    private Long rowId;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id", unique = true, nullable = false, updatable = false)
    private String userId;

    @JsonProperty
    @Column(name = "mobile_no", nullable = false)
    private String mobileNumber;

    @JsonProperty
    @Column(name = "user_name", nullable = false, length = 200)
    private String name;

    @JsonProperty
    @Column(name = "user_identifier", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @JsonProperty
    @Column(name = "password", nullable = false, length = 256)  // Adjust length as per your hashing strategy
    private String password;

    @JsonProperty
    @Column(name = "locale", length = 64)
    @Enumerated(EnumType.STRING)
    private Locale locale;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        if (createdAt == null) {
            createdAt = now;
        }
        if (userId == null) {
            userId = UUID.randomUUID().toString();
        }
        updatedAt = now; // Set the current timestamp for the updatedAt column
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date(); // Update the timestamp on every update
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Locale getLocale() {
        return locale;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public static class UserBuilder {
        private String mobileNumber;
        private String name;
        private String password;
        private UserType userType;
        private Locale locale;

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public UserBuilder setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public User build() {
            User user = new User();
            user.setMobileNumber(this.mobileNumber);
            user.setName(this.name);
            user.setUserType(this.userType);
            user.setLocale(this.locale);
            user.setPassword(this.password);
            return user;
        }
    }
}
