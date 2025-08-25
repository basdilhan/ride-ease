package com.rideease.democar_rental.Service;

import com.rideease.democar_rental.model.Payment;
import com.rideease.democar_rental.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Calculates the sum of all paid amounts
    public double calculateTotalEarnings() {
        return paymentRepository.findAll().stream()
                .filter(p -> "PAID".equalsIgnoreCase(p.getPaymentStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    // Counts the number of completed transactions
    public long countCompletedTransactions() {
        return paymentRepository.findAll().stream()
                .filter(p -> "PAID".equalsIgnoreCase(p.getPaymentStatus()))
                .count();
    }
}