package com.canaristar.backend.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtProvider {

    private final SecretKey key;

    // 1 day expiry
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public JwtProvider(JwtConstant jwtConstant) {
        this.key = Keys.hmacShaKeyFor(jwtConstant.getSecretKey().getBytes());
    }

    public String generateToken(Authentication auth) {

        String authorities = populateAuthorities(auth.getAuthorities());

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("email", auth.getName())
                .claim("authorities", authorities)
                .signWith(key)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return String.join(",", roles);
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}
