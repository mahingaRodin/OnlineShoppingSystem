package com.onlineShoppingBe.beForOnlineShoppingSystem.serviceImpl;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.UserDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.enums.UserRole;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IUserRepository;
import com.onlineShoppingBe.beForOnlineShoppingSystem.services.AuthService;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {
    private final IUserRepository userRepository;

    public AuthServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetailsService userDetailsService() {
        return username -> (
            UserDetails
        ) userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists");
        }
        User u = new User();
        u.setEmail(userDTO.getEmail());
        u.setName(userDTO.getName());
        u.setPhone(userDTO.getPhone());
        u.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        u.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(u);
        return createdUser.getUserDTO();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }
}
