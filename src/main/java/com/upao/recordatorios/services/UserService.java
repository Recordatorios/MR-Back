package com.upao.recordatorios.services;

import com.upao.recordatorios.models.dto.UserDTO;
import com.upao.recordatorios.models.dto.UserRegisterDTO;

public interface UserService {
    UserDTO registerUser(UserRegisterDTO userRegisterDTO);
    UserDTO getUserByEmail(String email);
}
