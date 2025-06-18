package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuantityRepo extends JpaRepository<Quantity, Long> {
    List<Quantity> findByProductCode(String productCode);
}
