package com.JWT.demo.Service;

import com.JWT.demo.Model.CartDetails;
import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Model.RolesList;
import com.JWT.demo.Repository.CartRepo;
import com.JWT.demo.Repository.ProductRepo;
import com.JWT.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    UserRepo userRepo;
    public Product createProduct(Product product){
        return productRepo.save(product);
    }
    public Page<Product> getProds(Pageable pageable,String searchKey) {
        if(searchKey.equals("")){
            return productRepo.findAll(pageable);
        }else{
            return productRepo.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(searchKey,searchKey,pageable);
        }

    }
    public void deleteProd(Integer id){
        productRepo.deleteById(id);
    }
    
    public Optional<Product> getProdsById(Integer id){
        return productRepo.findById(id);
    }
    public List<Product> getProdDetails(boolean isSingleOrder,Integer productId){
        if(isSingleOrder && productId!=0){
            List<Product> products=new ArrayList<>();
            Product product=productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product Not Found"));
            products.add(product);
            return products;
        }else{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            RolesList user=userRepo.findByUser(userName);
            List<CartDetails> carts=cartRepo.findByUser(user);
            return carts.stream().map(x->x.getProduct()).collect(Collectors.toList());
        }
    }
}
