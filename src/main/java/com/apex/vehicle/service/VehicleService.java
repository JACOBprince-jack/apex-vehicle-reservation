package com.apex.vehicle.service;

import com.apex.vehicle.dto.Dto.VehicleRequest;
import com.apex.vehicle.entity.Vehicle;
import com.apex.vehicle.entity.Vehicle.VehicleType;
import com.apex.vehicle.exception.ApexException;
import com.apex.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepo;

    // ── List / Search ────────────────────────────────────────────────────────
    public List<Vehicle> getAll() {
        return vehicleRepo.findAll();
    }

    public List<Vehicle> search(String type, Boolean available, String q) {
        VehicleType vt = null;
        if (type != null && !type.equalsIgnoreCase("all")) {
            vt = VehicleType.valueOf(type.toLowerCase());
        }
        return vehicleRepo.search(vt, available, q == null ? "" : q);
    }

    public Vehicle getById(String id) {
        return vehicleRepo.findById(id)
                .orElseThrow(() -> new ApexException("Vehicle not found: " + id));
    }

    // ── Create ───────────────────────────────────────────────────────────────
    public Vehicle create(VehicleRequest req) {
        if (vehicleRepo.existsById(req.getId())) {
            throw new ApexException("Vehicle ID already exists: " + req.getId());
        }
        return vehicleRepo.save(mapToEntity(new Vehicle(), req));
    }

    // ── Update ───────────────────────────────────────────────────────────────
    public Vehicle update(String id, VehicleRequest req) {
        Vehicle v = getById(id);
        return vehicleRepo.save(mapToEntity(v, req));
    }

    // ── Toggle availability ──────────────────────────────────────────────────
    public Vehicle setAvailability(String id, boolean available) {
        Vehicle v = getById(id);
        v.setAvailable(available);
        return vehicleRepo.save(v);
    }

    // ── Toggle maintenance ───────────────────────────────────────────────────
    public Vehicle setMaintenance(String id, boolean maintenance) {
        Vehicle v = getById(id);
        v.setUnderMaintenance(maintenance);
        if (maintenance) v.setAvailable(false);
        return vehicleRepo.save(v);
    }

    // ── Delete ───────────────────────────────────────────────────────────────
    public void delete(String id) {
        Vehicle v = getById(id);
        vehicleRepo.delete(v);
    }

    // ── Mapper ───────────────────────────────────────────────────────────────
    private Vehicle mapToEntity(Vehicle v, VehicleRequest req) {
        v.setId(req.getId());
        v.setMake(req.getMake());
        v.setModel(req.getModel());
        v.setType(VehicleType.valueOf(req.getType().toLowerCase()));
        v.setYear(req.getYear());
        v.setHorsepower(req.getHorsepower());
        v.setSeats(req.getSeats());
        v.setTransmission(req.getTransmission());
        v.setFuel(req.getFuel());
        v.setPricePerHour(req.getPricePerHour());
        v.setPricePerDay(req.getPricePerDay());
        v.setAvailable(req.isAvailable());
        v.setImageUrl(req.getImageUrl());
        v.setThumbUrl(req.getThumbUrl());
        v.setDescription(req.getDescription());
        return v;
    }
}
