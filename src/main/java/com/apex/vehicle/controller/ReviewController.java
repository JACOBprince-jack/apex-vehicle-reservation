package com.apex.vehicle.controller;

import com.apex.vehicle.dto.Dto.ReviewRequest;
import com.apex.vehicle.entity.Review;
import com.apex.vehicle.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /** GET /api/reviews  — All reviews (for Reviews page) */
    @GetMapping
    public ResponseEntity<List<Review>> all() {
        return ResponseEntity.ok(reviewService.all());
    }

    /** GET /api/reviews/vehicle/{id}  — Reviews for a specific vehicle */
    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<Review>> byVehicle(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.byVehicle(id));
    }

    /** GET /api/reviews/vehicle/{id}/rating  — Average rating */
    @GetMapping("/vehicle/{id}/rating")
    public ResponseEntity<Map<String, Double>> avgRating(@PathVariable String id) {
        return ResponseEntity.ok(Map.of("avgRating", reviewService.avgRating(id)));
    }

    /** POST /api/reviews  — Submit a review (logged-in users) */
    @PostMapping
    public ResponseEntity<Review> create(@RequestBody ReviewRequest req,
                                         @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.create(req, ud.getUsername()));
    }
}
