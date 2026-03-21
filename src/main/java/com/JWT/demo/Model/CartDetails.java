package com.JWT.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    @ManyToOne
    private Product product;
    @ManyToOne
    private RolesList user;

    public CartDetails(Product product, RolesList user) {
        this.product=product;
        this.user=user;
    }
}
