package com.JWT.demo.Service;

import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    public ProductReq createProduct(ProductReq product){
        return productRepo.save(product);
    }
}
