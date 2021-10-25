package com.example.simpleuser.security;

import com.example.simpleuser.controller.exceptioncontroller.AuthExceptionHandler;
import com.example.simpleuser.security.jwt.JWTAuthorization;
import com.example.simpleuser.security.jwt.JwtAuthentication;
import com.example.simpleuser.security.users.UserDetailsClassService;
import com.example.simpleuser.service.UserService;
import com.example.simpleuser.utils.ShortcutUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Validator;


@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsClassService userDetailsClassService;
    private final UserService repo;
    private final ShortcutUtils utils;
    private final Validator validator;


    public SecurityConfigurer(UserDetailsClassService userDetailsClassService,
                              UserService repo, ShortcutUtils utils, Validator validator) {
        this.userDetailsClassService = userDetailsClassService;
        this.repo = repo;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthentication(validator, authenticationManager(), utils))
                .addFilter(new JWTAuthorization(authenticationManager(), this.repo))
                .authorizeRequests()
                .antMatchers("/api/authorize").permitAll()
                .antMatchers("/api/account/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new AuthExceptionHandler())
        ;

    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userDetailsClassService);
        return provider;
    }

}

