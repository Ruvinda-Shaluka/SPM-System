package com.spms.bookingservice.service;

import com.spms.bookingservice.dto.BookingRequest;
import com.spms.bookingservice.dto.BookingResponse;
import com.spms.bookingservice.dto.BookingStatusUpdateRequest;
import com.spms.bookingservice.model.Booking;
import com.spms.bookingservice.model.BookingStatus;
import com.spms.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingResponse createBooking(BookingRequest request) {
        LocalDateTime start = (request.getStartTime() != null) ? request.getStartTime() : LocalDateTime.now();
        long minutes = (long) (request.getDurationHours() * 60);
        LocalDateTime end = start.plusMinutes(minutes);

        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .spaceId(request.getSpaceId())
                .vehicleLicensePlate(request.getVehicleLicensePlate().toUpperCase().trim())
                .startTime(start)
                .endTime(end)
                .durationHours(request.getDurationHours())
                .totalAmount(request.getTotalAmount())
                .status(BookingStatus.CONFIRMED)
                .paymentId(request.getPaymentId())
                .receiptNumber(request.getReceiptNumber())
                .build();

        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsBySpace(String spaceId) {
        return bookingRepository.findBySpaceIdOrderByCreatedAtDesc(spaceId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse updateBookingStatus(Long id, BookingStatusUpdateRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        booking.setStatus(request.getStatus());
        if (request.getPaymentId() != null) {
            booking.setPaymentId(request.getPaymentId());
        }
        if (request.getReceiptNumber() != null) {
            booking.setReceiptNumber(request.getReceiptNumber());
        }

        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public BookingResponse cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        booking.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public BookingResponse completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        booking.setStatus(BookingStatus.COMPLETED);
        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    private BookingResponse mapToResponse(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .userId(b.getUserId())
                .spaceId(b.getSpaceId())
                .vehicleLicensePlate(b.getVehicleLicensePlate())
                .startTime(b.getStartTime())
                .endTime(b.getEndTime())
                .durationHours(b.getDurationHours())
                .totalAmount(b.getTotalAmount())
                .status(b.getStatus())
                .paymentId(b.getPaymentId())
                .receiptNumber(b.getReceiptNumber())
                .createdAt(b.getCreatedAt())
                .updatedAt(b.getUpdatedAt())
                .build();
    }
}
