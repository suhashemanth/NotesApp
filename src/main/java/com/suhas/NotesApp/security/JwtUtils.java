package com.suhas.NotesApp.security;


import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtUtils {

    private static Long EXPIRATION_TIME_SECONDS=6*30*24*60*60*1000L;
    private SecretKey secretKey;
    @Value("${secretJWTString}")
    private String secretJwtString;

    @PostConstruct
    public void init()
    {
        byte[] bytes = secretJwtString.getBytes();
        this.secretKey=new SecretKeySpec(bytes,"HmacSHA256");
    }

    public String generateToken(String email)
    {
        return Jwts.builder().subject(email).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME_SECONDS)).
                signWith(secretKey).
                compact();
    }

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        return userDetails.getUsername().equals(getUserNameFromToken(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date currentDate=new Date();
        Date expirationDate=Jwts.parser().verifyWith(this.secretKey).build().
                parseSignedClaims(token).getPayload().getExpiration();
        return expirationDate.before(currentDate);
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }


}
