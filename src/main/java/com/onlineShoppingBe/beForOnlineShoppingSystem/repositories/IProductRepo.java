package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepo extends JpaRepository<Product, Long> {

    Optional<Object> findByCode(Product code);
}

