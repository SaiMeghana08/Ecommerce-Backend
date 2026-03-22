package com.JWT.demo.Controller;

import com.JWT.demo.Model.Order;
import com.JWT.demo.Model.OrderReq;
import com.JWT.demo.Model.Product;
import com.JWT.demo.Model.TransactionDetails;
import com.JWT.demo.Service.OrderService;
import com.JWT.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/getAllOrders/{status}")
    public List<Order> getAllOrders(@PathVariable String status){
         return orderService.getAllOrders(status);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PatchMapping("/updateOrderStatus/{orderId}")
    public Order updateOrderStatus(@PathVariable Integer orderId) {
        return orderService.updateOrderStatus(orderId);
    }

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/create")
    public TransactionDetails createOrder(@RequestParam Double amount) {
        System.out.println("CREATE API HIT");
        return orderService.createTransaction(amount);
    }

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) {

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        boolean isValid = orderService.verifyPayment(orderId, paymentId, signature);

        if (isValid) {
            return "Payment Success ✅";
        } else {
            return "Payment Failed ❌";
        }
    }
}
