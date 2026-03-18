package com.apex.vehicle.controller;

import com.apex.vehicle.dto.Dto.*;
import com.apex.vehicle.entity.Booking;
import com.apex.vehicle.entity.User;
import com.apex.vehicle.repository.BookingRepository;
import com.apex.vehicle.repository.UserRepository;
import com.apex.vehicle.repository.VehicleRepository;
import com.apex.vehicle.service.BookingService;
import com.apex.vehicle.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;
    private final BookingRepository bookingRepo;

    // ── Dashboard stats ──────────────────────────────────────────────────────
    /** GET /api/admin/dashboard */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> dashboard() {
        DashboardStats stats = new DashboardStats();
        stats.setTotalVehicles(vehicleRepo.count());
        stats.setAvailableVehicles(vehicleRepo.findByAvailableTrue().size());
        stats.setTotalBookings(bookingRepo.count());
        stats.setPendingBookings(bookingRepo.countByStatus(Booking.Status.PENDING));
        stats.setApprovedBookings(bookingRepo.countByStatus(Booking.Status.APPROVED));
        stats.setTotalUsers(userRepo.count());

        long revenue = bookingRepo.findAllByOrderByCreatedAtDesc().stream()
                .filter(b -> b.getStatus() == Booking.Status.COMPLETED ||
                             b.getStatus() == Booking.Status.APPROVED)
                .mapToLong(Booking::getTotalPrice)
                .sum();
        stats.setTotalRevenue(revenue);

        return ResponseEntity.ok(stats);
    }

    // ── All bookings ─────────────────────────────────────────────────────────
    /** GET /api/admin/bookings */
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> allBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /** GET /api/admin/bookings/pending */
    @GetMapping("/bookings/pending")
    public ResponseEntity<List<Booking>> pendingBookings() {
        return ResponseEntity.ok(bookingRepo.findByStatusOrderByCreatedAtDesc(Booking.Status.PENDING));
    }

    /** PATCH /api/admin/bookings/{id}/status */
    @PatchMapping("/bookings/{id}/status")
    public ResponseEntity<Booking> updateStatus(@PathVariable Long id,
                                                @RequestBody BookingStatusUpdate req) {
        return ResponseEntity.ok(bookingService.updateStatus(id, req));
    }

    // ── Users ────────────────────────────────────────────────────────────────
    /** GET /api/admin/users */
    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    // ── Reviews ──────────────────────────────────────────────────────────────
    /** DELETE /api/admin/reviews/{id} */
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
