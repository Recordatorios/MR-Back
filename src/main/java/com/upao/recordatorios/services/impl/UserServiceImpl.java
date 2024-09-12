package com.upao.recordatorios.services.impl;


import com.upao.recordatorios.infra.exceptions.UserAlreadyExistsException;
import com.upao.recordatorios.infra.exceptions.UserNotFoundException;
import com.upao.recordatorios.models.dto.UserDTO;
import com.upao.recordatorios.models.dto.UserRegisterDTO;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.infra.repository.UserRepository;
import com.upao.recordatorios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserRegisterDTO userRegisterDTO) {
        // Validar si el correo ya existe
        if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El correo ya está registrado: " + userRegisterDTO.getEmail());
        }

        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
