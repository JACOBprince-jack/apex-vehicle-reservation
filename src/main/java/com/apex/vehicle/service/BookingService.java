package com.apex.vehicle.service;

import com.apex.vehicle.dto.Dto.*;
import com.apex.vehicle.entity.Booking;
import com.apex.vehicle.entity.Booking.BookingType;
import com.apex.vehicle.entity.Booking.Status;
import com.apex.vehicle.entity.User;
import com.apex.vehicle.entity.Vehicle;
import com.apex.vehicle.exception.ApexException;
import com.apex.vehicle.repository.BookingRepository;
import com.apex.vehicle.repository.UserRepository;
import com.apex.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;

    // ── Create Booking ───────────────────────────────────────────────────────
    public Booking create(BookingRequest req, String userEmail) {

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApexException("User not found"));

        Vehicle vehicle = vehicleRepo.findById(req.getVehicleId())
                .orElseThrow(() -> new ApexException("Vehicle not found: " + req.getVehicleId()));

        if (!vehicle.isAvailable()) {
            throw new ApexException("Vehicle is currently not available for booking");
        }

        BookingType type = BookingType.valueOf(req.getBookingType().toUpperCase());

        // Check slot conflict for test drives
        if (type == BookingType.TESTDRIVE && req.getTimeSlot() != null) {
            List<String> taken = bookingRepo.findTakenSlots(req.getVehicleId(), req.getBookingDate());
            if (taken.contains(req.getTimeSlot())) {
                throw new ApexException("Time slot " + req.getTimeSlot() + " is already booked");
            }
        }

        Booking b = new Booking();
        b.setBookingRef(generateRef());
        b.setUser(user);
        b.setVehicle(vehicle);
        b.setBookingType(type);
        b.setBookingDate(req.getBookingDate());
        b.setTimeSlot(req.getTimeSlot());
        b.setRentalDays(req.getRentalDays());
        b.setCustomerName(req.getCustomerName() != null ? req.getCustomerName() : user.getName());
        b.setCustomerPhone(req.getCustomerPhone() != null ? req.getCustomerPhone() : user.getPhone());
        b.setLicenseUrl(req.getLicenseUrl());
        b.setTotalPrice(calcPrice(type, vehicle, req.getRentalDays()));
        b.setStatus(Status.APPROVED);

        return bookingRepo.save(b);
    }

    // ── My Bookings ──────────────────────────────────────────────────────────
    public List<Booking> myBookings(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApexException("User not found"));
        return bookingRepo.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    // ── Get by ref ───────────────────────────────────────────────────────────
    public Booking getByRef(String ref) {
        return bookingRepo.findByBookingRef(ref)
                .orElseThrow(() -> new ApexException("Booking not found: " + ref));
    }

    // ── Cancel (by customer) ─────────────────────────────────────────────────
    public Booking cancel(Long id, String userEmail) {
        Booking b = bookingRepo.findById(id)
                .orElseThrow(() -> new ApexException("Booking not found"));
        if (!b.getUser().getEmail().equalsIgnoreCase(userEmail)) {
            throw new ApexException("Unauthorised");
        }
        if (b.getStatus() != Status.PENDING) {
            throw new ApexException("Only PENDING bookings can be cancelled");
        }
        b.setStatus(Status.CANCELLED);
        return bookingRepo.save(b);
    }

    // ── Update Status (admin) ────────────────────────────────────────────────
    public Booking updateStatus(Long id, BookingStatusUpdate req) {
        Booking b = bookingRepo.findById(id)
                .orElseThrow(() -> new ApexException("Booking not found"));
        b.setStatus(Status.valueOf(req.getStatus().toUpperCase()));
        if (req.getAdminNotes() != null) b.setAdminNotes(req.getAdminNotes());
        return bookingRepo.save(b);
    }

    // ── All bookings (admin) ─────────────────────────────────────────────────
    public List<Booking> getAllBookings() {
        return bookingRepo.findAllByOrderByCreatedAtDesc();
    }

    // ── Taken slots ──────────────────────────────────────────────────────────
    public List<String> takenSlots(String vehicleId, java.time.LocalDate date) {
        return bookingRepo.findTakenSlots(vehicleId, date);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private String generateRef() {
        return "BK" + UUID.randomUUID().toString().replace("-", "")
                .substring(0, 8).toUpperCase();
    }

    private int calcPrice(BookingType type, Vehicle v, Integer days) {
        if (type == BookingType.TESTDRIVE) {
            return v.getPricePerHour();            // 1-hour test drive
        }
        if (days == null || days <= 0) return 0;
        int base = days * v.getPricePerDay();
        // 10% weekly discount if >= 7 days
        if (days >= 7) base = (int) (base * 0.90);
        return base;
    }
}
