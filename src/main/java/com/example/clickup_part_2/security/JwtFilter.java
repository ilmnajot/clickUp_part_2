package com.example.clickup_part_2.security;

import com.example.clickup_part_2.entity.User;
import com.example.clickup_part_2.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    JwtProvider jwtProvider;
    @Autowired
    @Lazy
    AuthService authService;


    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization = authorization.substring(7);
            String token = jwtProvider.getUsernameFromToken(authorization);
        User user = (User) authService.loadUserByUsername(token);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
    }
}
