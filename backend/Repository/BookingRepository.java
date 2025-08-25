package com.rideease.democar_rental.Repository;

import com.rideease.democar_rental.model.Booking;
import com.rideease.democar_rental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomer(Customer customer);

    /**
     This query checks for existing bookings for a specific car that overlap
     * with the requested new pickup and return dates.
     */
    @Query("SELECT b FROM Booking b WHERE b.car.carId = :carId AND b.returnDate > :pickupDate AND b.pickupDate < :returnDate")
    List<Booking> findOverlappingBookings(@Param("carId") Long carId, @Param("pickupDate") LocalDateTime pickupDate, @Param("returnDate") LocalDateTime returnDate);


    }

