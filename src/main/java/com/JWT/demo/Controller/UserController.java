package com.JWT.demo.Controller;

import com.JWT.demo.Model.RolesList;
import com.JWT.demo.Repository.UserRepo;
import com.JWT.demo.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    UserRepo repo;
    @PostConstruct
    public void initUsers(){

        service.createUsers();
    }

    @GetMapping("/Users")
    public List<RolesList> getUsers(){
        return repo.findAll();
    }

    //@PreAuthorize("hasRole('User')") --> "ROLE_USER"
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('User')")
    public String getUserAcess(){
        return "User Access";
    }

    //@PreAuthorize("hasRole('Admin')") --> "ROLE_ADMIN"
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public String getAdminAcess(){
        return "Admin Access";
    }

    //Authorities
    @GetMapping("/check")
    public Object checkAuth() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @PostMapping("/registerUser")
    public void registerUser(@RequestBody RolesList User){
        service.registerUser(User);
    }
}
