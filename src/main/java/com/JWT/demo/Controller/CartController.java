package com.JWT.demo.Controller;

import com.JWT.demo.Model.CartDetails;
import com.JWT.demo.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getCartDetails")
    public List<CartDetails> getCartDetails(){
        return cartService.getCartDetails();
    }
}
