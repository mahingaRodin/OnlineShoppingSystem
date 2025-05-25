package com.onlineShoppingBe.beForOnlineShoppingSystem.controllers;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.AuthenticationRequest;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.AuthenticationResponse;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.UserDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IUserRepository;
import com.onlineShoppingBe.beForOnlineShoppingSystem.services.AuthService;
import com.onlineShoppingBe.beForOnlineShoppingSystem.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil util;

    public AuthController(AuthService authService, IUserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil util) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.util = util;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO signupRequest) {
        try {
            UserDTO createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>("User Already Exists!", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Created. Please try again later", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(authService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        return ResponseEntity.ok(authService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest user) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch(BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        final UserDetails userDetails = authService.userDetailsService().loadUserByUsername(user.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        final String token = util.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();


        if(optionalUser.isPresent()) {
            authenticationResponse.setToken(token);
            authenticationResponse.setUserRole(optionalUser.get().getRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return ResponseEntity.ok(authenticationResponse).getBody();
    }
}
