package com.apex.vehicle.dto;

import com.apex.vehicle.entity.Booking;
import com.apex.vehicle.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

// ─── Auth ───────────────────────────────────────────────────────────────────

public class Dto {

    @Data
    public static class RegisterRequest {
        @NotBlank private String name;
        @Email @NotBlank private String email;
        @NotBlank private String password;
        private String phone;
    }

    @Data
    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String name;
        private String email;
        private User.Role role;
        private Long userId;
    }

    // ─── Vehicle ────────────────────────────────────────────────────────────

    @Data
    public static class VehicleRequest {
        @NotBlank private String id;
        @NotBlank private String make;
        @NotBlank private String model;
        @NotNull  private String type;       // "car" | "motorcycle"
        private int year;
        private int horsepower;
        private int seats;
        private String transmission;
        private String fuel;
        private int pricePerHour;
        private int pricePerDay;
        private boolean available;
        private String imageUrl;
        private String thumbUrl;
        private String description;
    }

    // ─── Booking ────────────────────────────────────────────────────────────

    @Data
    public static class BookingRequest {
        @NotBlank  private String vehicleId;
        @NotNull   private String bookingType;   // "TESTDRIVE" | "RENTAL"
        @NotNull   private LocalDate bookingDate;
        private String timeSlot;                  // required for TESTDRIVE
        private Integer rentalDays;               // required for RENTAL
        private String customerName;
        private String customerPhone;
        private String licenseUrl;
         private String paymentMethod;
        private String paymentStatus;
    }
   
    @Data
    public static class BookingStatusUpdate {
        @NotNull private String status;           // APPROVED | REJECTED | COMPLETED
        private String adminNotes;
    }

    // ─── Review ─────────────────────────────────────────────────────────────

    @Data
    public static class ReviewRequest {
        private String vehicleId;
        private int rating;
        private String comment;
    }

    // ─── Admin Dashboard ────────────────────────────────────────────────────

    @Data
    public static class DashboardStats {
        private long totalVehicles;
        private long availableVehicles;
        private long totalBookings;
        private long pendingBookings;
        private long approvedBookings;
        private long totalUsers;
        private long totalRevenue;
    }
}
