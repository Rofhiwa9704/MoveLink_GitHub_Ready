package com.movelink.backend.service;

import com.movelink.backend.dto.PaymentRequest;
import com.movelink.backend.entity.Payment;

public interface PaymentService {

    Payment createPayment(PaymentRequest request);

    Payment completePayment(Long paymentId);

    Payment getPayment(Long paymentId);
}