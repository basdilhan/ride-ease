package com.rideease.democar_rental.Repository;

import com.rideease.democar_rental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // This allows Spring Security to find a user by email
    Optional<Customer> findByEmail(String email);
}