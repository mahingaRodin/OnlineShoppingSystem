package com.onlineShoppingBe.beForOnlineShoppingSystem.services;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.ProductDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.QuantityDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;

import java.util.List;

public interface IProductService {
    Product registerProduct(ProductDTO productDTO);
    boolean addQuantity(QuantityDTO quantityDTO);
    List<Product> getAllProducts();
}
