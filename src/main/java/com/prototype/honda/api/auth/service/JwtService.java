package com.prototype.honda.api.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.prototype.honda.api.exception.exceptions.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    public String generateToken(String userId, String email, Collection<String> roles) {

        return JWT.create()
                .withSubject(userId)
                .withClaim("email", email)
                .withClaim("roles", roles.stream().toList())
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .sign(Algorithm.HMAC256(secret));
    }

    public String extractUsername(String token) {

        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new NotAuthorizedException("Token inválido ou expirado");
        }

    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        Date expiration = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getExpiresAt();

        return expiration.before(new Date());
    }
}
