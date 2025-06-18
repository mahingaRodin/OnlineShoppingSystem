package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import com.onlineShoppingBe.beForOnlineShoppingSystem.enums.UserRole;

public class AuthenticationResponse {
    private String token;
    private Long userId;
    private UserRole userRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
