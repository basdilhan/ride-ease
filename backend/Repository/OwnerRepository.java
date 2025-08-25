package com.rideease.democar_rental.Repository;

import com.rideease.democar_rental.Repository.OwnerRepository;
import com.rideease.democar_rental.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // This allows Spring Security to find an owner by email
    Optional<Owner> findByEmail(String email);
}