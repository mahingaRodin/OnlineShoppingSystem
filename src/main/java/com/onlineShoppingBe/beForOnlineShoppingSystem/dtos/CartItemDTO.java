package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemDTO {
    private Long customerId;
    private String code;
    private int quantity;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
