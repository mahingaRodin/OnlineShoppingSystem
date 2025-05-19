package com.onlineShoppingBe.beForOnlineShoppingSystem.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Purchased> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    // Getters and Setters

}
