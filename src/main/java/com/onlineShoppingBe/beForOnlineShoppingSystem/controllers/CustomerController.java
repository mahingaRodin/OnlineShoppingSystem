package com.onlineShoppingBe.beForOnlineShoppingSystem.controllers;

import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.CartItemDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.CustomerRegistrationDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.dtos.PurchaseDTO;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.CartItem;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Customer;
import com.onlineShoppingBe.beForOnlineShoppingSystem.models.Purchased;
import com.onlineShoppingBe.beForOnlineShoppingSystem.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 1. Register customer
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        Customer customer = customerService.registerCustomer(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    // 2. Add to cart
    @PostMapping("/cart")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItemDTO cartItemDTO) {
        CartItem item = customerService.addToCart(cartItemDTO);
        return ResponseEntity.ok(item);
    }

    // 3. Direct purchase
    @PostMapping("/purchase")
    public ResponseEntity<Purchased> purchaseProduct(@RequestBody PurchaseDTO purchaseDTO) {
        Purchased purchased = customerService.buyProduct(purchaseDTO);
        return ResponseEntity.ok(purchased);
    }

    // 4. Add multiple to cart
    @PostMapping("/cart/multiple")
    public ResponseEntity<List<CartItem>> addMultipleToCart(@RequestBody List<CartItemDTO> cartItemDTOs) {
        List<CartItem> items = customerService.addMultipleToCart(cartItemDTOs);
        return ResponseEntity.ok(items);
    }

    // 5. Checkout
    @PostMapping("/{customerId}/checkout")
    public ResponseEntity<List<Purchased>> checkout(@PathVariable Long customerId) {
        List<Purchased> purchases = customerService.checkout(customerId);
        return ResponseEntity.ok(purchases);
    }

//    // Get customer cart
//    @GetMapping("/{customerId}/cart")
//    public ResponseEntity<List<CartItem>> getCart(@PathVariable Long customerId) {
//        List<CartItem> cartItems = customerService.getCartItems(customerId);
//        return ResponseEntity.ok(cartItems);
//    }

//    // Get customer purchase history
//    @GetMapping("/{customerId}/purchases")
//    public ResponseEntity<List<Purchased>> getPurchases(@PathVariable Long customerId) {
//        List<Purchased> purchases = customerService.getPurchaseHistory(customerId);
//        return ResponseEntity.ok(purchases);
//    }
}
