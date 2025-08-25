package com.rideease.democar_rental.Service;

import com.rideease.democar_rental.dto.UserRegistration;
import com.rideease.democar_rental.model.Customer;
import com.rideease.democar_rental.model.Owner;
import com.rideease.democar_rental.Repository.CustomerRepository;
import com.rideease.democar_rental.Repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegistration registrationDto) {
        if (registrationDto == null || registrationDto.getRole() == null) {
            throw new IllegalArgumentException("Registration DTO or Role cannot be null.");
        }

        String role = registrationDto.getRole();

        if (customerRepository.findByEmail(registrationDto.getEmail()).isPresent() ||
                ownerRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("Email already exists: " + registrationDto.getEmail());
        }

        if (role.contains("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setName(registrationDto.getName());
            customer.setEmail(registrationDto.getEmail());
            customer.setTele(registrationDto.getPhone());
            customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            customerRepository.save(customer);

        } else if (role.contains("OWNER")) {
            Owner owner = new Owner();
            owner.setName(registrationDto.getName());
            owner.setEmail(registrationDto.getEmail());
            owner.setTele(registrationDto.getPhone());
            owner.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            ownerRepository.save(owner);
        } else {
            throw new IllegalArgumentException("Invalid role specified: " + role);
        }
    }
}