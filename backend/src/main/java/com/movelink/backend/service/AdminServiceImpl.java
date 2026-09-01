package com.movelink.backend.service;

import com.movelink.backend.dto.AdminDashboardResponse;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Rating;
import com.movelink.backend.entity.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.enums.TicketStatus;
import com.movelink.backend.enums.UserRole;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.PaymentRepository;
import com.movelink.backend.repository.RatingRepository;
import com.movelink.backend.repository.ReviewRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.SupportTicketRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;
    private final RatingRepository ratingRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final SupportTicketRepository supportTicketRepository;

    public AdminServiceImpl(
            UserRepository userRepository,
            DriverRepository driverRepository,
            RideRepository rideRepository,
            RatingRepository ratingRepository,
            PaymentRepository paymentRepository,
            ReviewRepository reviewRepository,
            SupportTicketRepository supportTicketRepository) {

        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.ratingRepository = ratingRepository;
        this.paymentRepository = paymentRepository;
        this.reviewRepository = reviewRepository;
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    public AdminDashboardResponse getDashboard() {

        Double revenue = paymentRepository.getTotalRevenue();

        if (revenue == null) {
            revenue = 0.0;
        }

        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalDrivers(driverRepository.count())
                .verifiedDrivers(driverRepository.countByVerifiedTrue())
                .availableDrivers(driverRepository.countByAvailableTrue())
                .totalRides(rideRepository.count())
                .pendingRides(rideRepository.countByStatus(RideStatus.PENDING))
                .acceptedRides(rideRepository.countByStatus(RideStatus.ACCEPTED))
                .inProgressRides(rideRepository.countByStatus(RideStatus.IN_PROGRESS))
                .completedRides(rideRepository.countByStatus(RideStatus.COMPLETED))
                .cancelledRides(rideRepository.countByStatus(RideStatus.CANCELLED))
                .totalPayments(paymentRepository.count())
                .totalRevenue(revenue)
                .totalRatings(ratingRepository.count())
                .totalReviews(reviewRepository.count())
                .openSupportTickets(
                        supportTicketRepository.countByStatus(TicketStatus.OPEN))
                .build();
    }

    @Override
public Page<User> getAllUsers(int page, int size, String sortBy) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortBy).ascending()
    );

    return userRepository.findAll(pageable);
}

@Override
public Page<Driver> getAllDrivers(int page, int size, String sortBy) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortBy).ascending()
    );

    return driverRepository.findAll(pageable);
}

@Override
public Page<Ride> getAllRides(int page, int size, String sortBy) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortBy).ascending()
    );

    return rideRepository.findAll(pageable);
}

@Override
public List<Ride> getRidesByStatus(RideStatus status) {
    return rideRepository.findByStatus(status);
}

@Override
public List<User> searchUsersByEmail(String email) {
    return userRepository.findByEmailContainingIgnoreCase(email);
}

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public void makeAdmin(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(UserRole.ADMIN);

        userRepository.save(user);
    }
}