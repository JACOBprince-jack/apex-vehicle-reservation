package com.apex.vehicle.repository;

import com.apex.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByType(Vehicle.VehicleType type);

    List<Vehicle> findByAvailableTrue();

    List<Vehicle> findByTypeAndAvailableTrue(Vehicle.VehicleType type);

    @Query("SELECT v FROM Vehicle v WHERE " +
           "(:type IS NULL OR v.type = :type) AND " +
           "(:available IS NULL OR v.available = :available) AND " +
           "(LOWER(v.make) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(v.model) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Vehicle> search(@Param("type") Vehicle.VehicleType type,
                         @Param("available") Boolean available,
                         @Param("q") String q);
}
