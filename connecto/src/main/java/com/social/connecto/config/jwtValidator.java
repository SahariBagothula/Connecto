package com.social.connecto.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.social.connecto.exceptions.BadCredentialsException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            try {
                // Extract email from JWT token
                String email = JwtProvider.getEmailFromJwtToken(jwt);

                // Create an empty list of authorities (permissions)
                List<GrantedAuthority> authorities = new ArrayList<>();

                // Create an authentication token and set it in the security context
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle invalid token case
                throw new BadCredentialsException("Invalid token");
            }
        } else {
            throw new BadCredentialsException("Please provide a token");
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
