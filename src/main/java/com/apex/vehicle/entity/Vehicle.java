package com.apex.vehicle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @Column(length = 10)
    private String id;           // e.g. "i01", "c01", "m01"

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @Enumerated(EnumType.STRING)
    @NotNull
    private VehicleType type;    // CAR, MOTORCYCLE

    private int year;

    private int horsepower;

    private int seats;

    @NotBlank
    private String transmission;

    @NotBlank
    private String fuel;

    /** Price per hour (INR) */
    @Column(name = "price_hour")
    private int pricePerHour;

    /** Price per day (INR) */
    @Column(name = "price_day")
    private int pricePerDay;

    private boolean available = true;

    private boolean underMaintenance = false;

    @Column(length = 500)
    private String imageUrl;

    @Column(name = "thumb_url", length = 500)
    private String thumbUrl;

    /** Optional: short description for detail view */
    @Column(length = 1000)
    private String description;

    public enum VehicleType {
        car, motorcycle
    }
}
