package com.JWT.demo.Controller;

import com.JWT.demo.Model.Order;
import com.JWT.demo.Model.OrderReq;
import com.JWT.demo.Model.Product;
import com.JWT.demo.Service.OrderService;
import com.JWT.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/placeOrder/{isSingleOrder}")
    public void placeOrder(@RequestBody OrderReq orderReq, @PathVariable boolean isSingleOrder){
        orderService.placeOrder(orderReq,isSingleOrder);
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/getOrderDetails")
    public List<Order> getOrderDetails(){
        return orderService.getOrderDetails();
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders(){
         return orderService.getAllOrders();
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PatchMapping("/updateOrderStatus/{orderId}")
    public Order updateOrderStatus(@PathVariable Integer orderId) {
        return orderService.updateOrderStatus(orderId);
    }
}
