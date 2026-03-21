package com.JWT.demo.Repository;

import com.JWT.demo.Model.Order;
import com.JWT.demo.Model.RolesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    List<Order> findByUser(RolesList user);
}
