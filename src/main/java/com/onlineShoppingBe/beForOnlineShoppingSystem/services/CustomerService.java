package com.onlineShoppingBe.beForOnlineShoppingSystem.services;

import com.onlineShoppingBe.beForOnlineShoppingSystem.config.WebSecConfig;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.CartItemDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.UserDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.PurchaseDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.CartItem;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.User;
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

    public User registerCustomer(UserDTO registrationDTO) {
        if (customerRepo.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setPhone(registrationDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationDTO.getPassword()));

        return customerRepo.save(user);
    }

    // 2. Create cart/purchase line
    public CartItem addToCart(CartItemDTO cartItemDTO) {
        User user = customerRepo.findById(cartItemDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(cartItemDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item already in cart
        Optional<CartItem> existingItem = cartItemRepo.findById(
                user.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
            return cartItemRepo.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(cartItemDTO.getQuantity());
            return cartItemRepo.save(newItem);
        }
    }

    // 3. Buy product directly
    public Purchased buyProduct(PurchaseDTO purchaseDTO) {
        User user = customerRepo.findByEmail(purchaseDTO.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = (Product) productRepo.findByCode(purchaseDTO.getCode())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Purchased purchased = new Purchased();
        purchased.setUser(user);
        purchased.setProduct(product);
        purchased.setQuantity(purchaseDTO.getQuantity());
        purchased.setTotal(product.getPrice() * purchaseDTO.getQuantity());
        purchased.setDate(LocalDateTime.now());
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
        User user = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepo.findByUserId(customerId);

        List<Purchased> purchases = cartItems.stream()
                .map(item -> {
                    Purchased purchased = new Purchased();
                    purchased.setUser(user);
                    purchased.setProduct(item.getProduct());
                    purchased.setQuantity(item.getQuantity());
                    purchased.setTotal(item.getProduct().getPrice() * item.getQuantity());
                    purchased.setPurchaseDate(LocalDateTime.now());
                    purchased.setDate(LocalDateTime.now());
                    return purchased;
                })
                .collect(Collectors.toList());

        purchaseRepo.saveAll(purchases);
        cartItemRepo.deleteByUserId(customerId);

        return purchases;
    }

}
