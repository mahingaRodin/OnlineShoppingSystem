package com.onlineShoppingBe.beForOnlineShoppingSystem.controllers;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.ProductDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.QuantityDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Quantity;
import com.onlineShoppingBe.beForOnlineShoppingSystem.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(final IProductService productService) {
        this.productService = productService;
    }

    // 1. Register new product
    @PostMapping("/create")
    public ResponseEntity<Product> registerProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.registerProduct(productDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // 2. Add quantity to product
    @PostMapping("/quantity")
    public ResponseEntity<Quantity> addQuantity(@RequestBody QuantityDTO quantityDTO) {
        Quantity quantity = productService.addQuantity(quantityDTO);
        return new ResponseEntity<>(quantity, HttpStatus.CREATED);
    }

    // 3. Get all products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}