package com.JWT.demo.Service;

import com.JWT.demo.Configuration.JWTRequestFilter;
import com.JWT.demo.Model.*;
import com.JWT.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private CartRepo cartRepo;
    public void placeOrder(OrderReq orderReq,boolean isSingleCheckout){

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
                    val.getDiscountPrice() * o.getQuantity(),
                    val,
                    user
            );
            if(!isSingleCheckout){
                List<CartDetails> carts=cartRepo.findByUser(user);
                carts.stream().forEach(x->cartRepo.deleteById(x.getCartId()));
            }
            orderRepo.save(order);
        }
    }

    public List<Order> getOrderDetails() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        RolesList user=userRepo.findByUser(userName);
        return orderRepo.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order updateOrderStatus(Integer orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus("DELIVERED");

        return orderRepo.save(order);
    }
}
