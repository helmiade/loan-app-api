package com.enigmacamp.livecode_loan_app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class JwtUtil {
    @Value("${loan_app_secret}")
    private String jwtSecret;

    @Value("${loan_app_name}")
    private String appName;

    @Value("${loan_app_jwtExpirationInSec}")
    private long jwtExpirationInSec;
//    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public String generateToken(AppUser appUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

            return JWT.create()
                    .withSubject(appUser.getUsername())
                    .withIssuer(appName)
                    .withExpiresAt(Instant.now().plusSeconds(jwtExpirationInSec))
                    .withIssuedAt(Instant.now())
                    .withClaim("roles",appUser.getRoles().stream().toList())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            log.error("error while generating token: {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public Boolean verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException exception){
            log.error("invalid verification JWT: {}", exception.getMessage());
            return false;
        }
    }

    public Map<String, String> getUserInfoByToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("roles", decodedJWT.getClaim("roles").asString());

            return userInfo;
        } catch (JWTVerificationException exception){
            log.error("invalid verification JWT: ", exception.getMessage());
            return null;
        }
    }
}

