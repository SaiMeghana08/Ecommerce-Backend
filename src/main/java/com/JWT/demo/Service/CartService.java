package com.JWT.demo.Service;

import com.JWT.demo.Configuration.JWTRequestFilter;
import com.JWT.demo.Model.CartDetails;
import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.RolesList;
import com.JWT.demo.Repository.CartRepo;
import com.JWT.demo.Repository.ProductRepo;
import com.JWT.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;


    public  CartDetails addtocart(Integer ProductId){
        Product product=productRepo.findById(ProductId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        RolesList user=userRepo.findByUser(userName);
        List<CartDetails> carts=cartRepo.findByUser(user);
        List<CartDetails> filteredcart=carts.stream().filter(x->x.getProduct().getProductId()==ProductId).collect(Collectors.toList());
        if(filteredcart.size()>0){
            return null;
        }
        if(userName!=null && product!=null) {
            CartDetails cart = new CartDetails(
                    product,
                    user
            );
            return cartRepo.save(cart);
        }
        return null;
    }

    public List<CartDetails> getCartDetails(){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        RolesList role=userRepo.findByUser(user);
        return cartRepo.findByUser(role);
    }
    public void deleteCartItem(Integer cartId){
        cartRepo.deleteById(cartId);
    }
}
