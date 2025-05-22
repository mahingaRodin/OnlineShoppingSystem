package com.onlineShoppingBe.beForOnlineShoppingSystem.services;

import com.onlineShoppingBe.beForOnlineShoppingSystem.config.WebSecConfig;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.CartItemDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.CustomerRegistrationDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.PurchaseDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.CartItem;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Customer;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Product;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Purchased;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.ICartItemRepo;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.ICustomerRepo;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IProductRepo;
import com.onlineShoppingBe.beForOnlineShoppingSystem.repositories.IPurchaseRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final ICustomerRepo customerRepo;
    private WebSecConfig passwordEncoder;
    private final IProductRepo productRepo;
    private final ICartItemRepo cartItemRepo;
    private final IPurchaseRepo purchaseRepo;

    public CustomerService(
            IPurchaseRepo purchaseRepo,
            IProductRepo productRepo, ICustomerRepo customerRepo, ICartItemRepo cartItemRepo
    ) {
        this.customerRepo = customerRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public Customer registerCustomer(CustomerRegistrationDTO registrationDTO) {
        if (customerRepo.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Customer customer = new Customer();
        customer.setFirstName(registrationDTO.getName());
        customer.setEmail(registrationDTO.getEmail());
        customer.setPhone(registrationDTO.getPhone());
        customer.setPassword(new BCryptPasswordEncoder().encode(registrationDTO.getPassword()));

        return customerRepo.save(customer);
    }

    // 2. Create cart/purchase line
    public CartItem addToCart(CartItemDTO cartItemDTO) {
        Customer customer = customerRepo.findById(cartItemDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepo.findById(cartItemDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item already in cart
        Optional<CartItem> existingItem = cartItemRepo.findById(
                customer.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
            return cartItemRepo.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCustomer(customer);
            newItem.setProduct(product);
            newItem.setQuantity(cartItemDTO.getQuantity());
            return cartItemRepo.save(newItem);
        }
    }

    // 3. Buy product directly
    public Purchased buyProduct(PurchaseDTO purchaseDTO) {
        Customer customer = customerRepo.findByEmail(purchaseDTO.getCustomerEmail())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = (Product) productRepo.findByCode(purchaseDTO.getCode())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Purchased purchased = new Purchased();
        purchased.setCustomer(customer);
        purchased.setProduct(product);
        purchased.setQuantity(purchaseDTO.getQuantity());
        purchased.setTotal(product.getPrice() * purchaseDTO.getQuantity());
        purchased.setPurchaseDate(LocalDateTime.now());

        return purchaseRepo.save(purchased);
    }

    // 4. Add multiple items to cart
    public List<CartItem> addMultipleToCart(List<CartItemDTO> cartItemDTOs) {
        return cartItemDTOs.stream()
                .map(this::addToCart)
                .collect(Collectors.toList());
    }

    // 5. Checkout (convert cart to purchases)
    @Transactional
    public List<Purchased> checkout(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<CartItem> cartItems = cartItemRepo.findByCustomerId(customerId);

        List<Purchased> purchases = cartItems.stream()
                .map(item -> {
                    Purchased purchased = new Purchased();
                    purchased.setCustomer(customer);
                    purchased.setProduct(item.getProduct());
                    purchased.setQuantity(item.getQuantity());
                    purchased.setTotal(item.getProduct().getPrice() * item.getQuantity());
                    purchased.setPurchaseDate(LocalDateTime.now());
                    return purchased;
                })
                .collect(Collectors.toList());

        purchaseRepo.saveAll(purchases);
        cartItemRepo.deleteByCustomerId(customerId);

        return purchases;
    }

}
