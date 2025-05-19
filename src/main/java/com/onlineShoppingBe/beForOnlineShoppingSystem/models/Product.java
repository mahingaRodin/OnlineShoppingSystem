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

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public LocalDate getInDate() {
        return inDate;
    }
    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public List<Quantity> getQuantities() {
        return quantities;
    }
    public void setQuantities(List<Quantity> quantities) {
        this.quantities = quantities;
    }
    public List<Purchased> getPurchases() {
        return purchases;
    }
    public void setPurchases(List<Purchased> purchases) {
        this.purchases = purchases;
    }
}
