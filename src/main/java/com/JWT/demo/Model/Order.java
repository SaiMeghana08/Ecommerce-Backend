package com.JWT.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderId;
    private String orderFullName;
    private String orderFullAddress;
    private String orderContactNumber;
    private String orderAlternateNumber;
    private String orderStatus;
    private Double orderAmount;
    private String transactionId;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private RolesList user;


    public Order(String fullName, String fullAddress, String contactNumber, String alternateContactNumber, String orderPlaced, double v, Product val, RolesList user,String transactionId) {
        this.orderFullName=fullName;
        this.orderFullAddress=fullAddress;
        this.orderContactNumber=contactNumber;
        this.orderAlternateNumber=alternateContactNumber;
        this.orderAmount=v;
        this.orderStatus=orderPlaced;
        this.product=val;
        this.user=user;
        this.transactionId=transactionId;
    }
}
