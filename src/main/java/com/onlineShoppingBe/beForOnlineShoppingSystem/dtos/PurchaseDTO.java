package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

public class PurchaseDTO {
    private String userEmail;
    private String code;
    private int quantity;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
