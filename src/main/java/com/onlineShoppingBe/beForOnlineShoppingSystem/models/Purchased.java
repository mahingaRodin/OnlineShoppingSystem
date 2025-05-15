package com.onlineShoppingBe.beForOnlineShoppingSystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "purchased")
public class Purchased {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "code")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
