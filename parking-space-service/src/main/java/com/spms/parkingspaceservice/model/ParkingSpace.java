package com.spms.parkingspaceservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "parking_spaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpace {

    @Id
    private String id; // e.g., "A1", "SLOT-001"

    @Column(nullable = false)
    private String location; // e.g., "Grand Mall, Level 2"

    @Column(nullable = false)
    private String city; // e.g., "Colombo", "New York"

    @Column(nullable = false)
    private String zone; // e.g., "Zone A - North", "Sector 4"

    @Column(nullable = false)
    private String ownerId; // ID of the space owner / provider

    @Column(nullable = false)
    private String type; // "STANDARD", "COMPACT", "EV_CHARGING", "VIP", "HANDICAPPED"

    @Column(nullable = false)
    private Double pricePerHour;

    @Builder.Default
    private boolean isAvailable = true;

    private Integer totalSlots;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}