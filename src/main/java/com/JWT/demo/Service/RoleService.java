package com.JWT.demo.Service;

import com.JWT.demo.Model.Role;
import com.JWT.demo.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    RoleRepo repo;
    public Role createRole(Role role){
        return repo.save(role);
    }
}
