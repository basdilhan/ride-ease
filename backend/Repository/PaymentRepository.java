package com.rideease.democar_rental.Repository;

import com.rideease.democar_rental.model.Booking;
import com.rideease.democar_rental.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBooking(Booking booking);
}