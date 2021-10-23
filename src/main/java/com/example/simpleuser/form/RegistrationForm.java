package com.example.simpleuser.form;


import com.example.simpleuser.validator.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

    @UniqueUsername
    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    private String username;

    @Size(min = 8,message = "En az 8 simvol olmalidir")
    @NotBlank(message = "Şifrə boş ola bilməz")
    private String  password;
}

