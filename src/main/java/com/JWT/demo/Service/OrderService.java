package com.JWT.demo.Service;

import com.JWT.demo.Configuration.JWTRequestFilter;
import com.JWT.demo.Model.*;
import com.JWT.demo.Repository.*;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static String KEY="rzp_test_SU7hwwVhSGNrBT";
    private static String KEY_SECRET="cFDnRg228nQOVlFQt1f0AWPG";
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
                    user,
                    orderReq.getTransactionId()
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

    public List<Order> getAllOrders(String status) {
        if(status.equals("All")){
            return orderRepo.findAll();
        }else{
            return orderRepo.findByOrderStatus(status);
        }
    }

    public Order updateOrderStatus(Integer orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus("DELIVERED");

        return orderRepo.save(order);
    }

    public TransactionDetails createTransaction(Double amount) {
        try {
            if (amount > 500000) {
                throw new RuntimeException("Amount exceeds single transaction limit. Use split payment.");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount)); // paise
            jsonObject.put("currency", "INR");

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
            com.razorpay.Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails details = new TransactionDetails();
            details.setOrderId(order.get("id").toString()); // FIX: use "id"
            details.setCurrency(order.get("currency").toString());
            details.setAmount(order.get("amount"));
            details.setKey(KEY);

            return details;

        } catch (Exception e) {
            throw new RuntimeException("Payment creation failed", e);
        }
    }
    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;

            boolean isValid = Utils.verifySignature(payload, signature, KEY_SECRET);

            return isValid;

        } catch (Exception e) {
            throw new RuntimeException("Payment verification failed");
        }
    }
}
