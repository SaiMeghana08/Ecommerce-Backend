package com.JWT.demo.Controller;

import com.JWT.demo.Model.CartDetails;
import com.JWT.demo.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/addtoCart/{productId}")
    public CartDetails addtocart(@PathVariable Integer productId) {
        return cartService.addtocart(productId);
    }
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/getCartDetails")
    public List<CartDetails> getCartDetails(){
        return cartService.getCartDetails();
    }

    @PreAuthorize("hasAuthority('User')")
    @DeleteMapping("/deleteCart/{cartId}")
    public void deleteCartItem(@PathVariable Integer cartId){
        cartService.deleteCartItem(cartId);
    }
}
