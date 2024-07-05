package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.dto.UserDTO;
import com.upao.recordatorios.models.dto.UserRegisterDTO;
import com.upao.recordatorios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.registerUser(userRegisterDTO);
    }
}
