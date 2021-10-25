package com.example.simpleuser.utils;

import com.example.simpleuser.form.ResponseForm;
import com.example.simpleuser.security.jwt.LoginViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ShortcutUtils {

    public ResponseForm getValidationErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < result.getFieldErrorCount(); i++) {
            map.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
        }
        return new ResponseForm("Validasiya xətası.", map);
    }


    public ResponseForm getValidationErrors(Set<ConstraintViolation<LoginViewModel>> violations) {
        List<ConstraintViolation<LoginViewModel>> list = violations.stream().toList();
        Map<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            hashMap.put(list.get(i).getPropertyPath().toString(), list.get(i).getMessage());
        }
        return new ResponseForm("Validasiya xetasi", hashMap);
    }


    public <T> T jsonToObject(String json, Class<T> o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, o);
    }


    public String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

}
