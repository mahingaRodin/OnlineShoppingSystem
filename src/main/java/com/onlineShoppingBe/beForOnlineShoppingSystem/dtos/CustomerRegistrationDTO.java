package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import lombok.Data;

@Data
public class CustomerRegistrationDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
}
