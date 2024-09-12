package com.upao.recordatorios.services;

import com.upao.recordatorios.models.dto.UserDTO;
import com.upao.recordatorios.models.dto.UserRegisterDTO;
import com.upao.recordatorios.models.entitys.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDTO registerUser(UserRegisterDTO userRegisterDTO);
    User getUserByEmail(String email); // Este método debe estar presente
    boolean existsByEmail(String email);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
