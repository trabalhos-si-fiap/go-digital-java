package com.laroz.infra.security;

import com.laroz.infra.exceptions.InvalidOrExpiredTokenException;
import com.laroz.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().equalsIgnoreCase("/api/v1/application-auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        var tokenJWT = getToken(request);

        try {
            if (tokenJWT != null) {
                var subject = tokenService.getSubject(tokenJWT);
                var user = userRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidOrExpiredTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado.");
            response.getWriter().flush();
        }
    }

    private String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
