package com.belleileperfumee.belle_ile_parfumee.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Récupérer le header Authorization
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // 2. Vérifier si le header contient "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Enlever "Bearer " pour garder juste le token
            email = jwtUtil.extractEmail(token); // Extraire l'email du token
        }

        // 3. Si on a un email ET qu'aucune authentification n'est déjà en place
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Vérifier si le token est valide
            if (jwtUtil.validateToken(token, email)) {

                // 5. Créer l'authentification pour Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Dire à Spring Security que l'utilisateur est authentifié
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}