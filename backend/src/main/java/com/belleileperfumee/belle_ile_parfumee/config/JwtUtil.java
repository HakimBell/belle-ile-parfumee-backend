package com.belleileperfumee.belle_ile_parfumee.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET = "MaCleSuperSecretePourJWTQuiDoitFaire256BitsMin!";

    // Clé secrète pour signer les tokens (comme un mot de passe)
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durée de validité du token : 10 heures
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 heures en millisecondes

    // Générer un token JWT pour un utilisateur
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // L'email de l'utilisateur
                .setIssuedAt(new Date()) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Date d'expiration
                .signWith(SECRET_KEY) // Signer avec la clé secrète
                .compact();
    }

    // Extraire l'email du token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extraire le rôle du token
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    // Vérifier si le token est expiré
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Vérifier si le token est valide
    public boolean validateToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    // Extraire toutes les informations du token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}