package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String code;
    private String name;
    private String productType;
    private double price;
    private LocalDate inDate;
    private String image;

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
}
