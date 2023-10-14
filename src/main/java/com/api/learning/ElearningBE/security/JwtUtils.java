package com.api.learning.ElearningBE.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final String secret = "VGFuUGh1Y1NQS1Q=";

    public String getEmailFromToken(String token){
        return getClaimsFromToken(token, Claims::getSubject);
    }
    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Claims getAllClaimsFromToken(String token){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    public Boolean isTokenExpired(String token){
        final Date dateExpire = getClaimsFromToken(token, Claims::getExpiration);
        return dateExpire.before(new Date());
    }
    public Boolean validateToken(UserDetails userDetails, String token){
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
