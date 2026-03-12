package com.JWT.demo.Controller;

import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductImages;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(value="/createProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(@RequestPart("product") Product product, @RequestPart("Image")MultipartFile[] file){
        try{
            Set<ProductImages> images=addImage(file);
            product.setProductImages(images);
            productService.createProduct(product);
        }catch(Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public Set<ProductImages> addImage(MultipartFile[] files) throws IOException {
        Set<ProductImages> images=new HashSet<>();
        for(MultipartFile file:files){
            ProductImages image=new ProductImages(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            images.add(image);
        }
        return images;
    }
    @GetMapping("/getProd")
    public List<Product> getAllProducts(){
        return productService.getProds();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProd(@PathVariable("id")Integer id){
        productService.deleteProd(id);
    }
}
