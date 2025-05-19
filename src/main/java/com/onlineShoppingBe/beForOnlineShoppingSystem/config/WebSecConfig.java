package com.onlineShoppingBe.beForOnlineShoppingSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecConfig {
    @Bean
    public PasswordEncoder passwordEncoder(String password) {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public
}
