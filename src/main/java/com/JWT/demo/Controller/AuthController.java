package com.JWT.demo.Controller;


import com.JWT.demo.Configuration.JwtUtil;
import com.JWT.demo.Model.AuthRequest;
import com.JWT.demo.Model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // 1️⃣ Authenticate username & password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // 2️⃣ Load user details (optional if you need roles)
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // 3️⃣ Generate JWT token
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // 4️⃣ Return the token to the client
        AuthResponse res=new AuthResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
        return res;
    }
}