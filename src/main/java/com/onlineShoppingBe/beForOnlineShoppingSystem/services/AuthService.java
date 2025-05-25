package com.onlineShoppingBe.beForOnlineShoppingSystem.services;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.UserDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AuthService {
    UserDetailsService userDetailsService();
    UserDTO createUser(UserDTO userDTO);
    Optional<User> findById(Long id);
    User updateUser(Long id, User updatedUser);
}
