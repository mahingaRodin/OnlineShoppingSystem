package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Purchased;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPurchaseRepo extends JpaRepository<Purchased,Long> {
    List<Purchased> findByCustomerId(Long customerId);
}
