package com.movelink.backend.service;

import com.movelink.backend.dto.AdminDashboardResponse;
import com.movelink.backend.entity.User;
import org.springframework.data.domain.Page;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.Rating;
import com.movelink.backend.enums.RideStatus;
import java.util.List;

public interface AdminService {

    AdminDashboardResponse getDashboard();

    Page<User> getAllUsers(int page, int size, String sortBy);

    Page<Driver> getAllDrivers(int page, int size, String sortBy);

    Page<Ride> getAllRides(int page, int size, String sortBy);

    List<Rating> getAllRatings();

    List<User> searchUsersByEmail(String email);

    List<Ride> getRidesByStatus(RideStatus status);

    void makeAdmin(String email);
}