package com.JWT.demo.Service;

import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    public Product createProduct(Product product){
        return productRepo.save(product);
    }

    public List<Product> getProds() {
        return productRepo.findAll();
    }
    public void deleteProd(Integer id){
        productRepo.deleteById(id);
    }
    
    public Optional<Product> getProdsById(Integer id){
        return productRepo.findById(id);
    }
}
