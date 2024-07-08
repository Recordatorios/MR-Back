package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.dto.LoginRequest;
import com.upao.recordatorios.models.dto.TokenResponse;
import com.upao.recordatorios.infra.security.JwtService;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(loginRequest.getEmail()); // Asegúrate de que tienes este método en tu servicio
        String token = jwtService.getToken(userDetails, user);
        return new TokenResponse(token);
    }
}
