package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
}
