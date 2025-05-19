package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemDTO {
    private Long customerId;
    private Product code;
    private int quantity;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Product getCode() {
        return code;
    }

    public void setCode(Product code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
