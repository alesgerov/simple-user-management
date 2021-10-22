package com.example.simpleuser.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.simpleuser.entity.UserTable;
import com.example.simpleuser.security.users.UserDetailsClass;
import com.example.simpleuser.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTAuthorization extends BasicAuthenticationFilter {
    private final UserService repo;

    public JWTAuthorization(AuthenticationManager manager,UserService repo) {
        super(manager);
        this.repo = repo;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String  header=request.getHeader(JWTProperties.HEADER);
        if (header==null || !header.startsWith(JWTProperties.TOKEN_PRE)){
            chain.doFilter(request,response);
            return;
        }
        Authentication authentication=getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token=request.getHeader(JWTProperties.HEADER);
        if (token!=null){
            String username= JWT.require(Algorithm.HMAC512(JWTProperties.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JWTProperties.TOKEN_PRE,""))
                    .getSubject();

            if (username!=null){
                Optional<UserTable> userClass=repo.getUserByUsername(username);
                if (userClass.isPresent()) {
                    UserDetailsClass principal = new UserDetailsClass(userClass.get());
                    return new UsernamePasswordAuthenticationToken(
                            username,null,principal.getAuthorities()
                    );
                }

            }
        }
        return null;
    }
}
