package com.spms.bookingservice.dto;

import com.spms.bookingservice.model.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingStatusUpdateRequest {
    @NotNull(message = "Status is required")
    private BookingStatus status;

    private Integer paymentId;
    private String receiptNumber;
}
