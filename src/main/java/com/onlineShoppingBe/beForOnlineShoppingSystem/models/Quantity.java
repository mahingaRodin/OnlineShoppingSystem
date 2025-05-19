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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
