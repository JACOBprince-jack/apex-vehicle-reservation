package com.apex.vehicle.service;

import com.apex.vehicle.dto.Dto.ReviewRequest;
import com.apex.vehicle.entity.Review;
import com.apex.vehicle.entity.User;
import com.apex.vehicle.entity.Vehicle;
import com.apex.vehicle.exception.ApexException;
import com.apex.vehicle.repository.ReviewRepository;
import com.apex.vehicle.repository.UserRepository;
import com.apex.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;

    public Review create(ReviewRequest req, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApexException("User not found"));

        Review r = new Review();
        r.setUser(user);
        r.setRating(req.getRating());
        r.setComment(req.getComment());

        if (req.getVehicleId() != null) {
            Vehicle v = vehicleRepo.findById(req.getVehicleId())
                    .orElseThrow(() -> new ApexException("Vehicle not found"));
            r.setVehicle(v);
        }
        return reviewRepo.save(r);
    }

    public List<Review> byVehicle(String vehicleId) {
        return reviewRepo.findByVehicleIdOrderByCreatedAtDesc(vehicleId);
    }

    public List<Review> all() {
        return reviewRepo.findAll();
    }

    public Double avgRating(String vehicleId) {
        Double avg = reviewRepo.avgRatingByVehicle(vehicleId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    public void delete(Long id) {
        reviewRepo.deleteById(id);
    }
}
