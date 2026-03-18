package com.apex.vehicle.service;

import com.apex.vehicle.config.JwtUtil;
import com.apex.vehicle.dto.Dto.*;
import com.apex.vehicle.entity.User;
import com.apex.vehicle.exception.ApexException;
import com.apex.vehicle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    // ── Register ─────────────────────────────────────────────────────────────
    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new ApexException("Email already registered");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail().toLowerCase());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        // First registered user with admin email gets ADMIN role
        if (req.getEmail().equalsIgnoreCase("admin@apex.com") ||
            req.getEmail().equalsIgnoreCase("dealer@apex.com")) {
            user.setRole(User.Role.ADMIN);
        }
        userRepo.save(user);

        return buildResponse(user);
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail().toLowerCase())
                .orElseThrow(() -> new ApexException("Invalid email or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new ApexException("Invalid email or password");
        }

        return buildResponse(user);
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private AuthResponse buildResponse(User user) {
        AuthResponse res = new AuthResponse();
        res.setToken(jwtUtil.generate(user.getEmail()));
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        res.setUserId(user.getId());
        return res;
    }
}
