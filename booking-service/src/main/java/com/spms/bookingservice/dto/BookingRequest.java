package com.spms.bookingservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Space ID is required")
    private String spaceId;

    @NotBlank(message = "Vehicle license plate is required")
    private String vehicleLicensePlate;

    private LocalDateTime startTime;

    @NotNull(message = "Duration in hours is required")
    @DecimalMin(value = "0.5", message = "Minimum duration is 0.5 hours")
    private Double durationHours;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private Double totalAmount;

    private Integer paymentId;

    private String receiptNumber;
}
