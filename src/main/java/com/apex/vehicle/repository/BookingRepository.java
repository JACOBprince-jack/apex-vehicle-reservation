package com.apex.vehicle.repository;

import com.apex.vehicle.entity.Booking;
import com.apex.vehicle.entity.Booking.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findByStatusOrderByCreatedAtDesc(Status status);

    List<Booking> findAllByOrderByCreatedAtDesc();

    Optional<Booking> findByBookingRef(String bookingRef);

    /** Slots already booked for a vehicle on a given date */
    @Query("SELECT b.timeSlot FROM Booking b WHERE b.vehicle.id = :vehicleId " +
           "AND b.bookingDate = :date " +
           "AND b.status NOT IN ('REJECTED','CANCELLED')")
    List<String> findTakenSlots(@Param("vehicleId") String vehicleId,
                                @Param("date") LocalDate date);

    /** Count bookings by status for admin dashboard */
    long countByStatus(Status status);
}
