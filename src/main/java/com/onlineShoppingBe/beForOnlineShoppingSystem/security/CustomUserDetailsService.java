package com.onlineShoppingBe.beForOnlineShoppingSystem.security;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Customer;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ICustomerRepository customerRepository;

    public CustomUserDetailsService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with: " + email));

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
        );
    }
}
