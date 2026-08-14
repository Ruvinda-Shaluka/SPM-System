package com.spms.parkingspaceservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingSpaceRequest {

    @NotBlank(message = "Space ID is required (e.g., A1, SLOT-101)")
    private String id;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotBlank(message = "Owner ID is required")
    private String ownerId;

    private String type = "STANDARD";

    @NotNull(message = "Price per hour is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    private Double pricePerHour;

    private boolean isAvailable = true;

    private Integer totalSlots = 1;

    private String description;

    private String imageUrl;
}
