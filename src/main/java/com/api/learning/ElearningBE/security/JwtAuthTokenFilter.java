package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.security.impl.UserDetailServiceImpl;
import com.api.learning.ElearningBE.services.account.AccountServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    public String extractToken(HttpServletRequest httpServletRequest){
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String jwt = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwt = requestTokenHeader.substring(7);
            return jwt;
        }
        return jwt;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractToken(httpServletRequest);
            String email = jwtUtils.getEmailFromToken(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() != null){
                UserDetails userDetails = userDetailService.loadUserByUsername(email);
                if (userDetails != null && jwtUtils.validateToken(userDetails,jwt)){
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            log.error("Occurred an error authentication: "+ e.getMessage());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
