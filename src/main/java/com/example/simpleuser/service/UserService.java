package com.example.simpleuser.service;

import com.example.simpleuser.entity.UserTable;
import com.example.simpleuser.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isRegistered(String  username){
        return getUserByUsername(username).isPresent();
    }

    public Optional<UserTable> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

}
