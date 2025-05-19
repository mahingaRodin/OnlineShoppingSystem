package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
