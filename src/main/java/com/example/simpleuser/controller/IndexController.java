package com.example.simpleuser.controller;

import com.example.simpleuser.dto.UserDTO;
import com.example.simpleuser.form.RegistrationForm;
import com.example.simpleuser.service.UserService;
import com.example.simpleuser.utils.ShortcutUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class IndexController {

    private final ShortcutUtils shortcutUtils;
    private final UserService userService;

    public IndexController(ShortcutUtils shortcutUtils, UserService userService) {
        this.shortcutUtils = shortcutUtils;
        this.userService = userService;
    }


    @PostMapping("/account/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationForm form,
                                          BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(422).body(shortcutUtils.getValidationErrors(result));
        }
        return ResponseEntity.status(201).body(userService.saveUser(form));
    }
}