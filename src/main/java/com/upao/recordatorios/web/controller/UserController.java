package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.dto.UserDTO;
import com.upao.recordatorios.models.dto.UserRegisterDTO;
import com.upao.recordatorios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        // Verificar si el correo electrónico ya está registrado
        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado. Por favor, usa otro correo electrónico.");
        }

        // Proceder con el registro del usuario
        UserDTO registeredUser = userService.registerUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
