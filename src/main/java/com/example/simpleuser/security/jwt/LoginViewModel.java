package com.example.simpleuser.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginViewModel {
    @NotBlank(message = "Istifadeci adi bos ola bilmez")
    private String username;

    @Size(min = 8, message = "En az 8 simvol olmalidir")
    @NotBlank(message = "Sifre bos ola bilmez")
    private String password;
}
