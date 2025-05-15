package com.onlineShoppingBe.beForOnlineShoppingSystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "in_date", nullable = false)
    private LocalDate inDate;

    @Column(name = "image")
    private String image;
    // This could be a path to the image file or a Base64 encoded string
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Quantity> quantities = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Purchased> purchases = new ArrayList<>();
}
