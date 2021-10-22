package com.example.simpleuser.controller;

import com.example.simpleuser.form.RegistrationForm;
import com.example.simpleuser.service.UserService;
import com.example.simpleuser.utils.ShortcutUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class IndexController {

    private final ShortcutUtils shortcutUtils;
    private final UserService userService;

    public IndexController(ShortcutUtils shortcutUtils, UserService userService) {
        this.shortcutUtils = shortcutUtils;
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationForm form,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(422).body(shortcutUtils.getValidationErrors(result));
        }
        return ResponseEntity.status(201).body(userService.saveUser(form));
    }


    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        Map<String, String> map = new HashMap<>();
        map.put("username", userService.getCurrentUsername());
        return ResponseEntity.ok(map);
    }
}
