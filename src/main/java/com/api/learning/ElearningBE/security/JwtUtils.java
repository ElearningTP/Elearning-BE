package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.dto.TokenDetail;
import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.utils.ZipUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final long JWT_VALIDITY_SECONDS = 24 * 60 * 60;
    private static final String DELIM = "\\|";
    @Value("${elearning.jwt.secret}")
    private String secret;

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date dateExpire = getClaimsFromToken(token, Claims::getExpiration);
        return dateExpire.before(new Date());
    }

    public Boolean validateToken(UserDetailsImpl userDetails, String token) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> pCode = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String additionalInfo = ZipUtils.zipString(userDetails.getAccountId() + DELIM + userDetails.getEmail() + DELIM + userDetails.getKind());
        claims.put("authorities", pCode);
        claims.put("name", userDetails.getFullName());
        claims.put("additional_info", additionalInfo);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts
                .builder()
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_SECONDS * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenDetail getTokenDetail(UserDetailsImpl userDetails) {
        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setFullName(userDetails.getFullName());
        tokenDetail.setAvatar(userDetails.getAvatar());
        tokenDetail.setToken(generateToken(userDetails));
        tokenDetail.setExpiresIn(JWT_VALIDITY_SECONDS);
        tokenDetail.setRole(userDetails.getRole());
        return tokenDetail;
    }
}
