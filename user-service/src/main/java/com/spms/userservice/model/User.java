package com.spms.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "app_users")
@Data // Lombok automatically creates Getters, Setters, and toString()
@NoArgsConstructor // Lombok creates the default constructor required by JPA
@AllArgsConstructor // Lombok creates a constructor with all fields
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role; // e.g., "DRIVER" or "OWNER"

    @Column(columnDefinition = "TEXT")
    private String bookingHistory; // A simple text block to hold log references
}