package com.JWT.demo.Service;

import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.ProductReq;
import com.JWT.demo.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
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
        if(isSingleOrder){
            List<Product> products=new ArrayList<>();
            Product product=productRepo.findById(productId).get();
            products.add(product);
            return products;
        }else{

        }
        return new ArrayList<>();
    }
}
