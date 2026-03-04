package com.JWT.demo.Model;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthResponse {

        private String token;
        private String name;
        private String role;

    public AuthResponse(String jwt, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = jwt;
        this.name = username;

        // Extract role from authorities
        this.role = authorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
// constructor + getters
}

