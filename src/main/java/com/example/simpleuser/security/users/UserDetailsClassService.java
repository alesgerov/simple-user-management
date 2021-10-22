package com.example.simpleuser.security.users;

import com.example.simpleuser.entity.UserTable;
import com.example.simpleuser.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsClassService implements UserDetailsService {

    private final UserService userService;

    public UserDetailsClassService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<UserTable> userClass = userService.getUserByUsername(s);
        if (userClass.isPresent()) {
            UserTable user = userClass.get();
            return new UserDetailsClass(user);
        } else {
            throw new UsernameNotFoundException("Username tapilmadi");
        }
    }


}
