package com.rideease.democar_rental.Repository;

import com.rideease.democar_rental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    // NEW: This method will find all cars where the model name contains the search keyword (case-insensitive)
    List<Car> findByCarModelContainingIgnoreCase(String keyword);
}