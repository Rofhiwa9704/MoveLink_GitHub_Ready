package com.movelink.backend.service;

import com.movelink.backend.dto.PaymentRequest;
import com.movelink.backend.entity.Payment;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.PaymentStatus;
import com.movelink.backend.repository.PaymentRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            RideRepository rideRepository,
            UserRepository userRepository) {

        this.paymentRepository = paymentRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Payment createPayment(PaymentRequest request) {

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Payment payment = Payment.builder()
                .ride(ride)
                .customer(customer)
                .amount(request.getAmount())
                .status(PaymentStatus.PENDING)
                .paymentReference(UUID.randomUUID().toString())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment completePayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}