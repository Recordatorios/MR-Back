package com.upao.recordatorios.infra.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
