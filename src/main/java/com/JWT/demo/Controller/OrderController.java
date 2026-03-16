package com.JWT.demo.Controller;

import com.JWT.demo.Model.OrderReq;
import com.JWT.demo.Model.Product;
import com.JWT.demo.Service.OrderService;
import com.JWT.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

//    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderReq orderReq){
        orderService.placeOrder(orderReq);
    }
}
