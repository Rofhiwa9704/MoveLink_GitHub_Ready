package com.movelink.backend.controller;

import com.movelink.backend.dto.AdminDashboardResponse;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Rating;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(adminService.getAllUsers(page, size, sortBy));
    }

    @GetMapping("/users/search")
public ResponseEntity<List<User>> searchUsersByEmail(
        @RequestParam String email) {

    return ResponseEntity.ok(
            adminService.searchUsersByEmail(email)
    );
}

@GetMapping("/rides/status")
public ResponseEntity<List<Ride>> getRidesByStatus(
        @RequestParam RideStatus status) {

    return ResponseEntity.ok(
            adminService.getRidesByStatus(status)
    );
}

    @GetMapping("/drivers")
    public ResponseEntity<Page<Driver>> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(adminService.getAllDrivers(page, size, sortBy));
    }

    @GetMapping("/rides")
    public ResponseEntity<Page<Ride>> getAllRides(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(adminService.getAllRides(page, size, sortBy));
    }

    @GetMapping("/ratings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(adminService.getAllRatings());
    }
}