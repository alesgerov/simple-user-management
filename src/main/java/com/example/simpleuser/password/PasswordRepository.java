package com.example.simpleuser.password;


public interface PasswordRepository {
    String hashPassword(String rawPassword);
    boolean checkPassword(String hashedPassword, String rawPassword);
}

