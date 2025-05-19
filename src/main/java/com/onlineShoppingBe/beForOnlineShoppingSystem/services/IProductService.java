package com.onlineShoppingBe.beForOnlineShoppingSystem.services;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.ProductDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.QuantityDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Quantity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Product registerProduct(ProductDTO productDTO);
    Quantity addQuantity(QuantityDTO quantityDTO);
    List<Product> getAllProducts();
}
