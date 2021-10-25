package com.example.simpleuser.controller.exceptioncontroller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestControllerAdvice
public class AuthExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (httpServletRequest.getAttribute("form") != null) {
            String message = httpServletRequest.getAttribute("form").toString();
            response.setStatus(422);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().println(message);
        } else {
            String message = """
                    {
                    \t\t"title": "Girish qadagandir",
                    \t\t"detail": "Bu sehifeye girish ucun token elde etmelisiniz"
                    }""";
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().println(message);
        }
    }
}
