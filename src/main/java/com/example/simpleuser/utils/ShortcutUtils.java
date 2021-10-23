package com.example.simpleuser.utils;

import com.example.simpleuser.form.ResponseForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShortcutUtils {


    public ResponseForm getValidationErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        System.out.println("bura" + result.getAllErrors());
        for (int i = 0; i < result.getFieldErrorCount(); i++) {
            map.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
        }
        return new ResponseForm("Validasiya xətası.", map);
    }

}
