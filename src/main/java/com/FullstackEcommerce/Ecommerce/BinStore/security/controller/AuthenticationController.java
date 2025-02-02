package com.FullstackEcommerce.Ecommerce.BinStore.security.controller;

import com.FullstackEcommerce.Ecommerce.BinStore.security.user.AuthenticationRequest;
import com.FullstackEcommerce.Ecommerce.BinStore.security.user.User;
import com.FullstackEcommerce.Ecommerce.BinStore.security.repo.UserRepository;
import com.FullstackEcommerce.Ecommerce.BinStore.security.service.CustomUserDetailsService;
import com.FullstackEcommerce.Ecommerce.BinStore.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        User user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new Exception("User not found"));

        // Manually check password
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}