package com.example.simpleuser.form;


import com.example.simpleuser.validator.UniqueUsername;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String  password;
}

