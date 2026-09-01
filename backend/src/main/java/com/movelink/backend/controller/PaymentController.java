package com.movelink.backend.controller;

import com.movelink.backend.dto.PaymentRequest;
import com.movelink.backend.entity.Payment;
import com.movelink.backend.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @PutMapping("/{paymentId}/complete")
    public Payment completePayment(@PathVariable Long paymentId) {
        return paymentService.completePayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}