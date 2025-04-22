package com.suhas.NotesApp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class JwtService {

    private String jwtSecret="";
    private SecretKey secretKey;

    @PostConstruct
    public void init()
    {
        try {
            this.jwtSecret= Encoders.BASE64.encode(KeyGenerator.getInstance("HmacSHA256").generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.secretKey=new SecretKeySpec(bytes,"HmacSHA256");
    }

    public String generateToken(String username) {
       return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*3*60*60))
                .signWith(secretKey).subject(username)
                .compact();

    }


    private Key getSecretKey() {
        byte[] decode = Decoders.BASE64.decode(jwtSecret);
       return Keys.hmacShaKeyFor(decode);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && checkIfNotExpired(token));
    }

    public String extractUserName(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Date getExpirationDate(String token)
    {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
    }

    public boolean checkIfNotExpired(String token)
    {
        Date expirationDate = getExpirationDate(token);
        Date currentDate= new Date();
        return currentDate.before(expirationDate);
    }
}
