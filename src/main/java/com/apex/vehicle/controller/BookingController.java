package com.apex.vehicle.controller;

import com.apex.vehicle.dto.Dto.*;
import com.apex.vehicle.entity.Booking;
import com.apex.vehicle.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /** POST /api/bookings  — Create a new booking (logged-in customers) */
    @PostMapping
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequest req,
                                          @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.create(req, ud.getUsername()));
    }

    /** GET /api/bookings/my  — Customer's own bookings */
    @GetMapping("/my")
    public ResponseEntity<List<Booking>> myBookings(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(bookingService.myBookings(ud.getUsername()));
    }

    /** GET /api/bookings/ref/{ref}  — Lookup by booking reference */
    @GetMapping("/ref/{ref}")
    public ResponseEntity<Booking> byRef(@PathVariable String ref) {
        return ResponseEntity.ok(bookingService.getByRef(ref));
    }

    /** GET /api/bookings/slots?vehicleId=c01&date=2024-12-25  — Taken slots */
    @GetMapping("/slots")
    public ResponseEntity<List<String>> takenSlots(
            @RequestParam String vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.takenSlots(vehicleId, date));
    }

    /** PATCH /api/bookings/{id}/cancel  — Customer cancels their own booking */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancel(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(bookingService.cancel(id, ud.getUsername()));
    }
}
