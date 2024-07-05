package com.upao.recordatorios.models.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;
}
