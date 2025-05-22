package com.onlineShoppingBe.beForOnlineShoppingSystem.repositories;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartItemRepo extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
}
