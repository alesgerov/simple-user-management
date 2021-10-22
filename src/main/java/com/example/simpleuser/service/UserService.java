package com.example.simpleuser.service;

import com.example.simpleuser.dto.UserDTO;
import com.example.simpleuser.entity.UserTable;
import com.example.simpleuser.form.RegistrationForm;
import com.example.simpleuser.password.PasswordService;
import com.example.simpleuser.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordService passwordService;

    public UserService(UserRepository repository, PasswordService passwordService) {
        this.repository = repository;
        this.passwordService = passwordService;
    }

    public boolean isRegistered(String  username){
        return getUserByUsername(username).isPresent();
    }

    public Optional<UserTable> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserDTO saveUser(RegistrationForm form){
        UserTable table=new UserTable();
        String hashedPw = passwordService.hashPassword(form.getPassword());
        table.setPassword(hashedPw);
        table.setUsername(form.getUsername());
        table=repository.save(table);
        if (table!=null){
            return new UserDTO(form.getUsername());
        }
        return null;
    }

}
