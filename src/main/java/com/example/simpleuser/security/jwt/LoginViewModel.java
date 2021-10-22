package com.example.simpleuser.security.jwt;

import lombok.Data;

@Data
public class LoginViewModel {
    private String username;
    private String password;
}
