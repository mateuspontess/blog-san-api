package br.com.blogsanapi.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.blogsanapi.infra.exception.InvalidTokenException;
import br.com.blogsanapi.model.user.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        if (user.getLogin() == null || user.getLogin().isBlank()) 
            throw new IllegalArgumentException("Cannot generate a token with a null or empty login");

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("blog-san")
                .withSubject(user.getLogin())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("blog-san")
                .build()
                .verify(token)
                .getSubject();

        } catch (JWTVerificationException ex){
            throw new InvalidTokenException(ex);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public Instant getExpirationDateWhithoutMiliseconds() {
        return this.genExpirationDate().truncatedTo(ChronoUnit.SECONDS);
    }
}