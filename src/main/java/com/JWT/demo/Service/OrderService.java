package com.JWT.demo.Service;

import com.JWT.demo.Configuration.JWTRequestFilter;
import com.JWT.demo.Model.*;
import com.JWT.demo.Repository.OrderRepo;
import com.JWT.demo.Repository.ProductRepo;
import com.JWT.demo.Repository.RoleRepo;
import com.JWT.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.JWT.demo.Configuration.JWTRequestFilter.UserLogin;

@Service
public class OrderService {
    private static String ORDER_PLACED="PLACED";
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    public void placeOrder(OrderReq orderReq){

        List<OrderQuantity> productQuantityList = orderReq.getOrderQuantities();

        for(OrderQuantity o : productQuantityList){

            System.out.println("Product ID: " + o.getProductId());

            Optional<Product> productOpt = productRepo.findById(o.getProductId());

            if(productOpt.isEmpty()){
                throw new RuntimeException("Product not found with id: " + o.getProductId());
            }

            Product val = productOpt.get();

            String userName = JWTRequestFilter.UserLogin;

            RolesList user = userRepo.findByUser(userName);

            Order order = new Order(
                    orderReq.getFullName(),
                    orderReq.getFullAddress(),
                    orderReq.getContactNumber(),
                    orderReq.getAlternateContactNumber(),
                    ORDER_PLACED,
                    val.getDiscount_price() * o.getQuantity(),
                    val,
                    user
            );

            orderRepo.save(order);
        }
    }
}
