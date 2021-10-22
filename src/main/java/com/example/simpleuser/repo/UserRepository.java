package com.example.simpleuser.repo;

import com.example.simpleuser.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserTable,Long> {
    Optional<UserTable> findByUsername(String  username);
}
