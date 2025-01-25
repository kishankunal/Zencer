package com.mrkunal.zencer.model.Entity;

import com.mrkunal.zencer.model.enums.UserType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_no"})})
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
}
