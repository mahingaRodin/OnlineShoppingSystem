package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemDTO {
    private Long userId;
    private String code;
    private int quantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
