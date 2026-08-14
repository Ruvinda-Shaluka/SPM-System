package com.spms.parkingspaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpaceResponse {
    private String id;
    private String location;
    private String city;
    private String zone;
    private String ownerId;
    private String type;
    private Double pricePerHour;
    private boolean isAvailable;
    private Integer totalSlots;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
