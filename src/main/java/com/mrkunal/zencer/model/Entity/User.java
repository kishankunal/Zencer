package com.mrkunal.zencer.model.Entity;

import com.mrkunal.zencer.model.enums.UserType;
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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_no"})})
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    @Id
    private Long userId;

    @JsonProperty
    @Column(name = "mobile_no")
    private String mobileNumber;

    @JsonProperty
    @Column(name = "user_name", nullable = false, length = 200)
    private String name;

    @JsonProperty
    @Column(name = "user_identifier", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @JsonProperty
    @Column(name = "locale", length = 64)
    private String locale;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now; // Set the current timestamp for the updatedAt column
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date(); // Update the timestamp on every update
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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

    public static class UserBuilder {

        private Long userId;
        private String mobileNumber;
        private String name;
        private UserType userType;
        private String locale;
        private Date createdAt;
        private Date updatedAt;

        public UserBuilder setUserId(Long userId) {
            this.userId = userId;
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

        public UserBuilder setLocale(String locale) {
            this.locale = locale;
            return this;
        }

        public UserBuilder setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            User user = new User();
            user.setUserId(this.userId);
            user.setMobileNumber(this.mobileNumber);
            user.setName(this.name);
            user.setUserType(this.userType);
            user.setLocale(this.locale);
            user.setCreatedAt(this.createdAt);
            user.setUpdatedAt(this.updatedAt);
            return user;
        }
    }
}
