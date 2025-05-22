package com.example.msauthservice.security;

import com.example.msauthservice.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
    public String createToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        // En 0.12.x, `Jwts.claims().setSubject()` es parte de una cadena de construcción del JWT
        // Es mejor pasar directamente el subject al builder o usar el metodo `add`
        claims.put("id", authUser.getId()); // Añadir ID como un claim personalizado
        claims.put("sub", authUser.getUserName()); // `sub` (subject) es el nombre de usuario

        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000); // 1 hora de expiración

        return Jwts.builder()
                .setClaims(claims) // Añadir todos los claims incluyendo el "sub"
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret) // Usar el objeto Key y el algoritmo
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser() // Empezar con Jwts.parserBuilder()
                    .setSigningKey(secret) // Establecer la clave de firma
                    .build() // Construir el parser
                    .parseClaimsJws(token); // Parsear el token

            return true;
        } catch (Exception e) {
            // Es útil loggear la excepción para saber qué tipo de error es (ExpiredJwtException, MalformedJwtException, etc.)
            // System.err.println("Error al validar token: " + e.getMessage());
            return false;
        }
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            // System.err.println("Error al obtener username del token: " + e.getMessage());
            return "bad token";
        }
    }
}
