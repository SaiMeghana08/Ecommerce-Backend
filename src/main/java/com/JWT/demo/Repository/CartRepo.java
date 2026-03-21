package com.JWT.demo.Repository;

import com.JWT.demo.Model.CartDetails;
import com.JWT.demo.Model.RolesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartDetails,Integer> {
    List<CartDetails> findByUser(RolesList role);
}
