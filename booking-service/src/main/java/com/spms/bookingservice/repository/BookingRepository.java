package com.spms.bookingservice.repository;

import com.spms.bookingservice.model.Booking;
import com.spms.bookingservice.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findBySpaceIdOrderByCreatedAtDesc(String spaceId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
}
