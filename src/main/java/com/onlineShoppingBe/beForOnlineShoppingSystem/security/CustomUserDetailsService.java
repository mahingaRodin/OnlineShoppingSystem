package com.onlineShoppingBe.beForOnlineShoppingSystem.security;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.ICustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        User user = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with: " + email));

        String role = String.valueOf(user.getRole());

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ role);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
