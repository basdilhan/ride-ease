package com.rideease.democar_rental.Service;

import com.rideease.democar_rental.model.Customer;
import com.rideease.democar_rental.Repository.CustomerRepository;
import com.rideease.democar_rental.Repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // This check handles the blank email problem
        if (email == null || email.isBlank()) {
            System.err.println("--- [LOGIN FAILED] Email parameter was empty or null. ---");
            throw new UsernameNotFoundException("Email cannot be empty.");
        }

        System.out.println("--- [LOGIN ATTEMPT] Searching for user with email: [" + email + "] ---");

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            System.out.println("--- [SUCCESS] Found user in 'customer' table. ---");
            Customer customer = customerOptional.get();
            return new User(
                    customer.getEmail(),
                    customer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        return ownerRepository.findByEmail(email)
                .map(owner -> {
                    System.out.println("--- [SUCCESS] Found user in 'owner' table. ---");
                    return new User(
                            owner.getEmail(),
                            owner.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER")));
                })
                .orElseThrow(() -> {
                    System.err.println("--- [LOGIN FAILED] User not found in any table for email: " + email + " ---");
                    return new UsernameNotFoundException("User not found with email: " + email);
                });
    }
}