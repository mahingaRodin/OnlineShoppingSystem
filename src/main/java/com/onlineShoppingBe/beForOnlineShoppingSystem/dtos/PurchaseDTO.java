package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;

public class PurchaseDTO {
    private String customerEmail;
    private Product code;
    private int quantity;

    public PurchaseDTO(String customerEmail, String productCode, int quantity) {}
    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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
