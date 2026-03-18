package com.apex.vehicle.controller;

import com.apex.vehicle.dto.Dto.VehicleRequest;
import com.apex.vehicle.entity.Vehicle;
import com.apex.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    /** GET /api/vehicles?type=car&available=true&q=bmw */
    @GetMapping
    public ResponseEntity<List<Vehicle>> list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(vehicleService.search(type, available, q));
    }

    /** GET /api/vehicles/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> get(@PathVariable String id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    /** POST /api/vehicles  — Admin only */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> create(@Valid @RequestBody VehicleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(req));
    }

    /** PUT /api/vehicles/{id}  — Admin only */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> update(@PathVariable String id,
                                          @Valid @RequestBody VehicleRequest req) {
        return ResponseEntity.ok(vehicleService.update(id, req));
    }

    /** PATCH /api/vehicles/{id}/availability  — Admin only */
    @PatchMapping("/{id}/availability")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> setAvailability(@PathVariable String id,
                                                   @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(vehicleService.setAvailability(id, body.get("available")));
    }

    /** PATCH /api/vehicles/{id}/maintenance  — Admin only */
    @PatchMapping("/{id}/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> setMaintenance(@PathVariable String id,
                                                  @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(vehicleService.setMaintenance(id, body.get("underMaintenance")));
    }

    /** DELETE /api/vehicles/{id}  — Admin only */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
