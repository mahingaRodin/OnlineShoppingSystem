package com.onlineShoppingBe.beForOnlineShoppingSystem.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quantities")
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "code")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "operation", nullable = false)
    private String operation; // e.g., "ADD", "REMOVE", "ADJUST"

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
