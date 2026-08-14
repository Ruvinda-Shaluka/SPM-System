package com.spms.bookingservice.dto;

import com.spms.bookingservice.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long userId;
    private String spaceId;
    private String vehicleLicensePlate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double durationHours;
    private Double totalAmount;
    private BookingStatus status;
    private Integer paymentId;
    private String receiptNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
