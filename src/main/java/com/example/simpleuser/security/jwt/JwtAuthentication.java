package com.example.simpleuser.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.simpleuser.security.users.UserDetailsClass;
import com.example.simpleuser.utils.ShortcutUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;


public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

    private final Validator validator;
    private final AuthenticationManager authenticationManager;
    private final ShortcutUtils shortcutUtils;

    public JwtAuthentication(Validator validator, AuthenticationManager authenticationManager, ShortcutUtils shortcutUtils) {
        this.validator = validator;
        this.authenticationManager = authenticationManager;
        this.shortcutUtils = shortcutUtils;
        setFilterProcessesUrl("/api/authorize");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginViewModel model = null;
        try {
            model = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
            Set<ConstraintViolation<LoginViewModel>> violations = validator.validate(model);
            if (!violations.isEmpty()) {
                request.setAttribute("form", shortcutUtils.objectToJson(shortcutUtils.getValidationErrors(violations)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert model != null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                model.getUsername(),
                model.getPassword(),
                new ArrayList<>()
        );
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsClass principal = (UserDetailsClass) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION))
                .sign(Algorithm.HMAC512(JWTProperties.SECRET.getBytes(StandardCharsets.UTF_8)));
        response.addHeader(JWTProperties.HEADER, JWTProperties.TOKEN_PRE + token);

    }
}