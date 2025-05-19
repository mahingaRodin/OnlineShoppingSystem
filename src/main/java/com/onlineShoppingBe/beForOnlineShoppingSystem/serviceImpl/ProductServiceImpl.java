package com.onlineShoppingBe.beForOnlineShoppingSystem.serviceImpl;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.ProductDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.QuantityDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Quantity;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IProductRepo;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IQuantityRepo;
import com.onlineShoppingBe.beForOnlineShoppingSystem.services.IProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    private final IProductRepo productRepo;

    IQuantityRepo quantityRepo;

    public ProductServiceImpl(
            IProductRepo productRepo,
            IQuantityRepo quantityRepo
    ) {
    this.quantityRepo = quantityRepo;
    this.productRepo = productRepo;
    }

    @Override
    public Product registerProduct(ProductDTO productDTO) {
        Product p = new Product();
        p.setCode(productDTO.getCode());
        p.setName(productDTO.getName());
        p.setProductType(productDTO.getProductType());
        p.setPrice(productDTO.getPrice());
        p.setInDate(productDTO.getInDate());
        p.setImage(productDTO.getImage());

        return productRepo.save(p);
    }

    @Override
    public Quantity addQuantity(QuantityDTO quantityDTO) {
        Product product = productRepo.findById(Long.valueOf(quantityDTO.getProductCode()))
                .orElseThrow(()-> new RuntimeException("Product Not Found"));

        Quantity q = new Quantity();
        q.setProduct(product);
        q.setQuantity(quantityDTO.getQuantity());
        q.setOperation(quantityDTO.getOperation());
        q.setDate(LocalDateTime.now());

        return quantityRepo.save(q);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

}
