package com.onlineShoppingBe.beForOnlineShoppingSystem.config;

import com.onlineShoppingBe.beForOnlineShoppingSystem.enums.UserRole;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataInitializer implements CommandLineRunner {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByEmail("mahingarodin@gmail.com")) {
            User admin = new User();
            admin.setEmail("mahingarodin@gmail.com");
            admin.setName("Mahinga Rodin");
            admin.setPhone("0793216126");
            admin.setPassword(passwordEncoder.encode("Admin!132"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }
}
