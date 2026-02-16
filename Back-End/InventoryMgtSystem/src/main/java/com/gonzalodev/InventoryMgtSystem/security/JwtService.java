package com.gonzalodev.InventoryMgtSystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    private static final long EXPIRATION_TIME_IN_MILLISEC = 1000L * 60L * 60L * 24L * 7L * 2L ; // 2 weeks
    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String secretJwtString;

    @PostConstruct // it is executed apter the @Bean is initialized
    private void init(){
        byte[] keyByte = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME_IN_MILLISEC))
                .signWith(secretKey)
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject); // obtains the email
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public boolean isTokeValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());// if the expiration is before now still ok
    }





}
