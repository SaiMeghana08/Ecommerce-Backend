package com.JWT.demo.Controller;

import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/createProduct")
    public ResponseEntity<ProductReq> createProduct(@RequestBody ProductReq product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
    }
}
