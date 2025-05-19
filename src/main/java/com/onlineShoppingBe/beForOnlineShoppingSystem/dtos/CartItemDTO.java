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
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Product getCode() {
        return code;
    }
    public void setId(Product code) {
        this.code = code;
    }
}
