package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.dto.LoginRequest;
import com.upao.recordatorios.models.dto.TokenResponse;
import com.upao.recordatorios.infra.security.JwtService;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Verificar si el correo no está registrado
        if (!userService.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El correo electrónico no está registrado.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.getUserByEmail(loginRequest.getEmail());
            String token = jwtService.getToken(userDetails, user);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas. Inténtalo nuevamente.");
        }
    }
}
