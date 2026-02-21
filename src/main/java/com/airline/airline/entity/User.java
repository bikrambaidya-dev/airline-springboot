package com.airline.airline.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor // ✅ Mandatory for JPA
public class User {
    public enum Role {
        ADMIN,
        USER,
        AGENT
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "verification_token", columnDefinition = "text", nullable = true)
    private String verificationToken;   
    
    @Column(name = "verification_token_expiry", columnDefinition = "timestamp", nullable = true)
    private Date verificationTokenExpiry;


    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ Clean constructor for object creation
    // public User(String name, String email, String password, String role, String
    // status) {
    // this.name = name;
    // this.email = email;
    // this.password = password;
    // this.role = role;
    // this.status = status;
    // }

    // ✅ Auto timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
