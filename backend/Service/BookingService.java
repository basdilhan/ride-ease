package com.rideease.democar_rental.Service;

import com.rideease.democar_rental.dto.BookingDto;
import com.rideease.democar_rental.model.Booking;
import com.rideease.democar_rental.model.Car;
import com.rideease.democar_rental.model.Customer;
import com.rideease.democar_rental.model.Payment;
import com.rideease.democar_rental.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private CustomerRepository customerRepository;

    @Transactional
    public void createBooking(BookingDto bookingDto) {
        //  VALIDATION LOGIC TO PREVENT DOUBLE BOOKING ---
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                bookingDto.getCarId(),
                bookingDto.getPickupDate(),
                bookingDto.getReturnDate()
        );

        if (!overlappingBookings.isEmpty()) {
            // If the list is not empty, it means the car is already booked for that time.
            throw new IllegalStateException("Sorry, this car is already booked for the selected dates.");
        }


        // Get the currently logged-in user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Car car = carRepository.findById(bookingDto.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Update car status to "Booked"
        car.setStatus("Booked");
        carRepository.save(car);

        // Calculate total amount
        long hours = Duration.between(bookingDto.getPickupDate(), bookingDto.getReturnDate()).toHours();
        // Ensure at least one day is charged for short rentals
        double days = Math.max(1, Math.ceil(hours / 24.0));
        double totalAmount = days * car.getPrice();

        // 1. Create and save the booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCar(car);
        booking.setPickupDate(bookingDto.getPickupDate());
        booking.setReturnDate(bookingDto.getReturnDate());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");
        Booking savedBooking = bookingRepository.save(booking);

        // 2. Create and save the simulated payment
        Payment payment = new Payment();
        payment.setBooking(savedBooking);
        payment.setAmount(totalAmount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus("PAID");
        paymentRepository.save(payment);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> findBookingsForCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByEmail(email)
                .map(bookingRepository::findByCustomer)
                .orElse(new ArrayList<>());
    }
    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("Booking not found with ID: " + bookingId));

        paymentRepository.findByBooking(booking).ifPresent(payment -> {
            paymentRepository.delete(payment);
        });

        Car car = booking.getCar();
        if (car != null) {
            car.setStatus("Available");
            carRepository.save(car);
        }

        bookingRepository.delete(booking);
    }
}