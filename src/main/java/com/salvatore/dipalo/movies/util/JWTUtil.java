package com.salvatore.dipalo.movies.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.salvatore.dipalo.movies.model.db.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt-secret}")
    private String secret;

    // Method to sign and create a JWT using the injected secret
    public String generateToken(User user) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date())
                .withIssuer("salvatore.dipalo")
                .sign(Algorithm.HMAC256(secret));
    }

    // Method to verify the JWT and then decode and extract the user email stored in the payload of the token
    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("salvatore.dipalo")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

}