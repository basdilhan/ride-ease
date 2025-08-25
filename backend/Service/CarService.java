package com.rideease.democar_rental.Service;

import com.rideease.democar_rental.model.Car;
import com.rideease.democar_rental.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // NEW: This method searches for cars based on a keyword
    public List<Car> searchCars(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return carRepository.findByCarModelContainingIgnoreCase(keyword);
        }
        // If no keyword, return all cars
        return carRepository.findAll();
    }
    public Optional<Car> findCarById(Long id) {
        return carRepository.findById(id);
    }

    // saveCar handles both creating a new car and updating an existing one
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}